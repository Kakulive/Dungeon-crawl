package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Heart;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Sword;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.Locale;

public class Player extends Actor {
    private boolean hasKey;

    private boolean onDownStairs;
    private boolean onUpStairs;
    private boolean cheatMode = false;


    public Player(Cell cell) {
        super(cell);
        this.hasKey = false;
        this.isDead = false;
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
                System.out.println("You need a key!");
                //TODO flash using javafx

            }
        } else if (isCandle(cellType)) {
            this.setHealth(this.getHealth() - 1);
            standardMove(nextCell);
        } else if (isDownStairs(cellType)) {
            onDownStairs = true;
        } else if (isUpStairs(cellType)){
            onUpStairs = true;
        } else if (isCheatModeOn())  {
            super.move(dx, dy);
        } else if (!isWall(cellType)){
            super.move(dx,dy);
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

    public boolean checkCheatCode(String name){
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
            switch (cellType.getTileName().toUpperCase()) {
                case "SWORD":
                    setItemUrl("https://i.imgur.com/PmvQYO3.png");
                    this.setAttack(this.getAttack() + Sword.getAttack());
                    break;
                case "KEY":
                    setItemUrl("https://i.imgur.com/4kUCAMK.png");
                    break;
                case "SHIELD":
                    setItemUrl("https://i.imgur.com/SNEwI8U.png");
                    this.setArmor(this.getArmor() + Shield.getArmor());
                    break;
                case "HEART":
                    this.setHealth(this.getHealth() + Heart.health);
                    setItemUrl(null);
                    break;
            }
        } else setItemUrl(null);
    }

    public boolean isOnDownStairs() {
        return onDownStairs;
    }

    public void setOnDownStairs(boolean onDownStairs) {
        this.onDownStairs = onDownStairs;
    }

    public boolean isOnUpStairs() {
        return onUpStairs;
    }

    public void setOnUpStairs(boolean onUpStairs) {
        this.onUpStairs = onUpStairs;
    }

}
