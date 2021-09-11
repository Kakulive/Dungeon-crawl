package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {
    private boolean hasKey;

    public Player(Cell cell) {
        super(cell);
        this.hasKey = false; //TODO change once inventory is implemented
    }

    public String getTileName() {
        return "player";
    }

    public boolean getHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }
}
