package com.codecool.dungeoncrawl.gamestateLocal;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.utils.MessageFlashing;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImportGameState extends JPanel {
    private MessageFlashing messageFlashing = new MessageFlashing();
    private JFileChooser chooser = new JFileChooser();
    private int importStatus = 0;


    public GameMap chooseLocationToImport(GameMap map, GameMap map1, GameMap map2, Inventory inventory) {
        JSONParser jsonParser = new JSONParser();
//        this.map = map;
        prepareLocationSelectWindow("Select file to import");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileReader reader = new FileReader(chooser.getSelectedFile());
                Object obj = jsonParser.parse(reader);
                JSONObject gameState = (JSONObject) obj;
                Player player = map.getPlayer();
                deletePlayerFromOldMap(map, map.getPlayer());
//                deletePlayerFromOldMap(map2, map2.getPlayer());
                setItemsAndEnemiesOnTheMap(map1, map2, gameState);

                String mapName = (String) gameState.get("current map");
                if (mapName.equals("/map.txt")) {
                    map = map1;
                } else {
                    map = map2;
                }
                int playerX = (Integer.parseInt(String.valueOf((Long) gameState.get("X"))));
                int playerY = (Integer.parseInt(String.valueOf((Long) gameState.get("Y"))));
                setPlayerOnMap(player, map, playerX, playerY);
                addItemsToInventory(gameState, player, inventory);
                setPlayerParameters(gameState, player);
                this.importStatus = 1;
                messageFlashing.showImportAndExportAlerts("Game state imported successfully!");
            } catch (FileNotFoundException e) {
                messageFlashing.showImportAndExportAlerts("File not found");
                e.printStackTrace();
            } catch (ParseException e) {
                messageFlashing.showImportAndExportAlerts("An error occurred.");
                e.printStackTrace();
            } catch (IOException e) {
                messageFlashing.showImportAndExportAlerts("An error occurred.");
                e.printStackTrace();
            }

        } else {
            System.out.println("No Selection ");
        }
        return map;
    }

//    public GameMap getMap() {
//        return map;
//    }

    private void addItemsToInventory(JSONObject gameState, Player player, Inventory inventory) {
        ArrayList items = ((ArrayList) gameState.get("items"));
        player.getInventory().clearTnventory();
        if (items.size() > 0) {
            for (Object item : items) {
                String singleItem = (String) item;
                inventory.addItemToInventory(singleItem);
            }
        }
    }

    private void setPlayerParameters(JSONObject gameState, Player player) {
        player.setName((String) gameState.get("player name"));
        player.setHealth(Integer.parseInt(String.valueOf((Long) gameState.get("HP"))));
        player.setArmor(Integer.parseInt(String.valueOf((Long) gameState.get("Armor"))));
        player.setAttack(Integer.parseInt(String.valueOf((Long) gameState.get("Attack"))));
        player.setHasKey((Boolean) gameState.get("hasKey"));
    }

    private void setItemsAndEnemiesOnTheMap(GameMap map1, GameMap map2, JSONObject gameState) {
        clearMaps(map1, map2);

        ArrayList map1Enemies = (ArrayList) gameState.get("map 1 enemy list");
        ArrayList map2Enemies = (ArrayList) gameState.get("map 2 enemy list");
        ArrayList map1Items = (ArrayList) gameState.get("map 1 item list");
        ArrayList map2Items = (ArrayList) gameState.get("map 2 item list");

        setEnemiesOnMap(map1, map1Enemies);
        setEnemiesOnMap(map2, map2Enemies);
        setItemsOnMap(map1, map1Items);
        setItemsOnMap(map2, map2Items);
    }

    private void prepareLocationSelectWindow(String title) {

        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
        chooser.addChoosableFileFilter(filter);
    }

    private void deletePlayerFromOldMap(GameMap map,  Player player) {
        int X = player.getX();
        int Y = player.getY();
        Cell oldPlayerCell = map.getCell(X, Y);
        map.setPlayer(null);
        oldPlayerCell.setActor(null);
        oldPlayerCell.setType(CellType.FLOOR);
    }

    private void deleteEnemiesFromMap(GameMap map) {
        List<Actor> enemyList = map.getAllEnemiesList();
        for (Actor enemy : enemyList) {
            Cell enemyCell = map.getCell(enemy.getX(), enemy.getY());
            enemyCell.setActor(null);
            enemyCell.setType(CellType.FLOOR);
        }
    }

    private void deleteItemsFromMap(GameMap map) {
        List<Item> itemList = map.getAllItemsList();
        for (Item item : itemList) {
            Cell itemCell = item.getCell();
            itemCell.setItem(null);
            itemCell.setType(CellType.FLOOR);
        }
    }

    private void setPlayerOnMap(Player player, GameMap map, int X, int Y) {
        Cell currentPlayerCell = map.getCell(X, Y);
        player.setCell(currentPlayerCell);
        map.setPlayer(player);
        currentPlayerCell.setActor(player);
    }

    private void clearMaps(GameMap map1, GameMap map2) {
        deleteItemsFromMap(map1);
        deleteItemsFromMap(map2);
        deleteEnemiesFromMap(map1);
        deleteItemsFromMap(map2);
        map1.getEnemiesList().clear();
        map2.getEnemiesList().clear();
    }

    private void setEnemiesOnMap(GameMap map, ArrayList enemies) {
        map.getEnemiesList().clear();
        for (Object enemy : enemies) {
            String singleItem = (String) enemy;
            String[] splittedItem = singleItem.split(";");
            String name = splittedItem[0];
            int x = Integer.parseInt(splittedItem[1]);
            int y = Integer.parseInt(splittedItem[2]);
            CellType cellType = CellType.FLOOR;
            map.getCell(x, y).setType(cellType);
            switch (name){
                case "skeleton":
                    Skeleton skeleton = new Skeleton(map.getCell(x, y));
                    map.addEnemyToList(skeleton);
                    break;
                case "wizard":
                    Wizard wizard = new Wizard(map.getCell(x, y));
                    map.addEnemyToList(wizard);
                    break;
                case "ghost":
                    Ghost ghost = new Ghost(map.getCell(x, y));
                    map.addEnemyToList(ghost);
                    break;
                case "spider":
                    Spider spider = new Spider(map.getCell(x, y));
                    map.addEnemyToList(spider);
                    break;
            }


        }
    }

    public int getImportStatus() {
        return importStatus;
    }

    private void setItemsOnMap(GameMap map, ArrayList items) {
        for (Object item : items) {
            String singleItem = (String) item;
            String[] splittedItem = singleItem.split(";");
            String name = splittedItem[0];
            int x = Integer.parseInt(splittedItem[1]);
            int y = Integer.parseInt(splittedItem[2]);
            CellType cellType = null;
                    switch (name){
                        case "key":
                            cellType = CellType.KEY;
                            map.getCell(x, y).setType(cellType);
                            Key key = new Key(map.getCell(x,y));
                            break;
                        case "shield":
                            cellType = CellType.SHIELD;
                            map.getCell(x, y).setType(cellType);
                            Shield shield = new Shield(map.getCell(x,y));
                            break;
                        case "sword":
                            cellType = CellType.SWORD;
                            map.getCell(x, y).setType(cellType);
                            Sword sword = new Sword(map.getCell(x,y));
                            break;
                        case "heart":
                            cellType = CellType.HEART;
                            map.getCell(x, y).setType(cellType);
                            Heart heart = new Heart(map.getCell(x,y));
                            break;
                    }
                    map.getCell(x, y).setType(cellType);

                }
            }
        }


