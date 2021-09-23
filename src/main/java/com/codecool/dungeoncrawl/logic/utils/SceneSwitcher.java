package com.codecool.dungeoncrawl.logic.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;

public class SceneSwitcher {
    private int separationStatistic = 10;

    private Button startGameButton = new Button("Start Game");
    private Button exitButton = new Button("Exit");

    private Button setPlayer = new Button("Start Game");
    private Button loadGame = new Button("Load Game");

    private Button submitButton = new Button("Submit");
    private Button addStatHealth = new Button();
    private Button subStatHealth  = new Button();
    private Button addStatArmor = new Button();
    private Button subStatArmor = new Button();
    private Button addStatAttack = new Button();
    private Button subStatAttack = new Button();

//    private Label maxSepPoints = new Label();
//    private Label inputName = new Label();
//    private Label setLive = new Label();
//    private Label setAttack = new Label();
//    private Label setArmor = new Label();


    private Scene menuScene;
    private Scene mainScene;
    private BorderPane mainBorderPane;
    private GridPane ui;
    private Label playerName = new Label();
    private TextField playerNameInput = new TextField();
    private TextField nameInput = new TextField();
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
        stage.setScene(startGameScene);
        stage.setTitle("Dungeon Crawl");
        stage.show();
    }

    public void menuGameScene(Stage stage, int windowWidth, int windowHeight) {
        BorderPane menuBorderPane = new BorderPane();

        GridPane center = initMenu();

        center.setAlignment(Pos.CENTER);
        menuBorderPane.setCenter(center);

        menuBorderPane.setStyle(getSceneStyleString("dungeon_masters2.jpg"));
        Scene menuScene = new Scene(menuBorderPane, windowWidth, windowHeight);
        stage.setScene(menuScene);
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

    public Label getPlayerName() {
        return playerName;
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

    public Button getSetPlayer() { return setPlayer; }

    public TextField getNameInput() { return nameInput; }

    public TextField getPlayerNameInput() { return playerNameInput; }

    public Button getAddStatHealth() { return addStatHealth; }
    public Button getSubStatHealth() { return subStatHealth; }
    public Button getAddStatArmor() { return  addStatArmor; }
    public Button getSubStatArmor() { return  subStatArmor; }
    public Button getAddStatAttack() { return addStatAttack; }
    public Button getSubStatAttack() { return subStatAttack; }
    public Button getSubmitButton() { return submitButton; }

    public int getSeparationStatistic() { return separationStatistic; }
    public void setSeparationStatistic(int numberToSeparation) { this.separationStatistic = numberToSeparation; }

//    public Label getMaxSepPoints() {return maxSepPoints;}
//    public Label getSetLive() { return setLive; }
//    public Label getSetAttack() { return setAttack; }
//    public Label getSetArmor() { return  setAttack; }
    private String playerNameToString() {
        String name = playerName.getText();
        return name;
    }

    private GridPane initMenu() {
        ui = setUiToMethods();
        ui.add(setPlayer, 0, 1);
        ui.add(loadGame, 0, 3);
        ui.add(exitButton, 0, 5);
        return ui;
    }

    private GridPane initSetPlayer() {
        ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(20));
        ui.setHgap(18);
        ui.setVgap(18);

        ui.add(playerNameInput,0,0);
        playerNameInput.setPromptText("What's your name?");

        setupSingleLabel("Health: ", 2, healthLabel);
        setupSingleLabel("Armor: ", 5, armorLabel);
        setupSingleLabel("Attack: ", 8, attackLabel);
        ui.add(submitButton, 0, 10);
        return ui;
    }
    private GridPane setUiToMethods() {
        ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.setHgap(5);
        ui.setVgap(5);
        return ui;
    }
    private GridPane initButtons() {
        ui = setUiToMethods();

        addStatHealth.setText("Health UP");
        addStatArmor.setText("Armor UP");
        addStatAttack.setText("Attack UP");

        subStatHealth.setText("Health Down");
        subStatArmor.setText("Armor Down");
        subStatAttack.setText("Attack Down");

        ui.add(addStatHealth, 0, 1);
        ui.add(subStatHealth, 0, 3);

        ui.add(addStatArmor, 0, 4);
        ui.add(subStatArmor, 0, 6);

        ui.add(addStatAttack, 0, 7);
        ui.add(subStatAttack, 0, 9);
        return ui;
    }

    private GridPane initUi() {
        ui = setUiToMethods();


        ui.add(nameInput, 0, 1);
        nameInput.setPromptText("What's your name?");
        ui.add(nameSubmitButton,0,2);
        ui.add(new Label(playerNameToString()), 0, 3);
        setupSingleLabel("Health: ", 4, healthLabel);
        setupSingleLabel("Attack: ", 5, attackLabel);
        setupSingleLabel("Armor: ", 6, armorLabel);
        ui.add(pickUpButton, 0, 7);
        ui.add(new Label("Inventory:"), 0, 8);

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

    public void changeMenuIfStart(Stage stage, int windowWidth, int windowHeight) {
        BorderPane setPlayerPane = new BorderPane();
        GridPane center = initSetPlayer();
        GridPane right = initButtons();
        GridPane left = new GridPane();

        left.setAlignment(Pos.CENTER);
        right.setAlignment(Pos.CENTER);
        center.setAlignment(Pos.CENTER);

        left.add(startGameButton, 0, 0);
        left.add(loadGame, 0, 1);

        setPlayerPane.setStyle(getSceneStyleString("dungeon_masters2.jpg"));
        setPlayerPane.setLeft(left);
        setPlayerPane.setCenter(center);
        setPlayerPane.setRight(right);

        Scene startGameScene = new Scene(setPlayerPane, windowWidth, windowHeight);
        stage.setScene(startGameScene);
        stage.setTitle("Dungeon Crawl");
        stage.show();

    }
}
