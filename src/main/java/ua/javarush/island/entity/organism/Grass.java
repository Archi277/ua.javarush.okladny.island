package ua.javarush.island.entity.organism;

import ua.javarush.island.entity.Organism;
import ua.javarush.island.entity.Plant;
import ua.javarush.island.gameisland.OrganismFactory;

public class Grass extends Plant {

    @Override
    public void reproduce() {

        if (getCurrentWeigth() <= getStartWeigth()) {

            setCurrentWeigth(getCurrentWeigth() + 1);
            setHealth(getCurrentWeigth());

        } else if (getCurrentArea().getNumberOrganismOfThisClass(this) < getMaxOrganismOnArea() && this.getNumberOfReproduceTime() > 0) {
            this.setNumberOfReproduceTime(getNumberOfReproduceTime()-1);
            getCurrentArea().getListToCreateOrganism().merge(OrganismFactory.PATH_TO_ORGANISM_FOLDER + "Grass", 1, Integer::sum);
        }
    }

    @Override
    public void move() {}

    @Override
    public void eat() {}

    @Override
    public String toString() {
        return "[WW]["+getHealth()+"]  ";
    }
}

