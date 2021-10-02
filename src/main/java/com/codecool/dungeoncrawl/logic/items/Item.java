package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public abstract class Item implements Drawable {
    private Cell cell;
    private int attack;
    private int armor;
    private int health;

    public Cell getCell() {
        return cell;
    }

    protected Item(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
        this.health = 0;
        this.attack = 0;
        this.armor = 0;

    }
private void updatePlayerHealth(Actor player){
        player.setHealth(player.getHealth()+  health);
}

private void updatePlayerArmor (Actor player){
        player.setArmor(player.getArmor() + armor);
}

private void updatePlayerAttack (Actor player){
        player.setArmor(player.getAttack() + attack);
}



}
