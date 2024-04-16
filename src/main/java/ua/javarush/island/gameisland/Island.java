package ua.javarush.island.gameisland;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ua.javarush.island.entity.Organism;
import ua.javarush.island.task.ListOfTaskCreator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

public class Island {
    private static GameSettings gameSettings = createGameSettingObject();
    private static int sizeX;
    private static int sizeY;
    public  static Area[][] areas;
    public static List<Organism> aliveOrganism = new ArrayList<>();
    public static Map<String, Integer> deathStatistics = new HashMap<>();
    public static Map<String, Integer> newbornStatistics = new HashMap<>();

    public Island() {

        createGameSettingObject();

        this.sizeX = gameSettings.getIslandSizeX();
        this.sizeY = gameSettings.getIslandSizeY();

        areas = new Area[sizeX][sizeY];

        createAreas();

        OrganismFactory.createOrganism(gameSettings.getResidentsProperties());
//        OrganismFactory.createOrganism("ua.javarush.island.entity.organism." + "Grass", areas[0][0], 5);
//        OrganismFactory.createOrganism("ua.javarush.island.entity.organism." + "Rabbit", areas[0][0], 5);
    }

    public static int getSizeX() {
        return sizeX;
    }

    public static int getSizeY() {
        return sizeY;
    }

    public static Area[][] getAreas() {
        return areas;
    }

    private void createAreas() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                areas[i][j] = new Area(new Coordinate(i, j));

            }
        }
    }

    public static void showAreas() {
        System.out.println("=".repeat(150));
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                System.out.print(areas[i][j]);
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("=".repeat(150));
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

    public static  void collectStatistic(){
        Map<String, Integer> statisticOfResidents = new HashMap<>();
        if(!deathStatistics.isEmpty()) {
            System.out.println("Death statistics: ");
            System.out.println(deathStatistics);

        }
        deathStatistics.clear();

        if(!newbornStatistics.isEmpty()) {
            System.out.println("Newborn statistics: ");
            System.out.println(newbornStatistics);

        }
        newbornStatistics.clear();

        for(Organism organism: aliveOrganism){
            if(organism!=null){
                statisticOfResidents.merge(organism.getClass().getSimpleName(), 1, Integer::sum);
            }
        }
        if(!statisticOfResidents.isEmpty()) {
            System.out.println("Now alive organism:");
            System.out.println(statisticOfResidents);

        }

    }

    public void live(int days){
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < days; i++) {
            //methodBasedOnList(gameLoader, i);

            methodBasedOnArea( i);
            //Thread.sleep(500);
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }
    private  void methodBasedOnArea( int i) {
        System.out.println("Day #: "  + i);
        collectStatistic();
        showAreas();

        for (int k = 0; k < Island.getSizeX(); k++) {
            for (int j = 0; j < Island.getSizeY(); j++) {
                Island.getAreas()[k][j].reproduceOrganismOnArea();
            }
        }

        for (int k = 0; k < Island.getSizeX(); k++) {
            for (int j = 0; j < Island.getSizeY(); j++) {
                Island.getAreas()[k][j].createNewOrganismOnArea();
            }
        }

        for (int k = 0; k < Island.getSizeX(); k++) {
            for (int j = 0; j < Island.getSizeY(); j++) {
                Island.getAreas()[k][j].feedOrganismOnArea();
            }
        }

        for (int k = 0; k < Island.getSizeX(); k++) {
            for (int j = 0; j < Island.getSizeY(); j++) {
                Island.getAreas()[k][j].moveOrganismOnArea();

            }
        }

        for (int k = 0; k < Island.getSizeX(); k++) {
            for (int j = 0; j < Island.getSizeY(); j++) {
                Island.getAreas()[k][j].checkAliveAnimalsOnArea();
            }
        }
    }
    public  void live2(int days) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        ListOfTaskCreator listOfTaskCreator = new ListOfTaskCreator();

        final long startTime = System.currentTimeMillis();

        for (int i=1;i<days+1;i++) {

            //collectStatisticOfAnimals();
            //showAreas();

            List<Future<Void>> taskReproduceResult = executorService.invokeAll(listOfTaskCreator.taskReproduceList);
            //while (!taskReproduceResult.stream().allMatch(o-> o.isDone())) {}

            executorService.invokeAll(listOfTaskCreator.taskCreateList);
            executorService.invokeAll(listOfTaskCreator.taskFeedList);
            executorService.invokeAll(listOfTaskCreator.taskMoveList);
            executorService.invokeAll(listOfTaskCreator.taskChecksList);

            System.out.println();
            System.out.println();
            System.out.println("*".repeat(50));
            System.out.println("After day #: "  + i);
            collectStatistic();
//

            //Thread.sleep(100);
        }


        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));

        executorService.shutdown();
    }

}
