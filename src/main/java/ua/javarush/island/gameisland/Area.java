package ua.javarush.island.gameisland;

import ua.javarush.island.entity.Organism;
import ua.javarush.island.gameloader.GameLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Area {
    private int coordinateX;
    private int coordinateY;
    private List<Organism> residents = new ArrayList<>();
    public Map<String, Integer> listToCreateOrganism = new HashMap<>();

    public Map<String, Integer> statisticOfResidents = new HashMap<>();

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
                result += organism.toString();
            }
            return result;
        } else return "[" + coordinateX + "," + coordinateY + "]  ";
    }

    public void createNewOrganism() {
        for (Map.Entry<String, Integer> set : listToCreateOrganism.entrySet()) {
            String classOrganismName = set.getKey();
            int quantityOrganism = set.getValue();
            for (int j = 0; j < quantityOrganism; j++) {
                GameLoader.createOrganism(classOrganismName, this, 1);
            }
        }
        listToCreateOrganism.clear();
    }

    public void refreshStatisticOfResidents() {
        for (Organism organism : residents) {
            statisticOfResidents.merge(organism.getClass().getTypeName(), 1, Integer::sum);
        }
        if(!statisticOfResidents.isEmpty()) {
            System.out.println(statisticOfResidents);
            System.out.println("["+getCoordinateX()+"]["+getCoordinateY()+"]___________________________________________");
        }
        statisticOfResidents.clear();
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

