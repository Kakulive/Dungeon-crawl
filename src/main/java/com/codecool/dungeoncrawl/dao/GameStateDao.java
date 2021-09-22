package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameStateModel;

import java.util.List;

public interface GameStateDao {
    void add(GameStateModel state);
    void update(GameStateModel state);
    GameStateModel get(int id);
    List<GameStateModel> getAll();
}
