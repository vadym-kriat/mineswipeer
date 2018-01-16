package model.util;

public enum Difficult {
    EASY(new Param(10, 9, 9)),
    NORMAL(new Param(40, 16, 16)),
    HARD(new Param(99, 16, 30));

    private final Param param;

    Difficult(final Param param) {
        this.param = param;
    }

    public Param getValue() {
        return param;
    }
}
