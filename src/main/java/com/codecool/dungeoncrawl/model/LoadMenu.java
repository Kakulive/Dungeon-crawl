package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;

import javax.swing.*;
import java.util.List;

public class LoadMenu extends JFrame {

    // Constructor
    public LoadMenu(GameDatabaseManager dbManager) {
        String[] columnNames = {"Number", "Game name", "Load"};
        Object[][] data = getData(dbManager, columnNames);
        JTable table = new JTable(data, columnNames);

        // Set custom renderer to teams column
        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());

        // Set custom editor to teams column
        table.getColumnModel().getColumn(2).setCellEditor(new ButtonRenderer.ButtonEditor(new JTextField()));

        JScrollPane pane = new JScrollPane(table);
        getContentPane().add(pane);
        setTitle("Load Game");
        setSize(980, 680);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public static void load(GameDatabaseManager dbManager) {
        LoadMenu menu = new LoadMenu(dbManager);
        menu.setVisible(true);
    }

    private Object[][] getData(GameDatabaseManager dbManager, String[] columnNames) {
        List<SavedGameModel> allSavedGames = dbManager.getAllSavedGames();
        int columnsNumber = columnNames.length;
        int gamesListLength = allSavedGames.size();
        Object[][] data = new String[columnsNumber][gamesListLength];
        for (int i = 0; i < columnsNumber; i++) {
            data[i][0] = String.valueOf(i+1);
            data[i][1] = allSavedGames.get(i).getSaveName();
            data[i][2] = String.valueOf(allSavedGames.get(i).getId());
        }
        return data;
    }
}