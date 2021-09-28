package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.ExportGameStateDao;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.staircaseExits;
import com.codecool.dungeoncrawl.logic.utils.Buttons;
import com.codecool.dungeoncrawl.logic.utils.SceneSwitcher;
import com.codecool.dungeoncrawl.model.LoadMenu;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.model.SavedGameModel;
import com.codecool.dungeoncrawl.model.SavedGameModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
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
import java.util.List;
import java.util.Optional;


public class Main extends Application {
    public static final String MAPNAME1 = "/map.txt";
    public static final String MAPNAME2 = "/map2.txt";

    private Buttons buttons = new Buttons();
    private Stage stage = new Stage();
    private SceneSwitcher sceneSwitcher = new SceneSwitcher();
    private ExportGameStateDao exportGameStateDao = new ExportGameStateDao();

    private GameMap map2 = MapLoader.loadMap(MAPNAME2); // DOWNSTAIRS
    private GameMap map1 = MapLoader.loadMap(MAPNAME1); // UPSTAIRS

    private GameMap map = map1;

    private final int windowWidth = map.getWidth() * Tiles.TILE_WIDTH;
    private final int windowHeight = map.getHeight() * Tiles.TILE_WIDTH;

    private Canvas canvas = new Canvas(windowWidth, windowHeight);
    private GraphicsContext context = canvas.getGraphicsContext2D();


    // TODO
    GameDatabaseManager dbManager;

    private int inventoryRowIndex = 13;
    private int inventoryColumnIndex = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO
        setupDbManager();
        this.stage = primaryStage;
        sceneSwitcher.startGameScene(stage, windowWidth, windowHeight);


        sceneSwitcher.getStartGameButton().setOnAction(event -> {
            sceneSwitcher.menuGameScene(stage, windowWidth, windowHeight);

//            sceneSwitcher.mainScene(stage, windowWidth, windowHeight, canvas);
//            sceneSwitcher.getMainScene().setOnKeyPressed(this::onKeyPressed);
            // TODO
            // scene.setOnKeyReleased(this::onKeyReleased);
//            refresh();
//            sceneSwitcher.getMainBorderPane().requestFocus(); // Brings the focus back on the map, instead of user UI
        });
        sceneSwitcher.getSetPlayer().setOnAction(event -> {
            sceneSwitcher.changeMenuIfStart(stage, windowWidth, windowHeight);
        });

        sceneSwitcher.getAddStatHealth().setOnAction(event -> {
            buttons.addStatButtons(sceneSwitcher, 1, map);
        });

        sceneSwitcher.getAddStatArmor().setOnAction(event -> {
            buttons.addStatButtons(sceneSwitcher, 2, map);
        });

        sceneSwitcher.getAddStatAttack().setOnAction(event -> {
            buttons.addStatButtons(sceneSwitcher, 3, map);
        });

        sceneSwitcher.getSubStatHealth().setOnAction(event -> {
            buttons.subStatButtons(sceneSwitcher, 1, map);
        });

        sceneSwitcher.getSubStatArmor().setOnAction(event -> {
            buttons.subStatButtons(sceneSwitcher, 2, map);
        });

        sceneSwitcher.getSubStatAttack().setOnAction(event -> {
            buttons.subStatButtons(sceneSwitcher, 3, map);
        });

        sceneSwitcher.getSubmitButton().setOnAction(event -> {
            if (buttons.validInputsAddingMenu(sceneSwitcher)) {
                buttons.submitButtonDo(map, sceneSwitcher);
                String userName = sceneSwitcher.getPlayerNameInput().getText();
                map.getPlayer().setName(userName);
                sceneSwitcher.getName().setText(userName);
                sceneSwitcher.mainScene(stage, windowWidth, windowHeight, canvas);
                sceneSwitcher.getMainScene().setOnKeyPressed(this::onKeyPressed);
                refresh();
                sceneSwitcher.getMainBorderPane().requestFocus(); // Brings the focus back on the map, instead of user UI

            }

        });

