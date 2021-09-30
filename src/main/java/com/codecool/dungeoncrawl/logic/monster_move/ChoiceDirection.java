package com.codecool.dungeoncrawl.logic.monster_move;

import java.util.Random;

public class ChoiceDirection {


    private int pickNumber() {
        return new Random().nextInt((4 - 1) + 1);
    }

    public int[] ChangeNumber() {
        switch (pickNumber()) {
            case 1:
                return new int[]{0, MoveDirection.UP.getMoveStep()};
            case 2:
                return new int[]{MoveDirection.LEFT.getMoveStep(), 0};
            case 3:
                return new int[]{MoveDirection.RIGHT.getMoveStep(), 0};
            case 4:
                return new int[]{0, MoveDirection.DOWN.getMoveStep()};
        }
        return new int[]{0, 0};
    }


}

