package ua.javarush.island.entity;

import ua.javarush.island.gameisland.OrganismFactory;
import java.util.NoSuchElementException;

public class Predator extends Animal {

    @Override
    public void eat() {

        boolean isFoodFind = false;
        for (Organism organism : currentArea.getResidents()) {

            String name = organism.getClass().getSimpleName();

            if (listOfEatableOrganism.containsKey(name) && getChanceToEat(name)) {

                isFoodFind = true;

                if (organism.getHealth() != 0) {
                    if (organism.getStartWeigth() > foodWeigthForSaturation) {
                        currentFoodWeigthForSaturation = foodWeigthForSaturation;
                        organism.setHealth(0);
                        break;

                    } else if (organism.getStartWeigth() < foodWeigthForSaturation) {
                        currentFoodWeigthForSaturation += organism.getStartWeigth();
                        organism.setHealth(0);
                    }
                }

            }
        }
        if (!isFoodFind) {
            setHealth(getHealth() - 1);
        }
    }

    @Override
    public void reproduce() {

        if (currentArea.getNumberOrganismOfThisClass(this) < maxOrganismOnArea && !this.isHavePair && this.getNumberOfReproduceTime() > 0) {

            Organism desiredOrganism;

            try {
                desiredOrganism = currentArea.getResidents().stream()
                        .filter(o -> !o.equals(this))
                        .filter(o -> (o.getClass().getSimpleName()).equals(this.getClass().getSimpleName()))
                        .filter(o -> o.getNumberOfReproduceTime() > 0)
                        .filter(o -> getReproduceProbability())
                        .findAny().get();

                String thisName = this.getClass().getSimpleName();

                this.isHavePair = true;
                desiredOrganism.setHavePair(true);

                desiredOrganism.setNumberOfReproduceTime(desiredOrganism.getNumberOfReproduceTime() - 1);
                this.setNumberOfReproduceTime(getNumberOfReproduceTime() - 1);

                getCurrentArea().getListToCreateOrganism().merge(OrganismFactory.PATH_TO_ORGANISM_FOLDER + thisName, 1, Integer::sum);

            } catch (NoSuchElementException e) {
                setHavePair(false);
            }
        }
    }
}
