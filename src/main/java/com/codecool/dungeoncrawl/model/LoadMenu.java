package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;

import javax.swing.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class LoadMenu extends JFrame {

    // Constructor
    public LoadMenu(GameDatabaseManager dbManager) {
        // Data for our table
        String[] columnNames = {"Number", "Game name", "Date", "Load"};

        // Data for test:
        Object[][] data = {
                {1, "New game", Date.valueOf("2021-09-24"), "Button"},
                {2, "New game 2", Date.valueOf("2021-09-25"), "Button"},
                {3, "New game 3", Date.valueOf("2021-09-26"), "Button"}
        };

        // Data from DB
//        Object[][] data = getData(dbManager, columnNames);

        JTable table = new JTable(data, columnNames);

        // Set custom renderer to teams column
        table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());

        // Set custom editor to teams column
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonRenderer.ButtonEditor(new JTextField()));

        /*
            ScrollPane
            Set size
            Set close operation
         */
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
        List<GameStateModel> allSavedGames = dbManager.getAllSavedGames();
        int columnsNumber = columnNames.length;
        int gamesListLength = allSavedGames.size();

        Object[][] data = new String[columnsNumber][gamesListLength];

        for (int i = 0; i < columnsNumber; i++) {
            String number = String.valueOf(i);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String stringDate = dateFormat.format(allSavedGames.get(i).getSavedAt());
            String click = String.valueOf(allSavedGames.get(i).id);
            data[i][0] = number;
            data[i][1] = allSavedGames.get(i).getCurrentMap();
            data[i][2] = stringDate;
            data[i][3] = click;
        }
        return data;
    }
}