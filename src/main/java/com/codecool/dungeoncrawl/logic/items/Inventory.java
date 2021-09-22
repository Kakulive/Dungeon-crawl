package com.codecool.dungeoncrawl.logic.items;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<String> inventories = new ArrayList();



    public List<String> getInventories() {
        return inventories;
    }

    public void setInventories(List<String> inventories) {
        this.inventories = inventories;
    }

    public void addInventory(String item){
        this.inventories.add(item);
    }
}

