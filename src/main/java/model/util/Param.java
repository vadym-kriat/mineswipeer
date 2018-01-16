package model.util;

public class Param {

    private final int numOfMines;
    private final int heightMap;
    private final int widthMap;

    public Param(final int numOfMines, final int heightMap, final int widthMap) {
        this.numOfMines = numOfMines;
        this.heightMap = heightMap;
        this.widthMap = widthMap;
    }

    public int getNumOfMines() {
        return numOfMines;
    }

    public int getHeightMap() {
        return heightMap;
    }

    public int getWidthMap() {
        return widthMap;
    }
}
