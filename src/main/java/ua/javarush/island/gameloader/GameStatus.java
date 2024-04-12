package ua.javarush.island.gameloader;

import ua.javarush.island.entity.Organism;
import ua.javarush.island.entity.Animal;
import ua.javarush.island.entity.Plant;

import java.util.*;

public class GameStatus {
    public static List<Organism> aliveOrganism = new ArrayList<>();
    public static Map<String, Integer> statisticOfResidents = new HashMap<>();

    public static  void collectStatisticOfAnimals(){
        for(Organism organism: GameStatus.aliveOrganism){
            if(organism!=null)statisticOfResidents.merge(organism.getClass().getSimpleName(), 1, Integer::sum);
        }
        if(!statisticOfResidents.isEmpty()) {
            System.out.println(statisticOfResidents);
        }
        statisticOfResidents.clear();
    }

    public static void checkAliveAnimals() {
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
}
