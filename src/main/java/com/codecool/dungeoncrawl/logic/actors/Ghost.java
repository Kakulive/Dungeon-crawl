package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Ghost extends Actor {
    private int health = 3;
    private final int attack = 1;
    private final int id;
    private CellType nextMoveCellType = CellType.FLOOR;

    public Ghost(Cell cell) {
        super(cell);
        this.id = Actor.enemyIdCounter;
        Actor.enemyIdCounter++;
    }

    @Override
    public String getTileName() {
        return "ghost";
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
        Cell nextCell = cell.getNeighbor(dx, dy);
        CellType nextCellType = nextCell.getType();
        cell.setActor(null);
        cell.setType(nextMoveCellType);
        this.nextMoveCellType = nextCellType;
        nextCell.setActor(this);
        nextCell.setType(CellType.ENEMY);
        cell = nextCell;
    }

    @Override
    public int getId() {
        return id;
    }
}
