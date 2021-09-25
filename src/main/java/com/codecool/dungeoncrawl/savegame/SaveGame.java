package com.codecool.dungeoncrawl.savegame;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;

import static com.codecool.dungeoncrawl.logic.utils.Messages.flashMessage;
import static com.codecool.dungeoncrawl.savegame.Modals.getPlayerName;
import static com.codecool.dungeoncrawl.savegame.Modals.overwriteMessage;

public class SaveGame {

    public static void saveGame(GameDatabaseManager dbManager) {
        String playerName;
        playerName = getPlayerName().get();
        if (playerName.equals("NoName")) { flashMessage("The progress is not saved!"); }
        else if (playerName.equals("")) { saveWhenNameNotEntered(dbManager); }
        else { saveWhenNameEntered(dbManager, playerName); }

        // TODO
//        map.getPlayer().setName();
//        Player player = map.getPlayer();
//        dbManager.savePlayer(player);
    }

    private static void saveWhenNameNotEntered(GameDatabaseManager dbManager) {
        String playerName;
        int lastId = dbManager.getTheLastPlayerId();
        playerName = "Player" + (lastId + 1);
        flashMessage("Your progress has been saved under the name " + "'" + playerName + "'");
        // TODO saveNewPlayer() - Saves the current state (current map, player position, and inventory content) in the database.
    }

    private static void saveWhenNameEntered(GameDatabaseManager dbManager, String playerName) {
        int playerId = dbManager.getPlayerIdIfPlayerNameExist(playerName);
        if (playerId == -1){
            // new Player
            // TODO saveNewPlayer() - Saves the current state (current map, player position, and inventory content) in the database.
            flashMessage("Your progress has been saved!");
        } else {
            String updateChoice = overwriteMessage().get();
            if (updateChoice.equals("Yes")) {
                // TODO updateCurrentPlayer() - Update the current state (current map, player position, and inventory content) in the database.
                flashMessage("Your progress has been updated!");
            } else {
                saveGame(dbManager);
            }
        }
    }
}
