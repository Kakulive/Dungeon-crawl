package com.codecool.dungeoncrawl;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Util {
    public static void flashMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Message");
        alert.setContentText(message);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();
    }
}
