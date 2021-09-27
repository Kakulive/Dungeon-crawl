package com.codecool.dungeoncrawl.logic.monster_move;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class CheckMove  {

    public boolean CheckMonsterMove(Cell monsterCell, int choiceX, int choiceY) {
        if (monsterCell.getNeighbor(choiceX, choiceY).getTileName().equals("floor")) {
            return true;
        } else { return false; }
    }
}
