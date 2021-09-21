package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class PlayerModel extends BaseModel {
    private String playerName;
    private String hasKey;
    private String items;
    private int x;
    private int y;
    private int hp;
    private int attack;
    private int armor;


    public PlayerModel(String playerName, int hp, int x, int y, int attack, int armor, String hasKey, String items) {
        this.playerName = playerName;
        this.hasKey = hasKey;
        this.items = items;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.attack = attack;
        this.armor = armor;
    }

    // TODO in constructor which is below enter hasKey, items, attack, armor
    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();

        this.hp = player.getHealth();

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getHasKey() {
        return hasKey;
    }

    public void setHasKey(String hasKey) {
        this.hasKey = hasKey;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
