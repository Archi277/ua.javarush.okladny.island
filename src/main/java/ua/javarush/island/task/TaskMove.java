package ua.javarush.island.task;

import ua.javarush.island.gameisland.Area;

import javax.lang.model.type.NullType;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class TaskMove implements Callable<Void> {
    private Area area;

    public TaskMove(Area area) {
        this.area = area;
    }

    @Override
    public Void call() throws Exception {

        Lock lockFrom = area.getLock();
        if (lockFrom.tryLock(20, TimeUnit.MILLISECONDS)) {
            try {
                area.moveOrganismOnArea();
            } finally {
                lockFrom.unlock();
            }
        }
        return null;
    }
}
