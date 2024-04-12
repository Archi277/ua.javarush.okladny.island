package ua.javarush.island.task;

import ua.javarush.island.gameisland.Area;

import javax.lang.model.type.NullType;
import java.util.concurrent.Callable;

public class TaskMove implements Callable<Void>,Runnable {
    private Area area;

    public TaskMove(Area area) {
        this.area = area;
    }



    @Override
    public Void call() throws Exception {
        area.moveOrganismOnArea();
        return null;
    }

    @Override
    public void run() {
        area.moveOrganismOnArea();
    }
}

