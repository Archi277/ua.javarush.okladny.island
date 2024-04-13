package ua.javarush.island;

import ua.javarush.island.entity.Organism;
import ua.javarush.island.gameisland.Island;
import ua.javarush.island.gameloader.GameLoader;
import ua.javarush.island.gameloader.GameStatus;
import ua.javarush.island.task.ListOfTaskCreator;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {

        GameLoader gameLoader = new GameLoader();
        ListOfTaskCreator listOfTaskCreator = new ListOfTaskCreator();

        //methodWithExetorService(gameLoader, listOfTaskCreator);

        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            //methodBasedOnList(gameLoader, i);

            methodBasedOnArea(gameLoader, i);
            //Thread.sleep(500);
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }


    private static void methodWithExetorService(GameLoader gameLoader, ListOfTaskCreator listOfTaskCreator) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        final long startTime = System.currentTimeMillis();
        for (int i=0;i<50;i++) {
            System.out.println("Day #: "  + i);
            GameStatus.collectStatisticOfAnimals();
            gameLoader.showAreas();

            //executorService.invokeAll(listOfTaskCreator.taskReproduceList);
            //TODO realize wait
            //TODO realize LOCK in Area
            //
            //executorService.invokeAll(listOfTaskCreator.taskCreateList);
            //executorService.invokeAll(listOfTaskCreator.taskFeedList);
            executorService.invokeAll(listOfTaskCreator.taskMoveList);
            executorService.invokeAll(listOfTaskCreator.taskChecksList);

        }


        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));

        executorService.shutdown();
    }

    private static void methodBasedOnList(GameLoader gameLoader, int i) {
        System.out.println("Iter #: "  + i);
        GameStatus.collectStatisticOfAnimals();
        gameLoader.showAreas();
        for (Organism organism : GameStatus.aliveOrganism) {
            organism.play();
        }
        for (int k = 0; k < Island.getSizeX(); k++) {
            for (int j = 0; j < Island.getSizeY(); j++) {
                Island.getAreas()[k][j].createNewOrganismOnArea();
            }
        }
        GameStatus.checkAliveAnimals();
    }

    private static void methodBasedOnArea(GameLoader gameLoader, int i) {
        System.out.println("Iter #: "  + i);
        GameStatus.collectStatisticOfAnimals();
        gameLoader.showAreas();

//        for (int k = 0; k < Island.getSizeX(); k++) {
//            for (int j = 0; j < Island.getSizeY(); j++) {
//                Island.getAreas()[k][j].reproduceOrganismOnArea();
//            }
//        }

//        for (int k = 0; k < Island.getSizeX(); k++) {
//            for (int j = 0; j < Island.getSizeY(); j++) {
//                Island.getAreas()[k][j].createNewOrganismOnArea();
//            }
//        }
//
//        for (int k = 0; k < Island.getSizeX(); k++) {
//            for (int j = 0; j < Island.getSizeY(); j++) {
//                Island.getAreas()[k][j].feedOrganismOnArea();
//            }
//        }

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


}



