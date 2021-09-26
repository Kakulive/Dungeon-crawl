package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Spider extends Actor {
    private int health = 2;
    private final int attack = 1;
    private final int id;

    public Spider(Cell cell) {
        super(cell);
        this.id = Actor.enemyIdCounter;
        Actor.enemyIdCounter++;
    }

    @Override
    public String getTileName() {
        return "spider";
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
    }

    @Override
    public int getId() {
        return id;
    }
}
