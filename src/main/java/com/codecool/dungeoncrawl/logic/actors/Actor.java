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

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Player player = (Player) cell.getActor();
        Cell nextCell = cell.getNeighbor(dx, dy);
        CellType cellType = nextCell.getType();
        if (isEnemy(cellType)) {
            battleMove(nextCell);
        } else if (isClosedDoor(cellType)) {
            if (player.getHasKey()) {
                openDoor(nextCell);
            } else {
                System.out.println("You need a key!");
                //TODO flash using javafx
            }
        } else if (!isWall(cellType) && !isClosedDoor(cellType))  {
            standardMove(nextCell);
        }
    }

    private void openDoor(Cell nextcell) {
        nextcell.setType(CellType.OPEN_DOOR);
    }

    public void pickUpItem() {
        Cell currentCell = cell.getNeighbor(0, 0);
        CellType cellType = currentCell.getType();
        if (cellType.equals(CellType.ITEM) || cellType.equals(CellType.KEY) || cellType.equals(CellType.HEART)) {
            currentCell.setType(CellType.FLOOR);
            switch (cellType.getTileName().toUpperCase()) {
                case "SWORD":
                    setItemUrl("https://i.imgur.com/PmvQYO3.png");
                    break;
                case "HEART":
                    setItemUrl("https://i.imgur.com/KFEzRS4.png");
                    break;
                case "KEY":
                    setItemUrl("https://i.imgur.com/4kUCAMK.png");
                    break;
            }
        } else setItemUrl(null);
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

    protected boolean isClosedDoor(CellType neighbourCellType) {
        return neighbourCellType == CellType.CLOSED_DOOR;

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
        int defeatedEnemyId = nextCell.getActor().getId();
        GameMap.removeEnemyFromList(defeatedEnemyId);
        nextCell.setActor(null);
        nextCell.setType(CellType.FLOOR);
        standardMove(nextCell);
    }

    private void standardMove(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    protected boolean isWall(CellType neighbourCellType) {
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
}
