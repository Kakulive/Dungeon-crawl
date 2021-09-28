package com.codecool.dungeoncrawl.dao;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class ExportGameStateDao extends JPanel {
    JFileChooser chooser = new JFileChooser();


    public void chooseLocationToSave() {
        prepareLocationSelectWindow("Select directory and insert filename to save.");
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            FileWriter myWriter;
            try {
                File file = new File(chooser.getSelectedFile() + ".json");
                if(file.exists()) {
                    myWriter = new FileWriter(chooser.getSelectedFile());
                }
                else{
                    myWriter = new FileWriter(chooser.getSelectedFile() + ".json");
                }
                myWriter.write("Tralalal123");
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            System.out.println("No Selection ");
        }
    }

    public void chooseLocationToLoad() {
        ArrayList<String> gamestateLoadFromFile = new ArrayList<>();
        prepareLocationSelectWindow("Select file to import");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File myObj = new File(chooser.getSelectedFile() + ".json");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    gamestateLoadFromFile.add(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
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







