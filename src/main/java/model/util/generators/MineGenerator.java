package model.util.generators;

import model.entities.Cell;
import model.interfaces.Generator;

import java.util.Random;

public class MineGenerator implements Generator {

    @Override
    public Cell[][] generate(final int reqMine, final int height, final int width, int i, int j) {
        final Cell[][] map = new Cell[height][width];
        int placedMine = 0;

        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                map[k][l] = new Cell();
            }
        }

        Random random = new Random();
        while (placedMine < reqMine) {
            final int genI = random.nextInt(height);
            final int getJ = random.nextInt(width);
            if (genI == i && getJ == j) {
                continue;
            }
            if (!map[genI][getJ].isMine()) {
                map[genI][getJ].setMine(true);
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        if (k + genI != -1 && l + getJ != -1 && k + genI < height && l + getJ < width) {
                            map[k + genI][l + getJ].incNearMines();
                        }
                    }
                }
                placedMine++;
            }
        }
        return map;
    }
}
