package ua.javarush.island.task;

import ua.javarush.island.gameisland.Area;

import java.util.concurrent.Callable;

public class TaskFeed implements Callable<Void>,Runnable{
    private Area area;

    public TaskFeed(Area area) {
        this.area = area;
    }

    @Override
    public Void call() throws Exception {
        area.feedOrganismOnArea();
        return null;
    }

    @Override
    public void run() {
        area.feedOrganismOnArea();
    }
}
