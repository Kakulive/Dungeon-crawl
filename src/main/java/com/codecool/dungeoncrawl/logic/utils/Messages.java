package com.codecool.dungeoncrawl.logic.utils;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;


public class Messages {
    public static void flashMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Message");
        alert.setContentText(message);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();
    }

    public static AtomicReference<String> getSaveGameWindow() {
        AtomicReference<String> playerName = new AtomicReference<>("Player");
        JDialog frame = new JDialog();
        frame.setTitle("Save game");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel headingPanel = new JPanel();
        JLabel headingLabel = new JLabel("What is your name?");
        headingPanel.add(headingLabel);

        JPanel inputPanel = new JPanel();
        JTextField nameInput = new JTextField();
        nameInput.setPreferredSize(new Dimension(150,24));
        inputPanel.add(nameInput);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener((actionEvent) -> {
            playerName.set(nameInput.getText());
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((actionEvent) -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(headingPanel);
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);

        frame.add(mainPanel);
        frame.pack();
        frame.setSize(300,150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getRootPane().setDefaultButton(submitButton);
        frame.requestFocusInWindow();
        return playerName;
    }

}
