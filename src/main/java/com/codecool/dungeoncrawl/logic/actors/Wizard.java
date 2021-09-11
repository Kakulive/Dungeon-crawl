package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Wizard extends Actor {
    private int health = 12;
    private final int attack = 5;
    private final int id;

    public Wizard(Cell cell) {
        super(cell);
        this.id = Actor.enemyIdCounter;
        Actor.enemyIdCounter++;
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
        Cell nextCell = cell.getSpecificCell(dx, dy);
        cell.setActor(null);
        cell.setType(CellType.FLOOR);
        nextCell.setActor(this);
        nextCell.setType(CellType.ENEMY);
        cell = nextCell;
    }

    @Override
    public int getId() {
        return id;
    }
}
