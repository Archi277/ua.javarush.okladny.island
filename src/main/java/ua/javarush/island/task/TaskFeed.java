package ua.javarush.island.task;

import ua.javarush.island.gameisland.Area;
import java.util.concurrent.Callable;

public class TaskFeed implements Callable<Void>{

    private final Area area;

    public TaskFeed(Area area) {
        this.area = area;
    }

    @Override
    public Void call() {
        area.feedOrganismOnArea();
        return null;
    }
}
