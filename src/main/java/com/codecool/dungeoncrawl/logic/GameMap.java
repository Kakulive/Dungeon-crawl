package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.utils.Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameMap {
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private final String mapName;

    private Player player;
    private List<Actor> enemiesList;

    private final Randomizer randomizer = new Randomizer();

    public GameMap(int width, int height, CellType defaultCellType, String mapName) {
        this.width = width;
        this.height = height;
        this.mapName = mapName;
        enemiesList = new ArrayList<>();
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public void moveEnemies(GameMap map) {
        List<Actor> enemiesList = map.getEnemiesList();
        for (Actor enemy : enemiesList) {
            int x = enemy.getX();
            int y = enemy.getY();
            int dx, dy;
            if (Objects.equals(enemy.getTileName(), "spider")) {
                int[] moveCoordinates = getSpiderMoveCoordinates(map, x, y);
                dx = moveCoordinates[0];
                dy = moveCoordinates[1];
            } else if (Objects.equals(enemy.getTileName(), "wizard")) {
                int[] moveCoordinates = getWizardMoveCoordinates(map, x, y);
                dx = moveCoordinates[0];
                dy = moveCoordinates[1];
            } else if (Objects.equals(enemy.getTileName(), "ghost")) {
                int[] moveCoordinates = getGhostMoveCoordinates(map, x, y);
                dx = moveCoordinates[0];
                dy = moveCoordinates[1];
            } else {
                dx = 0;
                dy = 0;
            }
            enemy.move(dx, dy);
        }
    }

    private int[] getSpiderMoveCoordinates(GameMap map, int x, int y) {
        int dx = randomizer.rollRandomMove();
        int dy = randomizer.rollRandomMove();
        while (!isSpiderMoveValid(map, x, y, dx, dy)) {
            dx = randomizer.rollRandomMove();
            dy = randomizer.rollRandomMove();
        }
        int[] moveCoordinates = new int[2];
        moveCoordinates[0] = dx;
        moveCoordinates[1] = dy;
        return moveCoordinates;
    }

    private boolean isSpiderMoveValid(GameMap map, int x, int y, int dx, int dy) {
        CellType cellType = map.getCell(x + dx, y + dy).getType();
        return map.getCell(x + dx, y + dy).getActor() == null && cellType == CellType.FLOOR;
    }

    private int[] getGhostMoveCoordinates(GameMap map, int x, int y) {
        int dx = randomizer.rollRandomMove();
        int dy = randomizer.rollRandomMove();
        while (!isGhostMoveValid(map, x, y, dx, dy)) {
            dx = randomizer.rollRandomMove();
            dy = randomizer.rollRandomMove();
        }
        int[] moveCoordinates = new int[2];
        moveCoordinates[0] = dx;
        moveCoordinates[1] = dy;
        return moveCoordinates;
    }

    private boolean isGhostMoveValid(GameMap map, int x, int y, int dx, int dy) {
        if (x + dx > (width - 1) || x + dx < 0 || y + dy > (height - 1) || y + dy < 0) {
            return false;
        }
        CellType cellType = map.getCell(x + dx, y + dy).getType();
        return map.getCell(x + dx, y + dy).getActor() == null && (cellType == CellType.FLOOR ||
                cellType == CellType.WALL || cellType == CellType.EMPTY);
    }

    private int[] getWizardMoveCoordinates(GameMap map, int x, int y) {
        int newX = randomizer.getRandomNumber(width);
        int newY = randomizer.getRandomNumber(height);
        while (!isWizardMoveValid(map, newX, newY)) {
            newX = randomizer.getRandomNumber(width);
            newY = randomizer.getRandomNumber(height);
        }
        int[] moveCoordinates = new int[2];
        moveCoordinates[0] = newX;
        moveCoordinates[1] = newY;
        return moveCoordinates;
    }

    private boolean isWizardMoveValid(GameMap map, int newX, int newY) {
        CellType cellType = map.getCell(newX, newY).getType();
        return map.getCell(newX, newY).getActor() == null && cellType == CellType.FLOOR;
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
        enemiesList.add(enemy);
    }

    public void removeEnemyFromList(int id) {
        enemiesList.removeIf(enemy -> enemy.getId() == id);
    }

    public String getMapName() {
        return mapName;
    }


}
