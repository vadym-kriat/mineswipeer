package model.util.image;

public enum NumberType {
    ZERO("0.png"), ONE("1.png"), TWO("2.png"), THREE("3.png"), FOUR("4.png"),
    FIVE("5.png"), SIX("6.png"), SEVEN("7.png"), EIGHT("8.png"), NINE("9.png");

    private final String name;

    NumberType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
