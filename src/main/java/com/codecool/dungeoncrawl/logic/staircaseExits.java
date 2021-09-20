package com.codecool.dungeoncrawl.logic;

public enum staircaseExits {
    DOWNSTAIRS_X(2),
    DOWNSTAIRS_Y(9),
    UPSTAIRS_X(5),
    UPSTAIRS_Y(13);

    private final int value;

    staircaseExits(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
