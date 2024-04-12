package ua.javarush.island.entity;

import ua.javarush.island.entity.Organism;

public abstract class Plant extends Organism {

    @Override
    public void play() {
        reproduce();
    }
}
