package model.util;

import javafx.scene.image.Image;
import model.util.image.CellType;
import model.util.image.NumberType;
import model.util.image.Scale;

import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    private static volatile ImageLoader instance;

    private Map<String, Image> mapImage;
    private Scale scale;

    private static final String ROOT_CELL_DIRECTORY = "/textures/";
    private static final String ROOT_NUMBER_DIRECTORY = "/textures/numbers/";
    private static final int CELL_SIDE = 16;
    private static final int NUM_HEIGHT = 23;
    private static final int NUM_WIDTH = 13;

    private ImageLoader() {
        mapImage = new HashMap<>();
        scale = Scale.CELL_LG_LEN;
        load();
    }

    private void load() {
        mapImage.clear();
        for (CellType type : CellType.values()) {
            mapImage.put(type.getName(), new Image(ROOT_CELL_DIRECTORY + type.getName(),
                    CELL_SIDE * scale.getValue(), CELL_SIDE * scale.getValue(),
                    false, false));
        }
        for (NumberType type : NumberType.values()) {
            mapImage.put(type.getName(), new Image(ROOT_NUMBER_DIRECTORY + type.getName(),
                    NUM_WIDTH * scale.getValue(), NUM_HEIGHT * scale.getValue(),
                    false, false));
        }
    }

    public void scaleTo(Scale scale) {
        this.scale = scale;
        load();
    }

    public Image getImage(String key) {
        return mapImage.get(key);
    }

    public int getCellSideLen() {
        return (int) (CELL_SIDE * scale.getValue());
    }

    public int getNumHeight() {
        return (int) (NUM_HEIGHT * scale.getValue());
    }

    public int getNumWidth() {
        return (int) (NUM_WIDTH * scale.getValue());
    }

    public static ImageLoader getInstance() {
        ImageLoader localInstance = instance;
        if (localInstance == null) {
            synchronized (ImageLoader.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }


}
