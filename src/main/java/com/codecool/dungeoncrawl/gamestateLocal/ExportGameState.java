package com.codecool.dungeoncrawl.gamestateLocal;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Inventory;
import com.codecool.dungeoncrawl.logic.utils.MessageFlashing;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.json.JSONObject;

public class ExportGameState extends JPanel implements PlayerDataProcess {
    private MessageFlashing messageFlashing = new MessageFlashing();
    private JFileChooser chooser = new JFileChooser();
    private PlayerModel playerModel;
    private GameStateModel state;

    public void chooseLocationToSave(Player player, GameMap map, GameMap map1, GameMap map2) {
        playerModel = new PlayerModel(player);
        state = new GameStateModel(map);

        JSONObject jo = new JSONObject();
        prepareLocationSelectWindow("Select directory and insert filename to save.");
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            FileWriter myWriter;
            try {
                File file = new File(chooser.getSelectedFile() + "");
                if (file.exists()) {
                    file.delete();
                    myWriter = new FileWriter(chooser.getSelectedFile());
                } else {
                    myWriter = new FileWriter(chooser.getSelectedFile() + ".json");
                }
                export(playerModel, jo, state);
                myWriter.write(jo.toString());
                myWriter.close();
                messageFlashing.showImportAndExportAlerts("Successfully wrote to the file.");
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

    @Override
    public void export(PlayerModel player, JSONObject jo, GameStateModel state) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = df.format(date);
        jo.put("save date", strDate);
        jo.put("player name", player.getPlayerName());
        jo.put("X", player.getX());
        jo.put("Y", player.getY());
        jo.put("HP", player.getHp());
        jo.put("Armor", player.getArmor());
        jo.put("Attack", player.getAttack());
        jo.put("hasKey", player.getHasKey());
        jo.put("items", player.getItems());
        jo.put("current map", state.getCurrentMap());
    }

    @Override
    public void load(PlayerModel player) {
    }

    @Override
    public List<PlayerModel> importAll() {
        return null;
    }
}







