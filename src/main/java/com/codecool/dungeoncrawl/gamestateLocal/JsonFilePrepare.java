package com.codecool.dungeoncrawl.gamestateLocal;
import com.codecool.dungeoncrawl.dao.PlayerDao;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JsonFilePrepare implements PlayerDataProcess{


    @Override
    public void export(PlayerModel player, JSONObject jo) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = df.format(date);
        jo.put("save date", strDate);
        jo.put("player name", player.getPlayerName());
        jo.put("X", player.getX());
        jo.put("Y", player.getY());
        jo.put("HP", player.getHp());
        jo.put("Armor", player.getPlayerName());
        jo.put("Attack", player.getPlayerName());
        jo.put("hasKey", player.getHasKey());
        jo.put("items", player.getItems());
    }


    @Override
    public void load(PlayerModel player) {

    }

    @Override
    public List<PlayerModel> importAll() {
        return null;
    }
}
