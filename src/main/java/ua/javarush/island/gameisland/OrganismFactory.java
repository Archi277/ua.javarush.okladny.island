package ua.javarush.island.gameisland;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.RandomStringUtils;
import ua.javarush.island.entity.Organism;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class OrganismFactory {

    public static final  String PATH_TO_ORGANISM_FOLDER = "ua.javarush.island.entity.organism.";
    public static final  String PATH_TO_YAML_FOLDER = "./src/main/resources/";

    public static void createOrganism(Map<String, Integer> residentsProperties) {

        for (String name : residentsProperties.keySet()) {

            String fullNameClass = PATH_TO_ORGANISM_FOLDER + name;
            int quantity = residentsProperties.get(name);

            if (quantity >0) {
                try {
                    Path pathToYamlConfigFile = Path.of( PATH_TO_YAML_FOLDER+ name + ".yaml");
                    if (!Files.exists(pathToYamlConfigFile)) {
                        System.out.println("File " + name + ".YAML not exist. Object not create ");
                    }

                    Class<?> organismClass = Class.forName(fullNameClass);

                    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    for (int i = 0; i < quantity; i++) {
                        Organism organism = (Organism) mapper.readValue(Files.readString(pathToYamlConfigFile), organismClass);
                        organism.setCurrentArea(Island.areas[getRandomX()][getRandomY()]);
                        organism.getCurrentArea().getResidents().add(organism);
                        organism.setID(RandomStringUtils.randomAlphanumeric(10));
                        Island.aliveOrganism.add(organism);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void createOrganism(String fullNameClass, Area area, int quantity) {

        String shortName = getShortNameClassFromFullName(fullNameClass);

        try {
            Path pathToYamlConfigFile = Path.of(PATH_TO_YAML_FOLDER + shortName + ".yaml");
            if (!Files.exists(pathToYamlConfigFile)) {
                System.out.println("File " + shortName + ".YAML not exist. Object not create ");
            }

            Class<?> organismClass = Class.forName(fullNameClass);

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            for (int i = 0; i < quantity; i++) {
                Organism organism = (Organism) mapper.readValue(Files.readString(pathToYamlConfigFile), organismClass);
                organism.setCurrentArea(area);
                organism.getCurrentArea().getResidents().add(organism);
                organism.setID(RandomStringUtils.randomAlphanumeric(10));
                Island.aliveOrganism.add(organism);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getShortNameClassFromFullName(String fullNameClass) {
        String[] words = fullNameClass.split("\\.");
        return words[words.length - 1];
    }

    private static int getRandomX() {
        return ThreadLocalRandom.current().nextInt(0, Island.getSizeX());
    }
    private static int getRandomY() {
        return ThreadLocalRandom.current().nextInt(0, Island.getSizeY());
    }
}
