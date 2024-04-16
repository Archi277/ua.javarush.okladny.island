package ua.javarush.island.gameisland;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class GameSettings {

    private  final int islandSizeX;
    private  final int islandSizeY;
    private  final int daysOfSimulation;
    private  final Map<String, Integer> residentsProperties;

    @JsonCreator
    public GameSettings(@JsonProperty("IslandSizeX") int islandSizeX,
                        @JsonProperty("IslandSizeY") int islandSizeY,
                        @JsonProperty("DaysOfSimulation") int daysOfSimulation,
                        @JsonProperty("residentsProperties") Map<String, Integer> residentsProperties){
        this.islandSizeX = islandSizeX;
        this.islandSizeY = islandSizeY;
        this.daysOfSimulation = daysOfSimulation;
        this.residentsProperties = residentsProperties;
    }

    public int getIslandSizeX() {
        return islandSizeX;
    }

    public int getIslandSizeY() {
        return islandSizeY;
    }

    public int getDaysOfSimulation() {
        return daysOfSimulation;
    }

    public Map<String, Integer> getResidentsProperties() {
        return residentsProperties;
    }
}
