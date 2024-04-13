package ua.javarush.island.gameisland;

import ua.javarush.island.entity.Organism;

public class Island {

    private static int sizeX;
    private static int sizeY;
    public  static Area[][] areas;

    public Island(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        areas = new Area[sizeX][sizeY];
        createAreas();
    }

    public static int getSizeX() {
        return sizeX;
    }

    public static int getSizeY() {
        return sizeY;
    }

    public static Area[][] getAreas() {
        return areas;
    }

    private void createAreas() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                areas[i][j] = new Area(new Coordinate(i, j));

            }
        }
    }


    public void showAreas() {
        System.out.println("=".repeat(150));
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                System.out.print(areas[i][j]);
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("=".repeat(150));
    }

}
