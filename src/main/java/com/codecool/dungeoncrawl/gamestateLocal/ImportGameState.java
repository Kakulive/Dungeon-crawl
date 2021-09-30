package com.codecool.dungeoncrawl.gamestateLocal;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.utils.MessageFlashing;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImportGameState extends JPanel {
    private MessageFlashing messageFlashing = new MessageFlashing();
    private JFileChooser chooser = new JFileChooser();


    public void chooseLocationToImport(GameMap map, GameMap map1, GameMap map2) {
        JSONParser jsonParser = new JSONParser();

        prepareLocationSelectWindow("Select file to import");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileReader reader = new FileReader(chooser.getSelectedFile());
                Object obj = jsonParser.parse(reader);
                JSONObject gameState = (JSONObject) obj;
                Player player = map.getPlayer();
                deletePlayerFromOldMap(map, player);
                player.setName((String) gameState.get("player name"));
                player.setHealth(Integer.parseInt(String.valueOf((Long) gameState.get("HP"))));
                player.setArmor(Integer.parseInt(String.valueOf((Long) gameState.get("Armor"))));
                player.setAttack(Integer.parseInt(String.valueOf((Long) gameState.get("Attack"))));
                player.setHasKey((Boolean) gameState.get("hasKey"));
                clearMaps(map1, map2);

                String map1Enemies = (String) gameState.get("map 1 enemy list");
                String map2Enemies = (String) gameState.get("map 2 enemy list");
                String map1Items = (String) gameState.get("map 1 item list");
                String map2Items = (String) gameState.get("map 2 item list");

                String[] map1EnemiesList = map1Enemies.split(";");
                String[] map2EnemiesList = map2Enemies.split(";");
                String[] map1ItemsList = map1Items.split(";");
                String[] map2ItemsList = map2Items.split(";");


                String mapName = (String) gameState.get("current map");
                if (mapName == "/map2.txt") {
                    map = map2;
                } else {
                    map = map1;
                }
                int playerX = (Integer.parseInt(String.valueOf((Long) gameState.get("X"))));
                int playerY = (Integer.parseInt(String.valueOf((Long) gameState.get("Y"))));
                setPlayerOnMap(player, map, playerX, playerY);
                String items = ((String) gameState.get("items"));
                player.getInventory().clearTnventory();
                if (items.length() > 0) {
                    String[] singleItem = items.split(";");
                    for (String item : singleItem) {
                        player.getInventory().addItemToInventory(item);
                    }
                }
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

    }

    private void prepareLocationSelectWindow(String title) {

        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
        chooser.addChoosableFileFilter(filter);
    }

    private void deletePlayerFromOldMap(GameMap map, Player player) {
        int X = player.getX();
        int Y = player.getY();
        Cell oldPlayerCell = map.getCell(X, Y);
        map.setPlayer(null);
        oldPlayerCell.setActor(null);
    }

    private void deleteEnemiesFromMap(GameMap map) {
        List<Actor> enemyList = map.getEnemiesList();
        for (Actor enemy : enemyList) {
            Cell enemyCell = map.getCell(enemy.getX(), enemy.getY());
            enemyCell.setActor(null);
        }
    }

    private void deleteItemsFromMap(GameMap map) {
        List<Item> itemList = map.getItemsList();
        for (Item item : itemList) {
            Cell itemCell = item.getCell();
            itemCell.setItem(null);
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

    private void setEnemiesOnMap(GameMap map, String[] enemies) {
        List<Actor> allEnemiesList = map.getAllEnemiesList();
        for (String enemy : enemies) {
            for (Actor enemyToCompare : allEnemiesList) {
                if (enemyToCompare.toString().equals(enemy)) {
                    Cell enemyCell = map.getCell(enemyToCompare.getX(), enemyToCompare.getY());
                    enemyCell.setActor(enemyToCompare);
                    map.addEnemyToList(enemyToCompare);
                }
            }
        }
    }

    private void setItemsOnMap(GameMap map, String[] items) {
        List<Item> itemList = map.getItemsList();
        for (String item : items) {
            for (Item itemToCompare : itemList) {
                if (itemToCompare.toString().equals(item)) {
                    itemToCompare.getCell().setItem(itemToCompare);
                }
            }
        }
    }
}


