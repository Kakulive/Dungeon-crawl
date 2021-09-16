package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    ENEMY("enemy"),
    SWORD("sword"),
    SHIELD("shield"),
    OPEN_DOOR("open_door"),
    CLOSED_DOOR("closed_door"),
    KEY("key"),
    DOWN_STAIRS("down_stairs"),
    UP_STAIRS("up_stairs"),
    HEART("heart"),
    CANDLE("candle"),
    GRASS("grass"),
    BONES("bones"),
    ;

    private final String tileName;


    CellType(String tileName) {
        this.tileName = tileName;
    }


    public String getTileName() {
        return tileName;
    }
}
