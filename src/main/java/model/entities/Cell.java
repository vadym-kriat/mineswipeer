package model.entities;

public class Cell {
    private boolean isMine;
    private boolean isHide;
    private boolean isFlag;
    private int numNear;

    public Cell() {
        this(0, false);
    }

    public Cell(int numNear, boolean isMine) {
        this.numNear = numNear;
        this.isMine = isMine;
        this.isHide = true;
    }

    public void incNearMines() {
        numNear++;
    }

    public int getNumNear() {
        return numNear;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    public boolean isHide() {
        return isHide;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    @Override
    public String toString() {
        return isMine ? "M" : String.valueOf(numNear);
    }

    public boolean isEmpty() {
        return numNear == 0;
    }
}
