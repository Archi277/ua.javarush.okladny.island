package ua.javarush.island.entity;

import ua.javarush.island.gameisland.Area;

public abstract class Organism {

    private String ID;
    public Area currentArea;
    public boolean isHavePair = false;

    public int health;
    private int startWeigth;
    private int currentWeigth;
    public int maxOrganismOnArea;
    public int numberOfReproduceTime;
    public int chanceToReproduce;
    public abstract void play();
    public  abstract  void reproduce();



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

}
