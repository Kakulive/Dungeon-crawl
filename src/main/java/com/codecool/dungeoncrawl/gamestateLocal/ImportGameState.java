package com.codecool.dungeoncrawl.gamestateLocal;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.utils.MessageFlashing;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImportGameState extends JPanel {
    private MessageFlashing messageFlashing = new MessageFlashing();
    private JFileChooser chooser = new JFileChooser();


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
                String items = ((String) gameState.get("items"));
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


}
