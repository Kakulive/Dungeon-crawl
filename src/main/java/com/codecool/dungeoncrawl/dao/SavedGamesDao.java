package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.SavedGameModel;

import java.util.List;

public interface SavedGamesDao {
    void add(SavedGameModel savedGameModel);
    void update(SavedGameModel savedGameModel);
    SavedGameModel get(String saveName);
    List<GameStateModel> getAll();
}
