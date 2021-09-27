package com.codecool.dungeoncrawl.logic.monster_move;

public enum MoveDirection {
    UP(-1), LEFT(-1), RIGHT(1), DOWN(1), STAY(0);

    private final int moveStep;

    MoveDirection(int moveStep) {
        this.moveStep = moveStep;
    }

    public int getMoveStep() { return moveStep; }

}
