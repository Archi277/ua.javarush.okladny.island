package ua.javarush.island.task;

import ua.javarush.island.gameisland.Area;
import java.util.concurrent.Callable;

public class TaskCheck implements Callable<Void> {

    private final Area area;

    public TaskCheck(Area area) {
        this.area = area;
    }

    @Override
    public Void call() {
        area.checkAliveAnimalsOnArea();
        return null;
    }
}
