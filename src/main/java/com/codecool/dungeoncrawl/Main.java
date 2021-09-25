package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.staircaseExits;
import com.codecool.dungeoncrawl.logic.utils.SceneSwitcher;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.codecool.dungeoncrawl.logic.utils.Messages.flashMessage;
import static com.codecool.dungeoncrawl.model.SaveGame.getPlayerName;
import static com.codecool.dungeoncrawl.model.SaveGame.overwriteMessage;


public class Main extends Application {
    public static final String MAPNAME1 = "/map.txt";
    public static final String MAPNAME2 = "/map2.txt";

    private Stage stage = new Stage();
    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    private GameMap map2 = MapLoader.loadMap(MAPNAME2); // DOWNSTAIRS
    private GameMap map1 = MapLoader.loadMap(MAPNAME1); // UPSTAIRS

    private GameMap map = map1;

    private final int windowWidth = map.getWidth() * Tiles.TILE_WIDTH;
    private final int windowHeight = map.getHeight() * Tiles.TILE_WIDTH;

    private Canvas canvas = new Canvas(windowWidth, windowHeight);
    private GraphicsContext context = canvas.getGraphicsContext2D();

    Label name = new Label();

    // TODO
    GameDatabaseManager dbManager;

    private int inventoryRowIndex = 7;
    private int inventoryColumnIndex = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO
        setupDbManager();
        this.stage = primaryStage;
        sceneSwitcher.startGameScene(stage, windowWidth+200, windowHeight);


        sceneSwitcher.getStartGameButton().setOnAction(event -> {
            sceneSwitcher.mainScene(stage, windowWidth, windowHeight, canvas);
            sceneSwitcher.getMainScene().setOnKeyPressed(this::onKeyPressed);
            // TODO
            // scene.setOnKeyReleased(this::onKeyReleased);
            refresh();
            sceneSwitcher.getMainBorderPane().requestFocus(); // Brings the focus back on the map, instead of user UI
        });


        sceneSwitcher.getExitButton().setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });

        sceneSwitcher.getNameSubmitButton().setOnAction(event -> {
            String userName = sceneSwitcher.getNameInput().getText();
            map.getPlayer().setName(userName);
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
        if (map.getPlayer().getCell().getTileName().equals("key")){
            map.getPlayer().setHasKey(true);
        }
        map.getPlayer().pickUpItem();
        borderPane.requestFocus();
        Label imageLabel = new Label();
        if (map.getPlayer().getItemUrl() != null) {
            Image image = new Image(map.getPlayer().getItemUrl());
            imageLabel.setGraphic(new ImageView(image));
            ui.add(imageLabel, inventoryColumnIndex, inventoryRowIndex);
            if (inventoryColumnIndex == 1) {
                inventoryColumnIndex = 0;
                inventoryRowIndex++;
            } else {
                inventoryColumnIndex++;
            }
        }
        refresh();
    }

    // TODO
    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
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
            // TODO move to key released
            case S:
                savePlayer();
                break;
            // z key for any query testing
            case Z:
//                Player newPlayer = map.getPlayer();
//                PlayerModel oldPlayer = dbManager.getSavedPlayer(4);
//                System.out.println(oldPlayer);
                dbManager.saveGameState(map);
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
        boolean isGoingDown = map.getPlayer().isGoingDown();
        boolean isGoingUp = map.getPlayer().isGoingUp();
        if (isGoingDown || isGoingUp){
            Player currentPlayer = map.getPlayer();
            int X = map.getPlayer().getX();
            int Y = map.getPlayer().getY();
            Cell oldPlayerCell = map.getCell(X,Y);
            map.setPlayer(null);
            oldPlayerCell.setActor(null);
            if (isGoingDown){
                changeLevel(currentPlayer, map2, staircaseExits.DOWNSTAIRS_X.getValue(), staircaseExits.DOWNSTAIRS_Y.getValue());
            } else {
                changeLevel(currentPlayer, map1, staircaseExits.UPSTAIRS_X.getValue(), staircaseExits.UPSTAIRS_Y.getValue());
            }
            map.getPlayer().setGoingDown(false);
            map.getPlayer().setGoingUp(false);
        }
    }

    // TODO

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }
    // TODO

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }

    private void changeLevel(Player currentPlayer, GameMap map2, int X, int Y) {
        map = map2;
        Cell currentPlayerCell = map.getCell(X, Y);
        currentPlayer.setCell(currentPlayerCell);
        map.setPlayer(currentPlayer);
        currentPlayerCell.setActor(currentPlayer);
    }

    private void savePlayer() {
        String playerName;
        playerName = getPlayerName().get();
        if (playerName.equals("NoName")) {
            flashMessage("The progress is not saved!");
            return;
        }
        else if (playerName.equals("")) {
            int lastId = dbManager.getTheLastPlayerId();
            playerName = "Player" + (lastId + 1);
            flashMessage("Your progress has been saved under the name " + "'" + playerName + "'");
            // TODO saveNewPlayer() - Saves the current state (current map, player position, and inventory content) in the database.
        }
        else {
            int playerId = dbManager.getPlayerIdIfPlayerNameExist(playerName);
            if (playerId == -1){
                // new Player
                // TODO saveNewPlayer() - Saves the current state (current map, player position, and inventory content) in the database.
                flashMessage("Your progress has been saved");
            } else {
                String updateChoice = overwriteMessage().get();
                if (updateChoice.equals("Yes")) {
                    // TODO updateCurrentPlayer() - Update the current state (current map, player position, and inventory content) in the database.
                    flashMessage("Your progress has been updated");
                } else {
                    savePlayer();
                }
            }
        }

//        map.getPlayer().setName();
//        Player player = map.getPlayer();
//        dbManager.savePlayer(player);
    }
}
