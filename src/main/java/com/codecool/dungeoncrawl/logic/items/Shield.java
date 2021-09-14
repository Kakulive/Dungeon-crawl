package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Shield extends Item{
    private static final int armor = 5;

    public Shield(Cell cell) {
        super(cell);
    }


    @Override
    public String getTileName() {
        return "shield";
    }

    public static int getArmor() {
        return armor;
    }
}
