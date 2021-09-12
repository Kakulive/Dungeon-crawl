package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item {

    protected Key(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return null;
    }
}
