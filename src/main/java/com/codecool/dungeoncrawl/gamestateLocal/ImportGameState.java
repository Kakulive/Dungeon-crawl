package com.codecool.dungeoncrawl.gamestateLocal;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.logic.utils.MessageFlashing;
import com.codecool.dungeoncrawl.logic.Cell;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImportGameState extends JPanel {
    private MessageFlashing messageFlashing = new MessageFlashing();
    private JFileChooser chooser = new JFileChooser();
//    private Cell cell = new Cell();
//    private Sword sword = new Sword(cell);
//    private Shield shield = new Shield(cell);
//    private Key key = new Key(cell);


    public void chooseLocationToImport(GameMap map) {
        JSONParser jsonParser = new JSONParser();
        prepareLocationSelectWindow("Select file to import");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileReader reader = new FileReader(chooser.getSelectedFile());
                Object obj = jsonParser.parse(reader);
                JSONObject gameState = (JSONObject) obj;
                Player player = map.getPlayer();
                player.setName((String) gameState.get("player name"));
                player.setHealth(Integer.parseInt(String.valueOf((Long) gameState.get("HP"))));
                player.setArmor(Integer.parseInt(String.valueOf((Long) gameState.get("Armor"))));
                player.setAttack(Integer.parseInt(String.valueOf((Long) gameState.get("Attack"))));
                player.setHasKey((Boolean) gameState.get("hasKey"));
//                String items = ((String) gameState.get("items"));
//                if(items.length() > 0){
//                    String[] itemsSplitted = items.split(";");
//                    for (String item:itemsSplitted) {
//                        switch (item){
//                            case "sword":
//                                player.getInventory().addItemToInventory(sword);
//                                break;
//                            case "key":
//                                player.getInventory().addItemToInventory(key);
//                                break;
//                            case "shield":
//                                player.getInventory().addItemToInventory(shield);
//                                break;
//                        }
//                    }
//                }

            messageFlashing.showImportAndExportAlerts("Game state imported successfully!");
            } catch (FileNotFoundException e) {
                messageFlashing.showImportAndExportAlerts("File not found");
                e.printStackTrace();}
            catch (ParseException e) {
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
}
