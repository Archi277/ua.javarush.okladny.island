package ua.javarush.island.task;

import ua.javarush.island.gameisland.Area;

import java.util.concurrent.Callable;

public class TaskCheck implements Callable<Void>, Runnable{
    private Area area;

    public TaskCheck(Area area) {
        this.area = area;
    }

    @Override
    public Void call() throws Exception {
        area.checkAliveAnimalsOnArea();
        return null;
    }

    @Override
    public void run() {
        area.checkAliveAnimalsOnArea();
    }
}
