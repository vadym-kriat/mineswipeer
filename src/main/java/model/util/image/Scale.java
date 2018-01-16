package model.util.image;

public enum Scale {
    CELL_SM_LEN(0.5f), CELL_MD_LEN(1), CELL_LG_LEN(1.5f), CELL_XL_LEN(2);

    private final float value;

    Scale(final float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
