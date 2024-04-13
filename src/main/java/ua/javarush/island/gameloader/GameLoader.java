package ua.javarush.island.gameloader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.RandomStringUtils;
import ua.javarush.island.entity.Organism;
import ua.javarush.island.gameisland.Area;
import ua.javarush.island.gameisland.Island;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameLoader {
    private static GameSettings gameSettings = createGameSettingObject();
    public static final  String PATH_TO_ORGANISM_FOLDER = "ua.javarush.island.entity.organism.";
    public static int IslandSize = gameSettings.getIslandSize();;
    public static Map<String, Integer> residentsProperties = gameSettings.getResidentsProperties();
    public static Island island;



    public GameLoader() {
        createGameSettingObject();
        island = new Island(IslandSize, IslandSize);
        //createOrganism();
        createOrganism("ua.javarush.island.entity.organism.Wolf", Island.areas[0][0],1);


    }

    private static GameSettings createGameSettingObject() {

        try {
            Path pathToYamlGameSettings = Path.of("./src/main/resources/GameSettings.yaml");
            if (!Files.exists(pathToYamlGameSettings)) System.out.println("File  not exist. Object not create ");

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return mapper.readValue(Files.readString(pathToYamlGameSettings), GameSettings.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAreas() {
        island.showAreas();
    }


    public static void createOrganism() {

        for (String name : residentsProperties.keySet()) {

            String fullNameClass = PATH_TO_ORGANISM_FOLDER + name;
            int quantity = GameLoader.residentsProperties.get(name);


            if (quantity >0) {
                try {
                    Path pathToYamlConfigFile = Path.of("./src/main/resources/" + name + ".yaml");
                    if (!Files.exists(pathToYamlConfigFile))
                        System.out.println("File " + name + ".YAML not exist. Object not create ");

                    Class organismClass = Class.forName(fullNameClass);
                    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    for (int i = 0; i < quantity; i++) {
                        Organism organism = (Organism) mapper.readValue(Files.readString(pathToYamlConfigFile), organismClass);
                        organism.setCurrentArea(Island.areas[getRandom()][getRandom()]);
                        organism.getCurrentArea().getResidents().add(organism);
                        organism.setID(RandomStringUtils.randomAlphanumeric(10));
                        GameStatus.aliveOrganism.add(organism);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
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
                GameStatus.aliveOrganism.add(organism);
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

    private static int getRandom() {
        return ThreadLocalRandom.current().nextInt(0, IslandSize);
    }



}


