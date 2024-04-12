package ua.javarush.island.entity.organism;


public class Rabbit extends Herbivore {
    public Rabbit() {
    }


    @Override
    public String toString() {
        return "[\uD83D\uDC3B"+getID()+"]["+getHealth()+"]  ";
    }


}
