package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.utils.Randomizer;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private List<Actor> enemiesList;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.enemiesList = new ArrayList<>();
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }
    public void moveEnemies(GameMap map) {
        Randomizer randomizer = new Randomizer();
        List<Actor> enemiesList = map.getEnemiesList();
        for (Actor enemy: enemiesList) {
            int dx = randomizer.rollRandomMove();
            int dy = randomizer.rollRandomMove();
            enemy.move(dx, dy);
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Actor> getEnemiesList() {
        return enemiesList;
    }

    public void addEnemyToList(Actor enemy) {
        this.enemiesList.add(enemy);
    }
}
