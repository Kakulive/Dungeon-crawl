package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Heart extends Item{
    public static final int health = 5;

    protected Heart(Cell cell) {
        super(cell);
    }


    @Override
    public String getTileName() {
        return "heart";
    }

}
