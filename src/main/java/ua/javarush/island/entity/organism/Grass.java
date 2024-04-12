package ua.javarush.island.entity.organism;

import ua.javarush.island.entity.Organism;
import ua.javarush.island.entity.Plant;
import ua.javarush.island.gameloader.GameLoader;

public class Grass extends Plant {

    @Override
    public void reproduce() {
        if (getCurrentWeigth() <= getStartWeigth()) {
            setCurrentWeigth(getCurrentWeigth() + 1);
            setHealth(getCurrentWeigth());
        } else {

            int counterOfSpecies = 0;
            for (Organism organism : getCurrentArea().getResidents()) {
                if (organism instanceof Plant ) counterOfSpecies++;
            }
            if (counterOfSpecies < getMaxOrganismOnArea()) {
                getCurrentArea().listToCreateOrganism.merge(GameLoader.PATH_TO_ORGANISM_FOLDER + "Grass", 1, Integer::sum);
            }
        }
    }
    @Override
    public String toString() {
        return "[WW"+getHealth()+"]  ";
    }
}
