package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Player extends Actor {
    private boolean hasKey;

    public Player(Cell cell) {
        super(cell);
        this.hasKey = false; //TODO change once inventory is implemented
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
        } else if (!isWall(cellType))  {
            super.move(dx, dy);
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

}
