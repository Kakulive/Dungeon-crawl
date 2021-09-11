package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private int attack = 5;
    private int armor = 0;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        CellType cellType = nextCell.getType();
        if (isEnemy(cellType)) {
            battleMove(nextCell);
        } else if (!isWall(cellType)) {
            standardMove(nextCell);
        }
    }

    public void pickUpItem(){
        Cell nextCell = cell.getNeighbor(0, 0);
        CellType cellType = nextCell.getType();
        if (cellType.equals(CellType.ITEM)){
            nextCell.setType(CellType.FLOOR);
        }
    }

    private void battleMove(Cell nextCell) {
        Actor player = cell.getActor();
        Actor enemy = nextCell.getActor();
        if (!isOneShot(player, enemy)) {
            updateEnemyHealth(player, enemy);
            if (doesAttackHurt(player, enemy)) {
                updatePlayerHealth(player, enemy);
            }
            if (isPlayerDead(player)) {
                // TODO game_over
            }
        } else {
            killEnemyAndMove(nextCell);
        }
    }

    private boolean isOneShot(Actor player, Actor enemy) {
        return enemy.getHealth() < player.getAttack();
    }

    private boolean isPlayerDead(Actor player) {
        return player.getHealth() < 0;
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
        nextCell.setActor(null);
        nextCell.setType(CellType.FLOOR);
        standardMove(nextCell);
    }

    private void standardMove(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    private boolean isWall(CellType neighbourCellType) {
        return neighbourCellType == CellType.WALL;
    }

    private boolean isEnemy(CellType neighbourCellType) {
        return neighbourCellType == CellType.ENEMY;
    }

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

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttack(int attack) {this.attack = attack;}

    public void setArmor(int armor) {this.armor = armor;}
}