        sceneSwitcher.getExportGameStateButton().setOnAction(event -> {
            exportGameStateDao.chooseLocationToSave();
        });

        sceneSwitcher.getImportGameStateButton().setOnAction(event -> {
            exportGameStateDao.chooseLocationToLoad();
        });


        sceneSwitcher.getExitButton().setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });


        sceneSwitcher.getPickUpButton().setOnAction(event -> pickUpItem(sceneSwitcher.getUi(), sceneSwitcher.getMainBorderPane()));
    }

    private void pickUpItem(GridPane ui, BorderPane borderPane) {
        if (map.getPlayer().getCell().getTileName().equals("key")) {
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
                map.getPlayer().move(1, 0);
                break;
            // TODO case S
//            case S:
//                Player player = map.getPlayer();
//                dbManager.savePlayer(player);
//                break;

            case Z: // z key for any query testing
                // this.showSaveModal();
                LoadMenu.load(dbManager);
                break;
        }
        if (map.getPlayer().isDead()) {
            sceneSwitcher.endGameScene(stage, windowWidth, windowHeight);
        }
        refresh();
        map.moveEnemies(map);
    }

    private void refresh() {
        changeCurrentMap();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, windowWidth, windowHeight);
        int mapLeftOffset = 2;
        int mapRightOffset = 3;

        int playerX = map.getPlayer().getX();
        int playerY = map.getPlayer().getY();

        if (playerX - mapLeftOffset < 0) {
            playerX = mapLeftOffset;
        }
        if (playerX + mapRightOffset > map.getWidth()) {
            playerX = map.getWidth() - mapRightOffset;
        }
        if (playerY - mapLeftOffset < 0) {
            playerY = mapLeftOffset;
        }
        if (playerY + mapRightOffset > map.getHeight()) {
            playerY = map.getHeight() - mapRightOffset;
        }

        for (int x = playerX - mapLeftOffset; x < playerX + mapRightOffset; x++) {
            for (int y = playerY - mapLeftOffset; y < playerY + mapRightOffset; y++) {
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
        if (isGoingDown || isGoingUp) {
            Player currentPlayer = map.getPlayer();
            int X = map.getPlayer().getX();
            int Y = map.getPlayer().getY();
            Cell oldPlayerCell = map.getCell(X, Y);
            map.setPlayer(null);
            oldPlayerCell.setActor(null);
            if (isGoingDown) {
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

    private void showSaveModal() {
        TextInputDialog saveDialog = createSaveModal();

        Optional<String> result = saveDialog.showAndWait();
        if (result.isPresent()) {
            String saveName = result.get();
            SavedGameModel previouslySavedGame = dbManager.getSavedGame(saveName);

            if (previouslySavedGame != null) {
                Alert overwriteAlert = createOverwriteAlert();
                Optional<ButtonType> confirmationResult = overwriteAlert.showAndWait();
                if (confirmationResult.get() == ButtonType.OK) {
                    dbManager.saveGameState(map);
                    dbManager.updateSavedGame(saveName);
                } else {
                    overwriteAlert.close();
                }
            } else {
                dbManager.saveGameState(map);
                dbManager.addSavedGame(saveName);
            }
        }

    }

    private TextInputDialog createSaveModal() {
        TextInputDialog saveDialog = new TextInputDialog();
        saveDialog.setTitle("Save Game");
        saveDialog.setHeaderText("Would you like to save your game?");
        saveDialog.setContentText("Save Game name: ");
        return saveDialog;
    }

    private Alert createOverwriteAlert() {
        Alert overwriteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        overwriteAlert.setTitle("Save Game");
        overwriteAlert.setHeaderText("Previous save found!");
        overwriteAlert.setContentText("Would you like to overwrite existing save?");
        return overwriteAlert;
    }

}
