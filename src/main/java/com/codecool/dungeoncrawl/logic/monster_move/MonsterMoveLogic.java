package com.codecool.dungeoncrawl.logic.monster_move;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;


public class MonsterMoveLogic {
    MonsterFollow monsterFollow = new MonsterFollow();
    ChoiceDirection choiceDirection = new ChoiceDirection();
    CheckMove checkMove = new CheckMove();



    public void moveFollow(GameMap gameMap, Cell monsterCell, int dx, int dy, Actor enemy) {
        Cell cellNeighbor = monsterCell.getNeighbor(dx, dy);
        if (monsterFollow.checkIfPlayer(gameMap, monsterCell)) {
            Cell playerCell = gameMap.getPlayer().getCell();
            int[] differenceInCords = monsterFollow.calculateDifference(playerCell, monsterCell);
            monsterFollow.monsterGo(differenceInCords, monsterCell, cellNeighbor, dx, dy);
        } else {
            onlyMove(dx, dy, enemy);
        }
    }
    public void onlyMove(int dx, int dy, Actor enemy) {
        enemy.move(dx, dy);
    }

//    public void onlyMove(GameMap gameMap, Cell monsterCell) {
//        int[] randomMove = choiceDirection.ChangeNumber();
//        if (checkMove.CheckMonsterMove(monsterCell, randomMove[0], randomMove[1])) {
//            monsterCell.getActor().move(randomMove[0], randomMove[1]);
//        } else {
//            onlyMove(gameMap, monsterCell);
//        }
//    }



}
