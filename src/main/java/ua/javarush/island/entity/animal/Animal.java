package ua.javarush.island.entity.animal;

import ua.javarush.island.entity.Organism;
import ua.javarush.island.gameisland.Area;
import ua.javarush.island.gameisland.Island;


import java.util.Map;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Organism {

    public int speedOfMove;
    public int foodWeigthForSaturation;
    public int currentFoodWeigthForSaturation;
    public Map<String, Integer> listOfEatableOrganism;



    public Area getCurrentArea() {
        return currentArea;
    }
    public void setCurrentArea(Area currentArea) {
        this.currentArea = currentArea;
    }

    public void eat() {
    }

    public void move() {
        SplittableRandom random = new SplittableRandom();
        int newCoordinateY, newCoordinateX;

        int randomDir = random.nextInt(0, 9 + 1);
        if (randomDir > 5) {
            int randomX = random.nextInt(-speedOfMove, speedOfMove + 1);
            newCoordinateX = currentArea.getCoordinateX() + randomX;
            newCoordinateY = currentArea.getCoordinateY();
        } else {
            int randomY = random.nextInt(-speedOfMove, speedOfMove + 1);
            newCoordinateY = currentArea.getCoordinateY() + randomY;
            newCoordinateX = currentArea.getCoordinateX();
        }

        if (newCoordinateX > Island.getSizeX() - 1) {
            newCoordinateX = newCoordinateX - Island.getSizeX() - 1;
        }
        if (newCoordinateY > Island.getSizeY() - 1) {
            newCoordinateY = newCoordinateY - Island.getSizeY() - 1;
        }
        if (newCoordinateX < 0) {
            newCoordinateX = (Island.getSizeX() - 1 - newCoordinateX);
        }
        if (newCoordinateY < 0) {
            newCoordinateY = (Island.getSizeY() - 1 - newCoordinateY);
        }
        if (newCoordinateX >= Island.getSizeX()) {
            newCoordinateX = Island.getSizeX() - 1;
        }
        if (newCoordinateY >= Island.getSizeY()) {
            newCoordinateY = Island.getSizeY() - 1;
        }

        Island.areas[currentArea.getCoordinateX()][currentArea.getCoordinateY()].getResidents().remove(this);

        currentArea = Island.areas[newCoordinateX][newCoordinateY];
        currentArea.getResidents().add(this);
        //TODO add chekinck max animal on area

    }

    @Override
    public void play() {
        eat();
        reproduce();
        move();
    }

    public boolean getChanceToEat(String name){
        SplittableRandom random = new SplittableRandom();
        int probabilityEatable = listOfEatableOrganism.get(name);
        if (random.nextInt(1, 101) <= probabilityEatable) return true;
        else return false;
    }
    public boolean getReproduceProbability(){
        SplittableRandom random = new SplittableRandom();
        if (random.nextInt(1, 101) <= getChanceToReproduce()) return true;
        else return false;
    }
}
