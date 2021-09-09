package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Sword extends Item{
    private int attack;

    public Sword(Cell cell) {
        super(cell);
        this.attack = 10;
    }

    @Override
    public String getTileName() {
        return "sword";
    }


}
