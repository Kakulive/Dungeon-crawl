package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Heart;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Sword;
import static com.codecool.dungeoncrawl.logic.utils.MessageFlashing.flashMessage;

public class Player extends Actor {
    private boolean hasKey;
    private String name = "Player";
    private boolean goingDown;
    private boolean goingUp;
    private boolean cheatMode = false;



    public Player(Cell cell) {
        super(cell);
        this.hasKey = false;
        this.isDead = false;
    }

    public Player(Cell cell, String name) {
        super(cell);
        this.name = name;
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        CellType cellType = nextCell.getType();
        if (isEnemy(cellType)) {
            battleMove(nextCell);
        } else if (isClosedDoor(cellType)) {
            if (this.getHasKey()) {
                openDoor(nextCell);
            } else {
                flashMessage("You need a key!");
            }
        } else if (isCandle(cellType)) {
            this.setHealth(this.getHealth() - 1);
            standardMove(nextCell);
        } else if (isDownStairs(cellType)) {
            goingDown = true;
        } else if (isUpStairs(cellType)) {
            goingUp = true;
        } else if (isCheatModeOn()) {
            super.move(dx, dy);
        } else if (!isWall(cellType)) {
            super.move(dx, dy);
        }
        if (isPlayerDead(this)) {
            setDead(true);
        }
    }

    public String getTileName() {
        return "player";
    }

    public boolean getHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    private void openDoor(Cell nextcell) {
        nextcell.setType(CellType.OPEN_DOOR);
    }

    public boolean isCheatModeOn() {
        return cheatMode;
    }

    public void setCheatMode(boolean cheatMode) {
        this.cheatMode = cheatMode;
    }

    public boolean checkCheatCode(String name) {
        return name.toLowerCase().equals("adam") || name.toLowerCase().equals("marcelina")
                || name.toLowerCase().equals("damian") || name.toLowerCase().equals("dymitr");
    }

    public void pickUpItem() {
        Cell currentCell = cell.getNeighbor(0, 0);
        CellType cellType = currentCell.getType();
        if (cellType.equals(CellType.SWORD)
                || cellType.equals(CellType.KEY)
                || cellType.equals(CellType.HEART)
                || cellType.equals(CellType.SHIELD)) {
            currentCell.setType(CellType.FLOOR);
            String tileName = cellType.getTileName().toUpperCase();
            showPickUpMessage(tileName);
            switch (tileName) {
                case "SWORD":
                    setItemUrl("/sword.png");
                    this.setAttack(this.getAttack() + Sword.getAttack());
                    break;
                case "KEY":
                    setItemUrl("/key.png");
                    break;
                case "SHIELD":
                    setItemUrl("/shield.png");
                    this.setArmor(this.getArmor() + Shield.getArmor());
                    break;
                case "HEART":
                    this.setHealth(this.getHealth() + Heart.health);
                    setItemUrl(null);
                    break;
            }
        } else setItemUrl(null);
    }

    private void showPickUpMessage(String item) {
        String message = null;
        switch (item) {
            case "SWORD":
                message = "Hmm, you found the sword. Do you think this will help you?\nAttack +" + Sword.getAttack();
                break;
            case "KEY":
                message = "Some doors are best left unopened";
                break;
            case "SHIELD":
                message = "Shields break as quickly as human lives\nArmor +" + Shield.getArmor();
                break;
            case "HEART":
                message = "You are lucky. Usually lives are lost rather than found in the dungeon\nHealth +" + Heart.health;
                break;
        }
        flashMessage(message);
    }

    public boolean isGoingDown() {
        return goingDown;
    }

    public void setGoingDown(boolean isPlayerGoingDown) {
        this.goingDown = isPlayerGoingDown;
    }

    public boolean isGoingUp() {
        return goingUp;
    }

    public void setGoingUp(boolean isPlayerGoingUp) {
        this.goingUp = isPlayerGoingUp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
