package main.tiles;

public enum FloorTileType {
    BARE(1),
    LOWCARPET(2),
    HIGHCARPET(3);

    private final int value;

    FloorTileType(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
