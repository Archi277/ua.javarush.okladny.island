package ua.javarush.island.entity.plant;

public class Grass extends Plant{


    @Override
    public String toString() {
        return "[WW"+getHealth()+"]  ";
    }
}
