package com.codecool.dungeoncrawl.logic.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Inventory {
    private List<String> inventory;

    public Inventory() {
        this.inventory = new ArrayList<>();
    }

    public List<String> getInventoryList() {
        return inventory;
    }

    public void addItemToInventory(Item item){
        if (!Objects.equals(item.getTileName(), "heart")){
            this.inventory.add(item.getTileName());
        }
    }

    @Override
    public String toString() {
        StringBuilder inventoryString = new StringBuilder();
        for (String item : inventory){
            inventoryString.append(item);
            inventoryString.append(";");
        }
        return inventoryString.toString();
    }
}

