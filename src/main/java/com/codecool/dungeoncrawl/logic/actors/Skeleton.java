package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    private int health = 6;
    private final int attack = 2;
    private final int id;

    public Skeleton(Cell cell) {
        super(cell);
        this.id = Actor.enemyIdCounter;
        Actor.enemyIdCounter++;
    }

    @Override
    public String getTileName() {
        return "skeleton";
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

    @Override
    public int getId() {
        return id;
    }
}
