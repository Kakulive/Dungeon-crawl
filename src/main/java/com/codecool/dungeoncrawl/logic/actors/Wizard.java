package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Wizard extends Actor {
    private int health = 12;
    private final int attack = 5;

    public Wizard(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "wizard";
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
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void move(int dx, int dy) {
        System.out.println("I am moving!");
        // TODO fix me

    }
}
