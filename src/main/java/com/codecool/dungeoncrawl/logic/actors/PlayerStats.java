package com.codecool.dungeoncrawl.logic.actors;

public enum PlayerStats {
    HEALTH(10),
    ATTACK(5),
    ARMOR(0);


    private final int valueStat;


    PlayerStats(int valueStat) {
        this.valueStat = valueStat;
    }

    public int getValueStat() { return valueStat; }
}
