package com.codecool.dungeoncrawl.logic.monster_move;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Spider;

public class MonsterFollow {
    ChoiceDirection choiceDirection = new ChoiceDirection();

    public boolean checkIfPlayer(GameMap gameMap, Cell monsterCell) {
        int maxLeft = monsterCell.getY() - 5;
        int maxUp = monsterCell.getX() - 5;

        for (int z = 0; z <= (maxUp + 9); z++) {
            for (int y = 0; y <= (maxLeft + 9); y++) {
                if (gameMap.getCell(z, y).getTileName().equals("player")) {
                    return true;
                }
            }
        }
        return false;
    }

    public Cell findPlayer(GameMap gameMap, Cell monsterCell) {
        int maxUp = monsterCell.getY() - 5;
        int maxLeft = monsterCell.getX() - 5;

        for (int z = 0; z <= (maxUp + 9); z++) {
            for (int y = 0; y <= (maxLeft + 9); y++) {
                if (gameMap.getCell(z, y).getTileName().equals("player")) {
                    return gameMap.getCell(z, y);
                }
            }
        }

        return monsterCell;
    }

    public int[] calculateDifference(Cell playerCell, Cell monsterCell ) {
        int xPlayer = playerCell.getX();
        int yPlayer = playerCell.getY();

        int xMonster = monsterCell.getX();
        int yMonster = monsterCell.getY();

        int differenceX = xMonster - xPlayer;
        int differenceY = yMonster - yPlayer;

        return new int[]{differenceX, differenceY};
    }

    public void monsterGo( int[] difference, Cell monsterCell, Cell neighbor) {
        int differenceX = difference[0];
        int differenceY = difference[1];

        if (differenceX == 0 && differenceY == 0) {
            monsterCell.getActor().battleMove(neighbor);
        } else {
            if (differenceX < 0 && differenceY < 0) {
                monsterCell.getActor().move(MoveDirection.DOWN.getMoveStep(), MoveDirection.RIGHT.getMoveStep());
            } else if (differenceX > 0 && differenceY < 0) {
                monsterCell.getActor().move(MoveDirection.UP.getMoveStep(), MoveDirection.RIGHT.getMoveStep());
            } else if (differenceX < 0 && differenceY > 0) {
                monsterCell.getActor().move(MoveDirection.UP.getMoveStep(), MoveDirection.LEFT.getMoveStep());
            } else {
                monsterCell.getActor().move(MoveDirection.UP.getMoveStep(), MoveDirection.LEFT.getMoveStep());
            }
        }

    }

}
