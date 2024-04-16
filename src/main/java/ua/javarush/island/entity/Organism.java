package ua.javarush.island.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ua.javarush.island.gameisland.Area;

import java.util.Map;
import java.util.Objects;

public abstract class Organism {

    private  String ID;
    protected Area    currentArea;
    protected boolean isHavePair = false;
    protected int chanceToReproduce;
    protected int maxOrganismOnArea;
    protected int numberOfReproduceTime;
    protected int startWeigth;
    protected int currentWeigth;
    protected int health;

    public  abstract  void reproduce();
    public  abstract  void move();
    public  abstract  void eat();


    public String getID() {
        return ID;
    }

    public Area getCurrentArea() {
        return currentArea;
    }

    public int getHealth() {
        return health;
    }

    public int getStartWeigth() {
        return startWeigth;
    }

    public int getCurrentWeigth() {
        return currentWeigth;
    }

    public int getMaxOrganismOnArea() {
        return maxOrganismOnArea;
    }

    public int getNumberOfReproduceTime() {
        return numberOfReproduceTime;
    }

    public int getChanceToReproduce() {
        return chanceToReproduce;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setCurrentWeigth(int currentWeigth) {
        this.currentWeigth = currentWeigth;
    }

    public void setNumberOfReproduceTime(int numberOfReproduceTime) {
        this.numberOfReproduceTime = numberOfReproduceTime;
    }

    public void setCurrentArea(Area currentArea) {
        this.currentArea = currentArea;
    }

    public void setHavePair(boolean havePair) {
        isHavePair = havePair;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Organism organism = (Organism) object;
        return Objects.equals(getID(), organism.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

}
