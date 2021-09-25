package com.codecool.dungeoncrawl.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

public class SaveGame {
    public static AtomicReference<String> getPlayerName() {
        AtomicReference<String> playerName = new AtomicReference<>("NoName");
        JDialog dialog = new JDialog(new JFrame(), "Save game", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setTitle("Save game");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel headingPanel = new JPanel();
        JLabel headingLabel = new JLabel("Write your name to save the progress");
        headingPanel.add(headingLabel);

        JPanel inputPanel = new JPanel();
        JLabel nameLabel = new JLabel("Name");
        JTextField nameInput = new JTextField();
        nameInput.setPreferredSize(new Dimension(150,24));
        inputPanel.add(nameLabel);
        inputPanel.add(nameInput);

        JButton submitButton = new JButton("Save");
        submitButton.addActionListener((actionEvent) -> {
            playerName.set(nameInput.getText());
            dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((actionEvent) -> dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING)));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(headingPanel);
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);

        dialog.add(mainPanel);
        dialog.pack();
        dialog.setSize(350,150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.setModal(true);
        return playerName;
    }

    public static AtomicReference<String> overwriteMessage(){
        AtomicReference<String> result = new AtomicReference<>("No");
        JDialog dialog = new JDialog(new JFrame(), "Overwrite game state", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setTitle("Overwrite game state");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel headingPanel = new JPanel();
        JLabel headingLabel = new JLabel("Would you like to overwrite the already existing state?");
        headingPanel.add(headingLabel);

        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener((actionEvent) -> {
            assert false;
            result.set("Yes");
            dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
        });
        JButton noButton = new JButton("No");
        noButton.addActionListener((actionEvent) -> dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING)));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        mainPanel.add(headingPanel);
        mainPanel.add(buttonPanel);

        dialog.add(mainPanel);
        dialog.pack();
        dialog.setSize(350,150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.setModal(true);
        return result;
    }
}
