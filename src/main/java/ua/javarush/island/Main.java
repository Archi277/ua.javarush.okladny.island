package ua.javarush.island;

import ua.javarush.island.entity.Organism;
import ua.javarush.island.gameisland.Island;
import ua.javarush.island.gameloader.GameLoader;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {

        GameLoader gameLoader = new GameLoader();

//        GameLoader.createOrganism("ua.javarush.island.entity.animal.predator.Wolf", Island.areas[4][4], 15);
//        GameLoader.createOrganism("ua.javarush.island.entity.animal.herbivore.Rabbit", Island.areas[4][4], 4);

        GameLoader.createOrganism("ua.javarush.island.entity.animal.predator.Wolf");
        GameLoader.createOrganism("ua.javarush.island.entity.animal.herbivore.Rabbit");
        GameLoader.createOrganism("ua.javarush.island.entity.plant.Grass");


        for (int i = 0; i < 200; i++) {
            for (Organism organism : GameLoader.aliveOrganism) {
                organism.play();
            }
            for (int k = 0; k < Island.getSizeX() ; k++) {
                for (int j = 0; j < Island.getSizeY() ; j++) {
                    Island.getAreas()[k][j].createNewOrganism();
                }
            }
            gameLoader.checkAliveAnimals();
            gameLoader.collectStaticticOfAnimals();
            gameLoader.showAreas();

            //Thread.sleep(500);
        }


    }
}

