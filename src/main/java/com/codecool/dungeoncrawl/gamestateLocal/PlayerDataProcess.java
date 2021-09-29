package com.codecool.dungeoncrawl.gamestateLocal;

import com.codecool.dungeoncrawl.model.PlayerModel;
import org.json.JSONObject;

import java.util.List;

public interface PlayerDataProcess {
        void export(PlayerModel player, JSONObject jo);
        void load(PlayerModel player);
        List<PlayerModel> importAll();
}
