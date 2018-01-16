package model.entities;

import model.interfaces.ClickListener;
import model.interfaces.FlagListener;
import model.interfaces.GameActionListener;
import model.interfaces.TimeListener;
import model.util.Difficult;
import model.util.Param;
import model.util.Point;
import model.util.Timer;
import model.util.generators.MineGenerator;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

public class Game implements ClickListener {
    private final Minefield minefield;
    private final Timer timer;
    private boolean isWin;
    private int flags;
    private int openedCell;
    private boolean isStart;

    private GameActionListener gameActionListener;
    private FlagListener flagListener;

    private static final Logger LOGGER = Logger.getLogger(Game.class);

    public Game(Difficult difficult) {
        this(difficult.getValue());
    }

    public Game(Param param) {
        minefield = new Minefield(param);
        timer = new Timer();
        isWin = false;
        isStart = false;
        flags = minefield.getNumOfMines();
    }

    public Game(Minefield minefield, Timer timer) {
        this.minefield = minefield;
        this.timer = timer;
    }

    private void start(final int i, final int j) {
        LOGGER.info("Start game...");
        isStart = true;
        LOGGER.info("Build minefield.");
        minefield.build(new MineGenerator(), i, j);
        LOGGER.info("Start the timer.");
        timer.start();
    }

    private void openEmpty(final int y, final int x) {
        final Queue<Point> cellQueue = new LinkedList<>();
        cellQueue.add(new Point(y, x));
        do {
            final Point p = cellQueue.poll();
            final int i = p.getI();
            final int j = p.getJ();
            for (int k = -1; k < 2; k++) {
                for (int l = -1; l < 2; l++) {
                    if (k + i != -1 && l + j != -1 && k + i < minefield.getHeightMap() && l + j < minefield.getWidthMap()) {
                        Cell c = minefield.getCell(k + i, l + j);
                        if (c.isHide()) {
                            if (!c.isFlag()) {
                                c.setHide(false);
                                openedCell++;
                                LOGGER.info("Open cell i:" + (i + k) + " j:" + (j + l));
                                checkWin();
                                gameActionListener.update(k + i, l + j);
                                if (isWin) {
                                    return;
                                }
                                if (c.isEmpty()) {
                                    cellQueue.add(new Point(k + i, l + j));
                                }
                            }
                        }
                    }
                }
            }
        } while (!cellQueue.isEmpty());
    }

    private void checkWin() {
        if (openedCell == minefield.getNumEmptyCell()) {
            isWin = true;
            timer.stop();
            LOGGER.info("Win. Stop timer.");
            gameActionListener.winGame();
            flagListener.updateFlag(flags = 0);
        }
    }

    @Override
    public void open(final int i, final int j) {
        if (!isStart) {
            start(i, j);
        }
        LOGGER.info("Click i:" + i + " j:" + j);
        final Cell cell = minefield.getCell(i, j);
        if (cell.isHide()) {
            if (cell.isFlag()) {
                cell.setFlag(false);
                gameActionListener.update(i, j);
                flagListener.updateFlag(++flags);
            } else {
                cell.setHide(false);
                if (cell.isMine()) {
                    isWin = false;
                    timer.stop();
                    LOGGER.info("Game over. Stop the timer.");
                    minefield.openAll();
                    gameActionListener.endGame(i, j);
                    return;
                }
                openedCell++;
                checkWin();
                gameActionListener.update(i, j);
                if (cell.isEmpty()) {
                    openEmpty(i, j);
                }
            }
        }
    }

    @Override
    public void setFlag(int i, int j) {
        if (isStart && flags > 0) {
            final Cell cell = minefield.getCell(i, j);
            if (!cell.isFlag() && cell.isHide()) {
                minefield.getCell(i, j).setFlag(true);
                LOGGER.info("Set flag i:" + i + " j:" + j);
                gameActionListener.update(i, j);
                flagListener.updateFlag(--flags);
            }
        }
    }

    public Minefield getMinefield() {
        return minefield;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public void setOpenedCell(int openedCell) {
        this.openedCell = openedCell;
    }

    public void setGameActionListener(GameActionListener gameActionListener) {
        this.gameActionListener = gameActionListener;
    }

    public void setTimeListener(TimeListener timeListener) {
        this.timer.setTimeListener(timeListener);
    }

    public void setFlagListener(FlagListener flagListener) {
        this.flagListener = flagListener;
        flagListener.updateFlag(flags);
    }
}
