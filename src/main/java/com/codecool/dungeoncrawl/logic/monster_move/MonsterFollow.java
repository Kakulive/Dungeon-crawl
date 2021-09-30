package com.codecool.dungeoncrawl.logic.monster_move;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Spider;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class MonsterFollow {
    ChoiceDirection choiceDirection = new ChoiceDirection();

    public boolean checkIfPlayer(GameMap gameMap, Cell monsterCell) {
        int maxLeft = monsterCell.getY() - 3;
        int maxUp = monsterCell.getX() - 3;
        int playerX = gameMap.getPlayer().getX();
        int playerY = gameMap.getPlayer().getY();

        for (int z = 0; z <= (maxUp + 6); z++) {
            for (int y = 0; y <= (maxLeft + 6); y++) {
                if (gameMap.getCell(z, y).getTileName().equals(gameMap.getCell(playerX, playerY).getTileName())) {
                    return true;
                } else {return false;}
            }
        }
        return false;
    }
// gameMap.getCell(z, y).getTileName().equals("player"
//    public Cell findPlayer(GameMap gameMap, Cell monsterCell) {
//        int maxUp = monsterCell.getY() - 3;
//        int maxLeft = monsterCell.getX() - 3;
//        int playerX = gameMap.getPlayer().getX();
//        int playerY = gameMap.getPlayer().getY();
//
//        for (int z = 0; z <= (maxUp + 6); z++) {
//            for (int y = 0; y <= (maxLeft + 6); y++) {
//                if (gameMap.getCell(z, y).getActor().getTileName().equals(gameMap.getCell(playerX, playerY).getActor().getTileName())) {
//                    return gameMap.getCell(z, y);
//                }
//            }
//        }
//
//        return monsterCell;
//    }

    public int[] calculateDifference(Cell playerCell, Cell monsterCell ) {
        int xPlayer = playerCell.getX();
        int yPlayer = playerCell.getY();

        int xMonster = monsterCell.getX();
        int yMonster = monsterCell.getY();

        int differenceX = xMonster - xPlayer;
        int differenceY = yMonster - yPlayer;

        return new int[]{differenceX, differenceY};
    }

    public void monsterGo( int[] difference, Cell monsterCell, Cell neighbor, int dx, int dy) {
        int differenceX = difference[0];
        int differenceY = difference[1];
        int  sumDifference = differenceX + differenceY;
        List<Integer> constantNumber = Arrays.asList(-2, -1, 0, 1, 2);

        if (constantNumber.contains(sumDifference)) {
            monsterCell.getActor().onlyMonsterAttack(neighbor, monsterCell, changeSignDiffX(difference), changeSignDiffY(difference));
        } else {
            if (differenceX < 0 && differenceY < 0) {
                monsterCell.getActor().move(MoveDirection.DOWN.getMoveStep(), MoveDirection.RIGHT.getMoveStep());
            } else if (differenceX > 0 && differenceY < 0) {
                monsterCell.getActor().move(MoveDirection.UP.getMoveStep(), MoveDirection.RIGHT.getMoveStep());
            } else if (differenceX < 0 && differenceY > 0) {
                monsterCell.getActor().move(MoveDirection.UP.getMoveStep(), MoveDirection.LEFT.getMoveStep());
            } else if (differenceX > 0 && differenceY > 0){
                monsterCell.getActor().move(MoveDirection.UP.getMoveStep(), MoveDirection.LEFT.getMoveStep());
            }
        }
    }

    private int changeSignDiffX(int[] difference) {
        if (difference[0] > 0) {
            difference[0] = -1;
        } else if (difference[0] < 0) {
            difference[0] = 1;
        } else {
            difference[0] = 0;
        }
        return difference[0];
    }

    private int changeSignDiffY(int[] difference) {
        if (difference[1] > 0) {
            difference[1] = -1;
        } else if (difference[1] < 0) {
            difference[1] = 1;
        } else {
            difference[0] = 0;
        }
        return difference[1];
    }
}
