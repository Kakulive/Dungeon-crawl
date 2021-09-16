package com.codecool.dungeoncrawl.logic.utils;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SceneSwitcher {

    private Button startGameButton = new Button("Start Game");

    private Scene mainScene;
    private BorderPane mainBorderPane;
    private GridPane ui;
    private TextField nameInput;
    private Label healthLabel = new Label();
    private Label attackLabel = new Label();
    private Label armorLabel = new Label();
    private Button nameSubmitButton = new Button("Submit");
    private Button pickUpButton = new Button("Pick up");

    public void startGameScene (Stage stage, int windowWidth, int windowHeight) {

        BorderPane startBorderPane = new BorderPane();
        startBorderPane.setCenter(startGameButton);
        Scene startGameScene = new Scene(startBorderPane, windowWidth, windowHeight);
        stage.setScene(startGameScene);
        stage.setTitle("Dungeon Crawl");
        stage.show();
    }

    public void endGameScene (Stage stage, int windowWidth, int windowHeight) {
        BorderPane endBorderPane = new BorderPane();
        Label endGameLabel = new Label("YOU DIED!");
        endBorderPane.setCenter(endGameLabel);
        Scene endGameScene = new Scene(endBorderPane, windowWidth, windowHeight);
        stage.setScene(endGameScene);
    }

    public void mainScene(Stage stage, int windowWidth, int windowHeight, Canvas canvas) {
        nameInput = new TextField();

        ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.setHgap(5);
        ui.setVgap(5);
        ui.add(nameInput,0,0);
        nameInput.setPromptText("What's your name?");
        ui.add(nameSubmitButton,0,1);
        ui.add(new Label("Health: "), 0, 2);
        ui.add(healthLabel, 1, 2);
        ui.add(new Label("Attack: "), 0, 3);
        ui.add(attackLabel, 1, 3);
        ui.add(new Label("Armor: "), 0, 4);
        ui.add(armorLabel, 1, 4);
        ui.add(pickUpButton, 0, 5);
        ui.add(new Label("Inventory:"), 0, 6);

        mainBorderPane = new BorderPane();

        mainBorderPane.setCenter(canvas);
        mainBorderPane.setRight(ui);

        mainScene = new Scene(mainBorderPane);

        stage.setScene(mainScene);

    }

    public Button getStartGameButton() {
        return startGameButton;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    public GridPane getUi() {
        return ui;
    }

    public TextField getNameInput() {
        return nameInput;
    }

    public Button getNameSubmitButton() {
        return nameSubmitButton;
    }

    public Button getPickUpButton() {
        return pickUpButton;
    }

    public Label getHealthLabel() {
        return healthLabel;
    }

    public Label getAttackLabel() {
        return attackLabel;
    }

    public Label getArmorLabel() {
        return armorLabel;
    }
}
