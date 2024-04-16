package ua.javarush.island.task;

import ua.javarush.island.gameisland.Area;

import java.util.concurrent.Callable;

public class TaskCreate implements Callable<Void> {
    private Area area;

    public TaskCreate(Area area) {
        this.area = area;
    }

    @Override
    public Void call() throws Exception {
        area.createNewOrganismOnArea();
        return null;
    }
}
