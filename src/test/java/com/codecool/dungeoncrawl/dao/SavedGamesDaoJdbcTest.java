package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.SavedGameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
    Before running the test, make sure that the
    environment variables for the test are also set
 */

class SavedGamesDaoJdbcTest {
    private GameDatabaseManager databaseManager;
    private SavedGamesDao savedGamesDao;
    private final SavedGameModel firstModel = getModel("New saved Game");
    private final SavedGameModel secondModel = getModel("Another game");

    @BeforeEach
    public void clearDB() throws SQLException {
        databaseManager = new GameDatabaseManager();
        databaseManager.setup();
        savedGamesDao = new SavedGamesDaoJdbc(databaseManager.connect());
        try (Connection connection = databaseManager.connect().getConnection()) {
            String sql = "TRUNCATE saved_games CASCADE;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error, cannot connect database", e);
        }
    }

    @Test
    void add_whenSavedNewGameViaDao_addsNewGameCorrectlyAndSetItsId() {
        // given
        // when
        savedGamesDao.add(firstModel);
        // then
        List<SavedGameModel> savedGames = databaseManager.getAllSavedGames();
        assertNotNull(savedGames);
        assertEquals(1, savedGames.size());
        SavedGameModel testedGame = savedGames.get(savedGames.size() - 1);
        checkAssertions(firstModel, testedGame);
        assertNotNull(testedGame.getGameStateId());
    }

    @Test
    void whenSavedNewGameThatIsNull_ThrowsNullPointerException(){
        // given
        SavedGameModel gameModel = null;
        // when
        Executable e = () -> savedGamesDao.add(gameModel);
        //then
        NullPointerException exception = assertThrows(NullPointerException.class, e);
        assertNull(exception.getMessage());
    }

    @Test
    void update_whenUpdateSavedGameDataViaDao_dataUpdatesCorrectly() {
        // given
        savedGamesDao.add(firstModel);
        SavedGameModel savedGame = databaseManager.getSavedGame(firstModel.getSaveName());
        int savedGameId = savedGame.getId();
        int newGameStateId = 5;
        SavedGameModel gameToUpdate = new SavedGameModel(savedGameId, newGameStateId, firstModel.getSaveName());
        // when
        savedGamesDao.update(gameToUpdate);
        // then
        List<SavedGameModel> savedGames = databaseManager.getAllSavedGames();
        SavedGameModel testedGame = savedGames.get(savedGames.size() - 1);
        checkAssertions(gameToUpdate, testedGame);
        assertEquals(testedGame.getId(), gameToUpdate.getId());
    }

    @Test
    void whenUpdateSavedGameDataWithGameThatIsNull_ThrowsNullPointerException(){
        // given
        savedGamesDao.add(firstModel);
        // when
        SavedGameModel gameToUpdate = null;
        Executable e = () -> savedGamesDao.update(gameToUpdate);
        // then
        NullPointerException exception = assertThrows(NullPointerException.class, e);
        assertNull(exception.getMessage());
    }

    @Test
    void get_whenGetSavedGameByNameViaDao_ReturnRequiredGameCorrectly() {
        // given
        savedGamesDao.add(firstModel);
        // when
        SavedGameModel testedGame = savedGamesDao.get(firstModel.getSaveName());
        // then
        assertNotNull(testedGame);
        checkAssertions(firstModel, testedGame);
        assertEquals(firstModel.getId(), testedGame.getId());
    }

    @Test
    void whenGetSavedGameByInvalidName_ReturnNull() {
        // given
        savedGamesDao.add(firstModel);
        // when
        SavedGameModel testedGame = savedGamesDao.get("Invalid name");
        // then
        assertNull(testedGame);
    }

    @Test
    void whenGetSavedGameFromDBWithNoRecords_ReturnNull() {
        // given
        // when
        SavedGameModel testedGame = savedGamesDao.get("Tested game");
        // then
        assertNull(testedGame);
    }

    @Test
    void getAll_WhenGetAllSavedGamesViaDao_ReturnListOfAllGamesCorrectly() {
        // given
        savedGamesDao.add(firstModel);
        savedGamesDao.add(secondModel);
        // when
        List<SavedGameModel> savedGames = databaseManager.getAllSavedGames();
        // then
        assertNotNull(savedGames);
        assertEquals(2, savedGames.size());
        checkAssertions(firstModel, savedGames.get(0));
        checkAssertions(secondModel, savedGames.get(1));
    }

    @Test
    void getAll_WhenGetAllSavedGamesFromDBWithNoRecords_ReturnListWithZeroElements() {
        // given
        // when
        List<SavedGameModel> savedGames = databaseManager.getAllSavedGames();
        // then
        assertNotNull(savedGames);
        assertEquals(0, savedGames.size());
    }

    private SavedGameModel getModel(String gameName) {
        int gameStateId = databaseManager != null ? databaseManager.getLastSavedGameId() : 0;
        return new SavedGameModel(gameStateId, gameName);
    }

    private void checkAssertions(SavedGameModel gameModel, SavedGameModel testedGame) {
        assertEquals(gameModel.getSaveName(), testedGame.getSaveName());
        assertEquals(gameModel.getGameStateId(), testedGame.getGameStateId());
    }
}