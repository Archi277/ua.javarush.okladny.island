package ua.javarush.island.gameloader;

import java.util.Map;

public class GameSettings {
    public  int IslandSize;
    public  Map<String, Integer> residentsProperties;

    public GameSettings(){
    }

    public int getIslandSize() {
        return IslandSize;
    }

    public Map<String, Integer> getResidentsProperties() {
        return residentsProperties;
    }
}
