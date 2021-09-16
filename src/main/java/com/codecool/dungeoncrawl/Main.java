package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.utils.SceneSwitcher;
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
    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    String mapName1 = "/map.txt";
    String mapName2 = "/map2.txt";

    GameMap map2 = MapLoader.loadMap(mapName2); // DOWNSTAIRS
    GameMap map1 = MapLoader.loadMap(mapName1); // UPSTAIRS

    GameMap map = map1;

    private final int windowWidth = map.getWidth() * Tiles.TILE_WIDTH;
    private final int windowHeight = map.getHeight() * Tiles.TILE_WIDTH;

    Canvas canvas = new Canvas(windowWidth, windowHeight);
    GraphicsContext context = canvas.getGraphicsContext2D();

    Label name = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;
        sceneSwitcher.startGameScene(stage, windowWidth, windowHeight);


        sceneSwitcher.getStartGameButton().setOnAction(event -> {
            sceneSwitcher.mainScene(stage, windowWidth, windowHeight, canvas);
            sceneSwitcher.getMainScene().setOnKeyPressed(this::onKeyPressed);
            refresh();
            sceneSwitcher.getMainBorderPane().requestFocus(); // Brings the focus back on the map, instead of user UI
        });

        sceneSwitcher.getNameSubmitButton().setOnAction(event -> {
            String userName = sceneSwitcher.getNameInput().getText();
            if (map.getPlayer().checkCheatCode(userName)){
                map.getPlayer().setCheatMode(true);
            }
            sceneSwitcher.getUi().getChildren().remove(sceneSwitcher.getNameInput());
            sceneSwitcher.getUi().getChildren().remove(sceneSwitcher.getNameSubmitButton());
            sceneSwitcher.getUi().add(name,0,0);
            name.setText(userName);
            name.setStyle("-fx-font-weight: bold");
            sceneSwitcher.getMainBorderPane().requestFocus();
        });

        sceneSwitcher.getPickUpButton().setOnAction(event -> pickUpItem(sceneSwitcher.getUi(), sceneSwitcher.getMainBorderPane()));
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
            sceneSwitcher.endGameScene(stage, windowWidth, windowHeight);
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
        sceneSwitcher.getHealthLabel().setText("" + map.getPlayer().getHealth());
        sceneSwitcher.getAttackLabel().setText("" + map.getPlayer().getAttack());
        sceneSwitcher.getArmorLabel().setText("" + map.getPlayer().getArmor());
    }

    private void changeCurrentMap() {
        Player currentPlayer = map.getPlayer();
        int X = map.getPlayer().getX();
        int Y = map.getPlayer().getY();

        if (map.getPlayer().isOnDownStairs()){
            map.getPlayer().setOnDownStairs(false);
            map.setPlayer(null);
            map.getCell(X,Y).setActor(null);
            map = map2;
            Cell currentPlayerCell = map.getCell(2,9);
            currentPlayer.setCell(currentPlayerCell);
            map.setPlayer(currentPlayer);
            currentPlayerCell.setActor(currentPlayer);

        } else if (map.getPlayer().isOnUpStairs()) {
            map.getPlayer().setOnUpStairs(false);
            Cell oldPlayerCell = map.getCell(X,Y);
            map.setPlayer(null);
            oldPlayerCell.setActor(null);
            map = map1;
            Cell currentPlayerCell = map.getCell(5,13);
            currentPlayer.setCell(currentPlayerCell);
            map.setPlayer(currentPlayer);
            currentPlayerCell.setActor(currentPlayer);

        }
    }


}
