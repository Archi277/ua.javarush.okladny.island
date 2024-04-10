package ua.javarush.island.gameloader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.RandomStringUtils;
import ua.javarush.island.entity.Organism;
import ua.javarush.island.entity.animal.Animal;
import ua.javarush.island.entity.plant.Plant;
import ua.javarush.island.gameisland.Area;
import ua.javarush.island.gameisland.Island;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameLoader {

    public static int IslandSize;
    public static Map<String, Integer> residentsProperties;
    public static Island island;
    public static List<Organism> aliveOrganism = new ArrayList<>();
    public Map<String, Integer> statisticOfResidents = new HashMap<>();

    public GameLoader() {
        createGameSettingObject();
        island = new Island(IslandSize, IslandSize);
    }

    private void createGameSettingObject() {
        GameSettings gameSettings = null;
        try {
            Path pathToYamlGameSettins = Path.of("./src/main/resources/GameSettings.yaml");
            if (!Files.exists(pathToYamlGameSettins)) System.out.println("File  not exist. Object not create ");

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            gameSettings = mapper.readValue(Files.readString(pathToYamlGameSettins), GameSettings.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GameLoader.IslandSize = gameSettings.getIslandSize();
        GameLoader.residentsProperties = gameSettings.getResidentsProperties();

    }

    public void showAreas() {
        island.showAreas();
    }


    public static void createOrganism(String fullNameClass) {

        String shortName = getShortNameClassFromFullName(fullNameClass);
        int quantity = GameLoader.residentsProperties.get(shortName);

        try {
            Path pathToYamlConfigFile = Path.of("./src/main/resources/" + shortName + ".yaml");
            if (!Files.exists(pathToYamlConfigFile))
                System.out.println("File " + shortName + ".YAML not exist. Object not create ");

            Class organismClass = Class.forName(fullNameClass);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            for (int i = 0; i < quantity; i++) {
                Organism organism = (Organism) mapper.readValue(Files.readString(pathToYamlConfigFile), organismClass);
                organism.setCurrentArea(Island.areas[getRandom()][getRandom()]);
                organism.getCurrentArea().getResidents().add(organism);
                organism.setID(RandomStringUtils.randomAlphanumeric(10));
                aliveOrganism.add(organism);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void createOrganism(String fullNameClass, Area area, int quantity) {

        String shortName = getShortNameClassFromFullName(fullNameClass);


        try {
            Path pathToYamlConfigFile = Path.of("./src/main/resources/" + shortName + ".yaml");
            if (!Files.exists(pathToYamlConfigFile))
                System.out.println("File " + shortName + ".YAML not exist. Object not create ");

            Class organismClass = Class.forName(fullNameClass);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            for (int i = 0; i < quantity; i++) {
                Organism organism = (Organism) mapper.readValue(Files.readString(pathToYamlConfigFile), organismClass);
                organism.setCurrentArea(area);
                organism.getCurrentArea().getResidents().add(organism);
                organism.setID(RandomStringUtils.randomAlphanumeric(10));
                aliveOrganism.add(organism);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private static String getShortNameClassFromFullName(String fullNameClass) {
        String[] words = fullNameClass.split("\\.");
        return words[words.length - 1];
    }


    public void checkAliveAnimals() {
        Iterator<Organism> organismIterator = aliveOrganism.iterator();
        while (organismIterator.hasNext()) {

            Organism nextOrganism = organismIterator.next();
            if (nextOrganism instanceof Animal) {
                Animal animal = (Animal) nextOrganism;
                if (animal.getHealth() < 10) {
                    animal.getCurrentArea().getResidents().remove(animal);
                    organismIterator.remove();
                }
            }
            if (nextOrganism instanceof Plant) {
                if (nextOrganism.getCurrentWeigth() <= nextOrganism.getStartWeigth() / 10) {
                    nextOrganism.getCurrentArea().getResidents().remove(nextOrganism);
                    organismIterator.remove();
                }
            }
        }
    }

    private static int getRandom() {
        return ThreadLocalRandom.current().nextInt(0, IslandSize);
    }

    public void collectStaticticOfAnimals(){
        for(Organism organism: aliveOrganism){
            this.statisticOfResidents.merge(organism.getClass().getSimpleName(), 1, Integer::sum);
        }
        if(!statisticOfResidents.isEmpty()) {
            System.out.println(statisticOfResidents);
        }
        statisticOfResidents.clear();
    }

}


