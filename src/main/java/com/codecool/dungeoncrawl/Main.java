package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.beans.EventHandler;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackLabel = new Label();
    Label armorLabel = new Label();
    Button pickUpButton = new Button("Pick up");

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Attack: "), 0, 1);
        ui.add(attackLabel, 1, 1);
        ui.add(new Label("Armor: "), 0, 2);
        ui.add(armorLabel, 1, 2);
        ui.add(pickUpButton, 0, 3);
        ui.add(new Label("Inventory:"), 0, 4);

        BorderPane borderPane = new BorderPane();

        final int[] rowIndex = {5};
        pickUpButton.setOnAction(event -> {
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
        });

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        borderPane.requestFocus(); // Brings the focus back on the map, instead of user UI

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
        refresh();
        map.moveEnemies(map);
    }

    private void refresh() {
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


}
