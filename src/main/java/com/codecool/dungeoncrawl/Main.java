package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    Stage stage = new Stage();
    String mapName1 = "/map.txt";
    String mapName2 = "/map2.txt";
    GameMap map2 = MapLoader.loadMap(mapName2); // DOWNSTAIRS
    GameMap map1 = MapLoader.loadMap(mapName1); // UPSTAIRS
    GameMap map = map1;
    private final int windowWidth = map.getWidth() * Tiles.TILE_WIDTH;
    private final int windowHeight = map.getHeight() * Tiles.TILE_WIDTH;
    Canvas canvas = new Canvas(
            windowWidth,
            windowHeight);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackLabel = new Label();
    Label armorLabel = new Label();
    Label name = new Label();
    TextField nameInput = new TextField();
    Button nameSubmitButton = new Button("Submit");
    Button pickUpButton = new Button("Pick up");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;

        // Start Game screen
        BorderPane startBorderPane = new BorderPane();
        Button startGameButton = new Button("Start Game");
        startBorderPane.setCenter(startGameButton);
        Scene startGameScene = new Scene(startBorderPane, windowWidth, windowHeight);
        stage.setScene(startGameScene);
        stage.setTitle("Dungeon Crawl");
        stage.show();


        // Main Game screen
        GridPane ui = new GridPane();
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

        BorderPane mainBorderPane = new BorderPane();

        mainBorderPane.setCenter(canvas);
        mainBorderPane.setRight(ui);

        Scene mainScene = new Scene(mainBorderPane);
//        primaryStage.setScene(mainScene);
//        refresh();
        mainScene.setOnKeyPressed(this::onKeyPressed);
//        primaryStage.setTitle("Dungeon Crawl");
//        primaryStage.show();
//        mainBorderPane.requestFocus(); // Brings the focus back on the map, instead of user UI

        //TODO put below code in the UserInterfaceHandler class

        startGameButton.setOnAction(event -> {
            stage.setScene(mainScene);
            refresh();
            mainBorderPane.requestFocus(); // Brings the focus back on the map, instead of user UI
        });

        nameSubmitButton.setOnAction(event -> {
            String userName = nameInput.getText();
            if (map.getPlayer().checkCheatCode(userName)){
                map.getPlayer().setCheatMode(true);
            }
            ui.getChildren().remove(nameInput);
            ui.getChildren().remove(nameSubmitButton);
            ui.add(name,0,0);
            name.setText(userName);
            name.setStyle("-fx-font-weight: bold");
            mainBorderPane.requestFocus();
        });

        pickUpButton.setOnAction(event -> pickUpItem(ui, mainBorderPane));
    }

    private void pickUpItem(GridPane ui, BorderPane borderPane) {
        final int[] rowIndex = {7};
        if (map.getPlayer().getCell().getTileName().equals("key")){
            map.getPlayer().setHasKey(true);
        }
        map.getPlayer().pickUpItem();
        borderPane.requestFocus();
        Label imageLabel = new Label();
        if (map.getPlayer().getItemUrl() != null) {
            Image image = new Image(map.getPlayer().getItemUrl());
            imageLabel.setGraphic(new ImageView(image));
            ui.add(imageLabel, 0, rowIndex[0]);
            rowIndex[0]++;
        }
        refresh();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        // End Game screen
        BorderPane endBorderPane = new BorderPane();
        Label endGameLabel = new Label("YOU DIED!");
        endBorderPane.setCenter(endGameLabel);
        Scene endGameScene = new Scene(endBorderPane, windowWidth, windowHeight);

        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
                break;
        }
        if (map.getPlayer().isDead()){
            stage.setScene(endGameScene);
        }
        refresh();
        map.moveEnemies(map);
    }

    private void refresh() {
        changeCurrentMap();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        attackLabel.setText("" + map.getPlayer().getAttack());
        armorLabel.setText("" + map.getPlayer().getArmor());
    }

    private void changeCurrentMap() {
        Player currentPlayer = map.getPlayer();
        if (map.getPlayer().isOnDownStairs()){
            map.getPlayer().setOnDownStairs(false);
            map = map2;
            Cell currentPlayerCell = map.getCell(2,9);
            currentPlayer.setCell(currentPlayerCell);
            this.map.setPlayer(currentPlayer);
        } else if (map.getPlayer().isOnUpStairs()) {
            map.getPlayer().setOnUpStairs(false);
            map = map1;
            Cell currentPlayerCell = map.getCell(5,13);
            currentPlayer.setCell(currentPlayerCell);
            this.map.setPlayer(currentPlayer);
        }
    }


}
