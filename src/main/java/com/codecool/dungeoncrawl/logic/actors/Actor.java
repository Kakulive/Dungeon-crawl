package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;

public abstract class Actor implements Drawable {
    protected Cell cell;
    private int health = 10;
    private int attack = 5;
    private int armor = 0;
    public static int enemyIdCounter = 1;
    private String itemUrl;
    protected boolean isDead;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    protected void battleMove(Cell nextCell) {
        Actor player = cell.getActor();
        Actor enemy = nextCell.getActor();
        if (!isOneShot(player, enemy)) {
            updateEnemyHealth(player, enemy);
            if (doesAttackHurt(player, enemy)) {
                updatePlayerHealth(player, enemy);
            }
            if (isPlayerDead(player)) {
                setDead(true);
            }
        } else {
            killEnemyAndMove(nextCell);
        }
    }

    protected boolean isClosedDoor(CellType neighbourCellType) {
        return neighbourCellType == CellType.CLOSED_DOOR;
    }

    private boolean isOneShot(Actor player, Actor enemy) {
        return enemy.getHealth() < player.getAttack();
    }

    protected boolean isPlayerDead(Actor player) {
        return player.getHealth() <= 0;
    }

    private boolean doesAttackHurt(Actor player, Actor enemy) {
        return player.getArmor() < enemy.getAttack();
    }

    private void updatePlayerHealth(Actor player, Actor enemy) {
        player.setHealth(player.getHealth() - (enemy.getAttack() - player.getArmor()));
    }

    private void updateEnemyHealth(Actor player, Actor enemy) {
        enemy.setHealth(enemy.getHealth() - player.getAttack());
    }

    private void killEnemyAndMove(Cell nextCell) {
        int defeatedEnemyId = nextCell.getActor().getId();
        cell.getGameMap().removeEnemyFromList(defeatedEnemyId);
        nextCell.setActor(null);
        nextCell.setType(CellType.FLOOR);
        standardMove(nextCell);
    }

    protected void standardMove(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    protected boolean isWall(CellType neighbourCellType) {
        return neighbourCellType == CellType.WALL;
    }

    protected boolean isEnemy(CellType neighbourCellType) {
        return neighbourCellType == CellType.ENEMY;
    }

    protected boolean isCandle(CellType neighbourCellType) {
        return neighbourCellType == CellType.CANDLE;
    }

    protected boolean isUpStairs(CellType neighbourCellType) {return neighbourCellType == CellType.UP_STAIRS;}

    protected boolean isDownStairs(CellType neighbourCellType) {return neighbourCellType == CellType.DOWN_STAIRS;}

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getId() {
        return 0;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDead() {
        return isDead;
    }
}
