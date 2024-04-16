package ua.javarush.island.entity.organism;

import ua.javarush.island.entity.Animal;
import ua.javarush.island.entity.Organism;
import ua.javarush.island.gameisland.OrganismFactory;

public class Herbivore extends Animal {


    @Override
    public void eat() {
        boolean isFoodFind = false;
        for (Organism organism : currentArea.getResidents()) {

            String name = organism.getClass().getSimpleName();

            if (listOfEatableOrganism.containsKey(name) && getChanceToEat(name)) {
                isFoodFind = true;

                //TODO delete
//                System.out.println(this.getClass().getSimpleName() + " find " + name);
//                System.out.println(this.getClass().getSimpleName() + "eat" + name);

                if (organism.getCurrentWeigth() > foodWeigthForSaturation) {
                    organism.setCurrentWeigth(organism.getCurrentWeigth() - foodWeigthForSaturation);
                    organism.setHealth(organism.getCurrentWeigth());
                }
            }
        }
        if (!isFoodFind) {
            setHealth(getHealth() - 1);
        }
    }

    @Override
    public void reproduce() {
        for (Organism organism : currentArea.getResidents()) {

            String organismName = organism.getClass().getSimpleName();
            String thisName = this.getClass().getSimpleName();

            if (!isHavePair && !organism.equals(this) && organismName.equals(thisName) && organism.getNumberOfReproduceTime() > 0) {

                if (currentArea.getNumberOrganismOfThisClass(this) < maxOrganismOnArea) {

                    if (getReproduceProbability()) {
                        this.isHavePair = true;
                        organism.setHavePair(true);
//                        System.out.println(thisName + " " + this.getID() + " find  pair to reproduce " + organismName + organism.getID());

                        organism.setNumberOfReproduceTime(organism.getNumberOfReproduceTime() - 1);
                        this.setNumberOfReproduceTime(getNumberOfReproduceTime() - 1);

                        getCurrentArea().getListToCreateOrganism().merge(OrganismFactory.PATH_TO_ORGANISM_FOLDER + thisName, 1, Integer::sum);
                    }
                }
            }
        }
        isHavePair = false;
    }
}
