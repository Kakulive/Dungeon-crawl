package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Spider extends Actor {
    private int health = 2;
    private final int attack = 1;

    public Spider(Cell cell) {
        super(cell);
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
        System.out.println("I am moving!");
        // TODO fix me
    }
}
