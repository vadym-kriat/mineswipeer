package view.elements;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import model.entities.Cell;
import model.entities.Game;
import model.entities.Minefield;
import model.interfaces.ClickListener;
import model.interfaces.GameActionListener;
import model.util.ImageLoader;
import model.util.image.CellType;
import org.apache.log4j.Logger;

public final class MinefieldGUI extends Canvas implements GameActionListener {

    private int cellSideLen;
    private final Game game;

    private ClickListener clickListener;
    private static final Logger LOGGER = Logger.getLogger(MinefieldGUI.class);

    public MinefieldGUI(Game game) {
        this.game = game;
        cellSideLen = ImageLoader.getInstance().getCellSideLen();
        init();
    }

    private void init() {
        resize();
        drawEmptyMinefield();
        setOnMouseClicked(event -> {
            MouseButton mouseButton = event.getButton();
            final int i = (int) (event.getY() / cellSideLen);
            final int j = (int) (event.getX() / cellSideLen);
            if (mouseButton == MouseButton.PRIMARY) {
                clickListener.open(i, j);
            } else if (mouseButton == MouseButton.SECONDARY) {
                clickListener.setFlag(i, j);
            }
        });
    }

    private void drawEmptyMinefield() {
        Minefield minefield = game.getMinefield();
        GraphicsContext gc = getGraphicsContext2D();
        ImageLoader im = ImageLoader.getInstance();
        for (int i = 0; i < minefield.getHeightMap(); i++) {
            for (int j = 0; j < minefield.getWidthMap(); j++) {
                final int y = i * cellSideLen;
                final int x = j * cellSideLen;
                gc.drawImage(im.getImage(CellType.HIDE.getName()), x, y);
            }
        }
    }

    private void drawAllFlags() {
        Minefield minefield = game.getMinefield();
        GraphicsContext gc = getGraphicsContext2D();
        ImageLoader im = ImageLoader.getInstance();
        for (int i = 0; i < minefield.getHeightMap(); i++) {
            for (int j = 0; j < minefield.getWidthMap(); j++) {
                final int y = i * cellSideLen;
                final int x = j * cellSideLen;
                final Cell cell = game.getMinefield().getCell(i, j);
                if (!cell.isFlag() && cell.isMine()) {
                    gc.drawImage(im.getImage(CellType.FLAG.getName()), x, y);
                }
            }
        }
    }

    private void drawCell(final int i, final int j) {
        GraphicsContext gc = getGraphicsContext2D();
        final Cell cell = game.getMinefield().getCell(i, j);
        final int y = i * cellSideLen;
        final int x = j * cellSideLen;

        ImageLoader im = ImageLoader.getInstance();
        if (cell.isHide()) {
            if (cell.isFlag()) {
                gc.drawImage(im.getImage(CellType.FLAG.getName()), x, y);
            } else {
                gc.drawImage(im.getImage(CellType.HIDE.getName()), x, y);
            }
        } else {
            if (cell.isMine()) {
                if (cell.isFlag()) {
                    gc.drawImage(im.getImage(CellType.DETECTED_MINE.getName()), x, y);
                } else {
                    gc.drawImage(im.getImage(CellType.MINE.getName()), x, y);
                }
            } else {
                switch (cell.getNumNear()) {
                    case 0:
                        gc.drawImage(im.getImage(CellType.EMPTY.getName()), x, y);
                        break;
                    case 1:
                        gc.drawImage(im.getImage(CellType.ONE.getName()), x, y);
                        break;
                    case 2:
                        gc.drawImage(im.getImage(CellType.TWO.getName()), x, y);
                        break;
                    case 3:
                        gc.drawImage(im.getImage(CellType.THREE.getName()), x, y);
                        break;
                    case 4:
                        gc.drawImage(im.getImage(CellType.FOUR.getName()), x, y);
                        break;
                    case 5:
                        gc.drawImage(im.getImage(CellType.FIVE.getName()), x, y);
                        break;
                    case 6:
                        gc.drawImage(im.getImage(CellType.SIX.getName()), x, y);
                        break;
                    case 7:
                        gc.drawImage(im.getImage(CellType.SEVEN.getName()), x, y);
                        break;
                    case 8:
                        gc.drawImage(im.getImage(CellType.EIGHT.getName()), x, y);
                        break;
                }
            }
        }
    }

    private void resize() {
        setHeight(game.getMinefield().getHeightMap() * cellSideLen);
        setWidth(game.getMinefield().getWidthMap() * cellSideLen);
    }

    private void clear() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
    }

    public void rescale() {
        cellSideLen = ImageLoader.getInstance().getCellSideLen();
        clear();
        resize();
        if (!game.isStart()) {
            drawEmptyMinefield();
        } else {
            drawMinefield();
        }
    }

    public void drawMinefield() {
        Minefield minefield = game.getMinefield();
        for (int i = 0; i < minefield.getHeightMap(); i++) {
            for (int j = 0; j < minefield.getWidthMap(); j++) {
                drawCell(i, j);
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void update(int i, int j) {
        drawCell(i, j);
    }

    @Override
    public void endGame(final int i, final int j) {
        setDisable(true);
        drawMinefield();
        final int y = i * cellSideLen;
        final int x = j * cellSideLen;
        getGraphicsContext2D().drawImage(ImageLoader.getInstance().getImage(CellType.EXPLODED_MINE.getName()), x, y);

    }

    @Override
    public void winGame() {
        setDisable(true);
        drawAllFlags();
    }
}
