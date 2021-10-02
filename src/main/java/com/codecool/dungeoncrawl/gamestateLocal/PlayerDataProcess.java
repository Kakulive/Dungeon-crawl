package com.codecool.dungeoncrawl.gamestateLocal;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.json.JSONObject;

import java.util.List;

public interface PlayerDataProcess {
        void export(PlayerModel player, JSONObject jo, GameStateModel state, GameMap map1, GameMap map2);
        void load(PlayerModel player);
        List<PlayerModel> importAll();
}
