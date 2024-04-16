package ua.javarush.island.gameisland;

import ua.javarush.island.entity.Organism;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;


public class Area {
    private final Coordinate coordinate;
    private List<Organism> residents = new ArrayList<>();
    private Map<String, Integer> listToCreateOrganism = new HashMap<>();

    private Lock lock = new ReentrantLock();

    public Area(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Map<String, Integer> getListToCreateOrganism() {
        return listToCreateOrganism;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public List<Organism> getResidents() {
        return residents;
    }

    public Lock getLock(){
        return this.lock;
    }

    @Override
    public String toString() {

        if (residents.size() != 0) {
            String result = "";
            for (Organism organism : residents) {
                if (organism != null) result += organism.toString();
            }
            return result;
        } else return "[" + coordinate.getX() + "," + coordinate.getY() + "]  ";
    }

    public void feedOrganismOnArea() {

        List<Organism> residentsCopy = List.copyOf(residents);
        residentsCopy.forEach(organism -> organism.eat());
    }

    public void reproduceOrganismOnArea() {

        residents.forEach(organism -> organism.reproduce());

    }

    public void moveOrganismOnArea() {

        List<Organism> residentsCopy = List.copyOf(residents);
        residentsCopy.forEach(organism -> organism.move());


    }

    public void createNewOrganismOnArea() {

        listToCreateOrganism.forEach((className, quantity) -> OrganismFactory.createOrganism(className, this, quantity));
        listToCreateOrganism.forEach((className, quantity) -> Island.newbornStatistics.merge(OrganismFactory.getShortNameClassFromFullName(className), quantity,Integer::sum));
        listToCreateOrganism.clear();
    }

    public void checkAliveAnimalsOnArea() {

        List<Organism> residentsCopy = List.copyOf(residents);
        residentsCopy.stream().filter(o -> o.getHealth() < 10).peek(this::removeOrganism).peek(o -> {
            Island.aliveOrganism.remove(o);
            Island.deathStatistics.merge(o.getClass().getSimpleName(), 1, Integer::sum);
        }).collect(Collectors.toList());

        //TODO delete this after test

//        Iterator<Organism> organismIterator = residents.iterator();
//        while (organismIterator.hasNext()) {
//
//            Organism nextOrganism = organismIterator.next();
//            if (nextOrganism instanceof Animal) {
//                Animal animal = (Animal) nextOrganism;
//
//                if (animal.getHealth() < 10 ) {
//                    GameStatus.aliveOrganism.remove(animal);
//                    organismIterator.remove();
//                }
//
//            }
//            if (nextOrganism instanceof Plant) {
//                if (nextOrganism.getCurrentWeigth() <= nextOrganism.getStartWeigth() / 10) {
//                    GameStatus.aliveOrganism.remove(nextOrganism);
//                    organismIterator.remove();
//                }
//            }
//        }
    }

    public int getNumberOrganismOfThisClass(Organism organism) {

        return (int) residents.stream().filter(o -> o.getClass().getTypeName().equals(organism)).count();

    }

    public void removeOrganism(Organism organism) {
        residents.remove(organism);
    }
}

