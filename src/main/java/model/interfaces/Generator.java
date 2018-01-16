package model.interfaces;

import model.entities.Cell;
import model.util.Param;

public interface Generator {

    Cell[][] generate(int numOfMines, int height, int width, int i, int j);

}
