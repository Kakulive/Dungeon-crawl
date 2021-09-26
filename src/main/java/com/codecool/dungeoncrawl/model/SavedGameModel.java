package com.codecool.dungeoncrawl.model;

public class SavedGameModel {
    private int id;
    private int gameStateId;
    private String saveName;

    public SavedGameModel(int id, int gameStateId, String save_name) {
        this.id = id;
        this.gameStateId = gameStateId;
        this.saveName = save_name;
    }

    public SavedGameModel(int gameStateId, String saveName) {
        this.gameStateId = gameStateId;
        this.saveName = saveName;
    }

    public int getId() {
        return id;
    }

    public int getGameStateId() {
        return gameStateId;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGameStateId(int gameStateId) {
        this.gameStateId = gameStateId;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }
}
