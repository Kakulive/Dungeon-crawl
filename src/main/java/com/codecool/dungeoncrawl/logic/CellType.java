package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    ENEMY("enemy"),
    ITEM("sword"),
    OPEN_DOOR("open_door"),
    CLOSED_DOOR("closed_door"),
    KEY("key"),
    STAIRS("stairs"),
    HEART("heart")
    ;

    private final String tileName;


    CellType(String tileName) {
        this.tileName = tileName;
    }


    public String getTileName() {
        return tileName;
    }
}
