package ua.javarush.island.task;

import ua.javarush.island.gameisland.Area;
import ua.javarush.island.gameisland.Island;
import java.util.ArrayList;
import java.util.List;

public class ListOfTaskCreator {

    public   List<TaskReproduce> taskReproduceList = new ArrayList<>();
    public   List<TaskCreate> taskCreateList = new ArrayList<>();
    public   List<TaskFeed> taskFeedList = new ArrayList<>();
    public   List<TaskMove> taskMoveList = new ArrayList<>();
    public   List<TaskCheck> taskChecksList = new ArrayList<>();

    public ListOfTaskCreator() {
        createTask();
    }

    public  void createTask(){

        for (int k = 0; k < Island.getSizeX(); k++) {
            for (int j = 0; j < Island.getSizeY(); j++) {
                Area area = Island.getAreas()[k][j];
                taskReproduceList.add( new TaskReproduce(area));
                taskCreateList.add( new TaskCreate(area));
                taskFeedList.add( new TaskFeed(area));
                taskMoveList.add( new TaskMove(area));
                taskChecksList.add( new TaskCheck(area));
            }
        }
    }
}
