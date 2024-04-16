package ua.javarush.island.entity;

import ua.javarush.island.gameisland.Area;
import ua.javarush.island.gameisland.Coordinate;
import ua.javarush.island.gameisland.Direction;
import ua.javarush.island.gameisland.Island;
import java.util.Map;
import java.util.SplittableRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public abstract class Animal extends Organism {

    private boolean isMoved = false;
    protected int speedOfMove;
    protected int foodWeigthForSaturation;
    protected int currentFoodWeigthForSaturation;
    protected Map<String, Integer> listOfEatableOrganism;

    public Area getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(Area currentArea) {
        this.currentArea = currentArea;
    }

    public void setSpeedOfMove(int speedOfMove) {
        this.speedOfMove = speedOfMove;
    }

    public void setFoodWeigthForSaturation(int foodWeigthForSaturation) {
        this.foodWeigthForSaturation = foodWeigthForSaturation;
    }

    public void setListOfEatableOrganism(Map<String, Integer> listOfEatableOrganism) {
        this.listOfEatableOrganism = listOfEatableOrganism;
    }

    public void move() {

        // If animal moved in forward area, method move() will call again. As is  result in one iteration
        // animal can make any times steps. For this reason used isMoved flag.

        if (!isMoved) {
            SplittableRandom random = new SplittableRandom();

            Coordinate newCoordinate = new Coordinate(0, 0);
            newCoordinate.setX(currentArea.getCoordinate().getX());
            newCoordinate.setY(currentArea.getCoordinate().getY());

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

            Area desiredArea = Island.areas[newCoordinate.getX()][newCoordinate.getY()];

            tryToMoveAnimalToDesiredArea(desiredArea);

        } else isMoved = false;
    }

    private void tryToMoveAnimalToDesiredArea(Area desiredArea){

        Lock lockTo = desiredArea.getLock();

        try {
            if (desiredArea.getNumberOrganismOfThisClass(this) < maxOrganismOnArea) {
                if (lockTo.tryLock(40, TimeUnit.MILLISECONDS)) {
                    try {
                        isMoved = true;
                        currentArea.removeOrganism(this);
                        currentArea = desiredArea;
                        currentArea.getResidents().add(this);
                    } finally {
                        lockTo.unlock();
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int correctionCoordinate(int newCoordinateValue, Direction direction) {

        if (direction == Direction.Horizontal) {
            if (newCoordinateValue > Island.getSizeX() - 1) {
                return 0;
            } else if (newCoordinateValue < 0) {
                return Island.getSizeX() - 1;
            }
            return newCoordinateValue;
        }

        if (direction == Direction.Vertical) {
            if (newCoordinateValue > Island.getSizeY() - 1) {
                return 0;
            } else if (newCoordinateValue < 0) {
                return Island.getSizeY() - 1;
            }
            return newCoordinateValue;
        }
        return newCoordinateValue;
    }

    private Direction choseDirection(SplittableRandom random) {
        int randomDir = random.nextInt(0, 9 + 1);
        if (randomDir > 5) {
            return Direction.Horizontal;
        } else return Direction.Vertical;
    }

    public boolean getChanceToEat(String name) {
        SplittableRandom random = new SplittableRandom();
        int probabilityEatable = listOfEatableOrganism.get(name);
        return random.nextInt(1, 101) <= probabilityEatable;
    }

    public boolean getReproduceProbability() {
        SplittableRandom random = new SplittableRandom();
        return random.nextInt(1, 101) <= getChanceToReproduce();
    }
}
