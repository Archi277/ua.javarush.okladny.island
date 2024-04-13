package ua.javarush.island.entity;

import ua.javarush.island.entity.Organism;
import ua.javarush.island.gameisland.Area;
import ua.javarush.island.gameisland.Coordinate;
import ua.javarush.island.gameisland.Direction;
import ua.javarush.island.gameisland.Island;


import java.util.Map;
import java.util.Objects;
import java.util.SplittableRandom;

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

        Coordinate newCoordinate = currentArea.getCoordinate();

        Direction direction = choseDirection(random);

        int randomAxisValue = random.nextInt(-speedOfMove, speedOfMove + 1);

        if (direction == Direction.Horizontal) {

            int newAxisValue = newCoordinate.getX() + randomAxisValue;
            newCoordinate.setX(correctionCoordinate(newAxisValue, direction));

        }
        if (direction == Direction.Vertical) {

            int newAxisValue = newCoordinate.getY() + randomAxisValue;
            newCoordinate.setY(correctionCoordinate(newAxisValue, direction));
        }
        Area newArea = Island.areas[newCoordinate.getX()][newCoordinate.getY()];
       // Island.areas[currentArea.getCoordinate().getX()][currentArea.getCoordinate().getY()].getResidents().remove(this);
        currentArea.removeOrganism(this);

        currentArea = newArea;

        currentArea.getResidents().add(this);
        //TODO add check max animal on area

    }

    private int correctionCoordinate(int newCoordinateValue, Direction direction) {
        if(direction == Direction.Horizontal) {
            if (newCoordinateValue > Island.getSizeX() - 1) {
                return  0;
            } else if (newCoordinateValue < 0) {
                return  Island.getSizeX() - 1;
            }
            return newCoordinateValue;
        }


        if (direction == Direction.Vertical) {
            if (newCoordinateValue > Island.getSizeY() - 1) {
                return  0;
            } else if (newCoordinateValue < 0) {
                return  Island.getSizeY() - 1;
            }
            return newCoordinateValue;
        }
        return newCoordinateValue;
    }

    private Direction choseDirection(SplittableRandom random){
        int randomDir = random.nextInt(0, 9 + 1);
        if (randomDir > 5) {
            return  Direction.Horizontal;
        } else return Direction.Vertical;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Animal animal = (Animal) object;
        return Objects.equals(getID(), animal.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }
}
