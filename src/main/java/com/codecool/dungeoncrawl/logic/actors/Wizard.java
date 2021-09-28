package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Wizard extends Actor {
    private int health = 12;
    private final int attack = 5;
    private final int id;
    private int moveCounter = 3;
    private final int wizardIsMoving = 1;
    private final int moveFrequency = 3;

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
    public void move(int x, int y) {
        if (moveCounter == wizardIsMoving) {
        Cell nextCell = cell.getSpecificCell(x, y);
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
        this.moveCounter = moveFrequency;
    } else {
            moveCounter--;
        }
    }

    @Override
    public int getId() {
        return id;
    }

}
