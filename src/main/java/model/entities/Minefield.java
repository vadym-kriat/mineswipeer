package model.entities;

import model.interfaces.Generator;
import model.util.Param;

import java.util.Iterator;

public class Minefield {

    private Cell[][] map;
    private final int heightMap;
    private final int widthMap;
    private final int numOfMines;

    public static final int MIN_SIZE = 9;
    public static final int MIN_NUM_OF_MINES = 10;

    public Minefield(int heightMap, int widthMap, int numOfMines) {
        this.heightMap = heightMap;
        this.widthMap = widthMap;
        this.numOfMines = numOfMines;
    }

    public Minefield(final Param param) {
        this.heightMap = param.getHeightMap();
        this.widthMap = param.getWidthMap();
        this.numOfMines = param.getNumOfMines();
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

    public void setMap(Cell[][] map) {
        this.map = map;
    }

    public Cell getCell(int i, int j) {
        return map[i][j];
    }

    public void build(Generator generator, final int i, final int j) {
        map = generator.generate(numOfMines, heightMap, widthMap, i, j);
    }

    public int getNumEmptyCell() {
        return heightMap * widthMap - numOfMines;
    }

    public void openAll() {
        for (int i = 0; i < heightMap; i++) {
            for (int j = 0; j < widthMap; j++) {
                if (map[i][j].isHide()) {
                    map[i][j].setHide(false);
                }
            }
        }
    }
}