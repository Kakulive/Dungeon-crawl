package com.codecool.dungeoncrawl.savegame;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.GameMap;

import static com.codecool.dungeoncrawl.logic.utils.Messages.flashMessage;
import static com.codecool.dungeoncrawl.savegame.Modals.saveModal;
import static com.codecool.dungeoncrawl.savegame.Modals.overwriteModal;

public class SaveGame {

    public static void saveGame(GameDatabaseManager dbManager, GameMap map) {
        String playerName;
        playerName = saveModal().get();
        if (playerName.equals("NoName")) { flashMessage("The progress is not saved!"); }
        else if (playerName.equals("")) { saveWhenNameNotEntered(dbManager, map); }
        else { saveWhenNameEntered(dbManager, map, playerName); }
    }

    private static void saveWhenNameNotEntered(GameDatabaseManager dbManager, GameMap map) {
        String playerName;
        int lastId = dbManager.getTheLastPlayerId();
        playerName = "Player" + (lastId + 1);
        flashMessage("Your progress has been saved under the name " + "'" + playerName + "'");
        saveCurrentGame(dbManager, map, playerName);
    }

    private static void saveWhenNameEntered(GameDatabaseManager dbManager, GameMap map, String playerName) {
        int playerId = dbManager.getPlayerIdIfPlayerNameExist(playerName);
        if (playerId == -1){
            // new Player
            saveCurrentGame(dbManager, map, playerName);
            flashMessage("Your progress has been saved!");
        } else {
            // existing Player
            String choiceToUpdate = overwriteModal().get();
            if (choiceToUpdate.equals("Yes")) {
                updateCurrentGame(dbManager, map, playerName, playerId);
            } else {
                saveGame(dbManager, map);
            }
        }
    }

    private static void saveCurrentGame(GameDatabaseManager dbManager, GameMap map, String playerName) {
        map.getPlayer().setName(playerName);
        dbManager.savePlayer(map.getPlayer());
        dbManager.saveGameState(map);
    }

    private static void updateCurrentGame(GameDatabaseManager dbManager, GameMap map, String playerName, int playerId) {
        map.getPlayer().setName(playerName);
        dbManager.updatePlayer(map.getPlayer(), playerId);
        dbManager.updateGameState(map, playerId);
        flashMessage("Your progress has been updated!");
    }
}
