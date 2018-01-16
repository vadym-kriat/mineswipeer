package model.util.image;

public enum CellType {
    ONE("1-cell.png"), TWO("2-cell.png"), THREE("3-cell.png"), FOUR("4-cell.png"), FIVE("5-cell.png"),
    SIX("6-cell.png"), SEVEN("7-cell.png"), EIGHT("8-cell.png"), MINE("mine.png"), EMPTY("empty.png"),
    HIDE("hide.png"), FLAG("flag.png"), DETECTED_MINE("detected_mine.png"),
    EXPLODED_MINE("exploded_mine.png");

    private final String name;

    CellType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
