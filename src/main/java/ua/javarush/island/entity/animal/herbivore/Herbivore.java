package ua.javarush.island.entity.animal.herbivore;

import ua.javarush.island.entity.Organism;
import ua.javarush.island.entity.animal.Animal;

import java.util.SplittableRandom;

public class Herbivore extends Animal {
    @Override
    public void eat() {
        boolean isFoodFind = false;
        for (Organism organism : currentArea.getResidents()) {

            String name = organism.getClass().getSimpleName();


            if (listOfEatableOrganism.containsKey(name)) {
                isFoodFind = true;
                System.out.println(this.getClass().getSimpleName() + " find " + name);

                if (getChanceToEat(name)) {
                    System.out.println(this.getClass().getSimpleName() + "eat" + name);

                    if (organism.getCurrentWeigth() > foodWeigthForSaturation) {
                        organism.setCurrentWeigth(organism.getCurrentWeigth()- foodWeigthForSaturation);
                        organism.setHealth(organism.getCurrentWeigth());
                    }
                }
            }
        }
        if(!isFoodFind) {
            setHealth( getHealth() - 1);
        }
    }
    @Override
    public void reproduce() {
        for (Organism organism : currentArea.getResidents()) {

            String organismName = organism.getClass().getSimpleName();
            String thisName = this.getClass().getSimpleName();


            if (!isHavePair) {
                if (currentArea.getNumberAnimalOfThisClass(this) < maxOrganismOnArea) {
                    if (!organism.equals(this)) {
                        if (organismName.equals(thisName)) {
                            if(organism.getNumberOfReproduceTime() > 0) {
                                if (getReproduceProbability()) {
                                    this.isHavePair =true;
                                    organism.isHavePair = true;
                                    System.out.println(thisName+ " " + this.getID() + " find  pair to reproduce " + organismName + organism.getID());

                                    organism.setNumberOfReproduceTime(organism.getNumberOfReproduceTime() - 1);
                                    this.setNumberOfReproduceTime(getNumberOfReproduceTime() - 1);

                                    getCurrentArea().listToCreateOrganism.merge("ua.javarush.island.entity.animal.herbivore."+ thisName, 1, Integer::sum);
                                }
                            }
                        }
                    }
                }
            }
        }
        isHavePair = false;
    }
}
