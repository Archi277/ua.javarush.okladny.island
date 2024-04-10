package ua.javarush.island.entity.animal.predator;

public class Wolf extends Predator {

    public Wolf() {
    }

    @Override
    public String toString() {
        return "[\uD83D\uDC3A"+getID()+"]["+getHealth()+"]  ";
    }

}
