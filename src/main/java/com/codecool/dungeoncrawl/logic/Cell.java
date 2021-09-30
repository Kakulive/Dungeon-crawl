package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private GameMap gameMap;
    private Item item;
    private int x, y;

    public Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Item getItem() {return item;}

    public void setItem(Item item) {this.item = item;}

    public Actor getActor() {return actor;}

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    public Cell getSpecificCell(int x, int y) {
        return gameMap.getCell(x,y);
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell setX(int x) { this.x = x; return this; }

    public Cell setY(int y) { this.y = y; return  this; }

    public GameMap getGameMap() {
        return gameMap;
    }
}
