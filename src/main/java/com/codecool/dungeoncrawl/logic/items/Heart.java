package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Heart extends Item{
    private static final int health = 5;

    public Heart(Cell cell) {
        super(cell);
    }

    public static int getHealth() {
        return health;
    }

    @Override
    public String getTileName() {
        return "heart";
    }

}
