package com.codecool.dungeoncrawl.logic.utils;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SceneSwitcher {

    private Button startGameButton = new Button("Start Game");
    private Button exitButton = new Button("Exit");


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
        BorderPane bottom = new BorderPane();
        bottom.setCenter(exitButton);
        startBorderPane.setBottom(bottom);
        startBorderPane.setStyle(getSceneStyleString("dungeon_masters2.jpg"));

        Scene startGameScene = new Scene(startBorderPane, windowWidth, windowHeight);
        startBorderPane.setCenter(startGameButton);
        startGameButton.requestFocus();
        stage.setScene(startGameScene);
        stage.setTitle("Dungeon Crawl");
        stage.show();
    }

    public Button getExitButton() {
        return exitButton;
    }

    public void endGameScene (Stage stage, int windowWidth, int windowHeight) {
        BorderPane endBorderPane = new BorderPane();
        BorderPane bottom = new BorderPane();
        bottom.setCenter(exitButton);
        endBorderPane.setStyle(getSceneStyleString("dungeon_dead.gif"));
        Scene endGameScene = new Scene(endBorderPane, windowWidth, windowHeight);
        endBorderPane.setBottom(bottom);
        stage.setScene(endGameScene);
    }

    public void mainScene(Stage stage, int windowWidth, int windowHeight, Canvas canvas) {
        nameInput = new TextField();

        GridPane ui = initUi();
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

    private GridPane initUi() {
        ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.setHgap(5);
        ui.setVgap(5);
        ui.add(nameInput,0,0);
        nameInput.setPromptText("What's your name?");
        ui.add(nameSubmitButton,0,1);
        setupSingleLabel("Health: ", 2, healthLabel);
        setupSingleLabel("Attack: ", 3, attackLabel);
        setupSingleLabel("Armor: ", 4, armorLabel);
        ui.add(pickUpButton, 0, 5);
        ui.add(new Label("Inventory:"), 0, 6);

        return ui;
    }

    private String getSceneStyleString(String styleString) {
        return "-fx-background-color: black;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-image: url('" + styleString + "');"+
                "-fx-background-position: center center";
    }

    private void setupSingleLabel(String s, int i, Label healthLabel) {
        ui.add(new Label(s), 0, i);
        ui.add(healthLabel, 1, i);
    }
}
