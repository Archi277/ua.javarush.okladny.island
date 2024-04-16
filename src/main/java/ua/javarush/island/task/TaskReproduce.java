package ua.javarush.island.task;

import ua.javarush.island.gameisland.Area;
import java.util.concurrent.Callable;

public class TaskReproduce implements Callable<Void>{
private Area area;

    public TaskReproduce(Area area) {
        this.area = area;
    }

    @Override
    public Void call() throws Exception {
        area.reproduceOrganismOnArea();
        return null;
    }
}
