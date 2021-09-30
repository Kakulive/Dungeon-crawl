package com.codecool.dungeoncrawl.logic.utils;

import com.codecool.dungeoncrawl.logic.items.Heart;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Sword;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MessageFlashing {
    public static void flashMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Message");
        alert.setContentText(message);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    public void showPickUpMessage(String item) {
        String message = null;
        switch (item) {
            case "SWORD":
                message = "Hmm, you found the sword. Do you think this will help you?\nAttack +" + Sword.getAttack();
                break;
            case "KEY":
                message = "Some doors are best left unopened";
                break;
            case "SHIELD":
                message = "Shields break as quickly as human lives\nArmor +" + Shield.getArmor();
                break;
            case "HEART":
                message = "You are lucky. Usually lives are lost rather than found in the dungeon\nHealth +" + Heart.getHealth();
                break;
        }
        flashMessage(message);
    }

    public void showImportAndExportAlerts(String s) {
    }
}
