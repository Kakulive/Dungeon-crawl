package com.codecool.dungeoncrawl.gamestateLocal;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;

import com.codecool.dungeoncrawl.logic.items.Inventory;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.utils.MessageFlashing;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.json.JSONObject;

public class ExportGameState extends JPanel implements PlayerDataProcess {
    private MessageFlashing messageFlashing = new MessageFlashing();
    private JFileChooser chooser = new JFileChooser();
    private PlayerModel playerModel;
    private GameStateModel state;
    private Inventory inventory = new Inventory();

    public void chooseLocationToSave(Player player, GameMap map, GameMap map1, GameMap map2, Inventory inventory) {
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
                export(playerModel, jo, state, map1, map2);
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
    public void export(PlayerModel player, JSONObject jo, GameStateModel state, GameMap map1, GameMap map2) {
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
        jo.put("items", inventory.getInventoryList());
        jo.put("current map", state.getCurrentMap());
        String[] enemyList1 = toStringConverter(map1.getEnemiesList());
        String[] enemyList2 = toStringConverter(map2.getEnemiesList());
        String[] itemList1 = toStringConverterItem(map1.getItemsList());
        String[] itemList2 = toStringConverterItem(map2.getItemsList());

        jo.put("map 1 enemy list", enemyList1);
        jo.put("map 1 item list", itemList1);
        jo.put("map 2 enemy list", enemyList2);
        jo.put("map 2 item list", itemList2);
    }
    private String[] toStringConverter(List<Actor> list){
        ArrayList<String> listToReturn = new ArrayList<>();
    for (Actor obj : list)
    {
        String x = String.valueOf(obj.getCell().getX());
        String y = String.valueOf(obj.getCell().getY());
        String name = obj.getTileName();
        listToReturn.add(name+';'+x+';'+y);
    }
        return listToReturn.toArray(new String[0]);
    }

    private String[] toStringConverterItem(List<Item> list){
        ArrayList<String> listToReturn = new ArrayList<>();
        for (Item obj : list)
        {
            String name = obj.getTileName();
            String x = String.valueOf(obj.getCell().getX());
            String y = String.valueOf(obj.getCell().getY());

            listToReturn.add(name+';'+x+';'+y);
        }
        return listToReturn.toArray(new String[0]);
    }



    @Override
    public void load(PlayerModel player) {
    }

    @Override
    public List<PlayerModel> importAll() {
        return null;
    }
}







