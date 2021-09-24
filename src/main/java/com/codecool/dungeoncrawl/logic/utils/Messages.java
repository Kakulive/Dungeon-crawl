package com.codecool.dungeoncrawl.logic.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class Messages {
    public static void flashMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Message");
        alert.setContentText(message);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.setX(600);
        alert.setY(340);
        alert.show();
    }
}
