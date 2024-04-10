package ua.javarush.island.entity.plant;

import ua.javarush.island.entity.Organism;
import ua.javarush.island.gameloader.GameLoader;

public class Plant extends Organism {

    @Override
    public void play() {
        reproduce();
    }

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
                getCurrentArea().listToCreateOrganism.merge("ua.javarush.island.entity.plant.Grass", 1, Integer::sum);
            }
        }
    }
}
