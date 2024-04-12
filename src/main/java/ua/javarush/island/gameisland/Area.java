package ua.javarush.island.gameisland;

import ua.javarush.island.entity.Animal;
import ua.javarush.island.entity.Organism;
import ua.javarush.island.entity.Plant;
import ua.javarush.island.gameloader.GameLoader;
import ua.javarush.island.gameloader.GameStatus;

import java.util.*;


public class Area  {
    private int coordinateX;
    private int coordinateY;
    private List<Organism> residents = new ArrayList<>();
    public Map<String, Integer> listToCreateOrganism = new HashMap<>();

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public List<Organism> getResidents() {
        return residents;
    }

    public Area(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }



    @Override
    public String toString() {

        if (residents.size() != 0) {
            String result = "";
            for (Organism organism : residents) {
                if(organism != null) result += organism.toString();
            }
            return result;
        } else return "[" + coordinateX + "," + coordinateY + "]  ";
    }
    public void feedOrganismOnArea(){
        for(Organism residentsOrganism : residents){
            if (residentsOrganism instanceof Animal) {
                Animal animal = (Animal) residentsOrganism;
                animal.eat();
            }
        }
    }
    public void reproduceOrganismOnArea(){
        for(Organism residentsOrganism : residents){
            residentsOrganism.reproduce();
        }
    }

    public void moveOrganismOnArea(){

        List<Organism> residentsCopy = List.copyOf(residents);
        for(Organism residentsOrganism : residentsCopy){
            if (residentsOrganism instanceof Animal) {
                Animal animal = (Animal) residentsOrganism;
                animal.move();
            }
        }
    }

    public void createNewOrganismOnArea() {
        for (Map.Entry<String, Integer> set : listToCreateOrganism.entrySet()) {
            String classOrganismName = set.getKey();
            int quantityOrganism = set.getValue();
            for (int j = 0; j < quantityOrganism; j++) {
                GameLoader.createOrganism(classOrganismName, this, 1);
            }
        }
        listToCreateOrganism.clear();
    }
    public  void checkAliveAnimalsOnArea() {
        Iterator<Organism> organismIterator = residents.iterator();
        while (organismIterator.hasNext()) {

            Organism nextOrganism = organismIterator.next();
            if (nextOrganism instanceof Animal) {
                Animal animal = (Animal) nextOrganism;
                if (animal.getHealth() < 10 ) {
                    GameStatus.aliveOrganism.remove(animal);
                    organismIterator.remove();
                }

            }
            if (nextOrganism instanceof Plant) {
                if (nextOrganism.getCurrentWeigth() <= nextOrganism.getStartWeigth() / 10) {
                    GameStatus.aliveOrganism.remove(nextOrganism);
                    organismIterator.remove();
                }
            }
        }
    }

    public int getNumberAnimalOfThisClass(Organism organism){
        int numberAnimalOfThisClass= 0;
        String targetNameOrganism = organism.getClass().getTypeName();
        for(Organism residentsOrganism : residents){
            if(residentsOrganism.getClass().getTypeName().equals(targetNameOrganism)){
                numberAnimalOfThisClass++;
            }
        }
        return numberAnimalOfThisClass;
    }
}

