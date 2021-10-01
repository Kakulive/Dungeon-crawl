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
        SavedGameModel gameModel = getModel();
        // when
        savedGamesDao.add(gameModel);
        // then
        List<SavedGameModel> savedGames = databaseManager.getAllSavedGames();
        assertNotNull(savedGames);
        assertEquals(1, savedGames.size());
        SavedGameModel testedGame = savedGames.get(savedGames.size() - 1);
        checkAssertions(gameModel, testedGame);
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
        SavedGameModel gameModel = getModel();
        savedGamesDao.add(gameModel);
        SavedGameModel savedGame = databaseManager.getSavedGame(gameModel.getSaveName());
        int savedGameId = savedGame.getId();
        int newGameStateId = 5;
        SavedGameModel gameToUpdate = new SavedGameModel(savedGameId, newGameStateId, gameModel.getSaveName());
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
        SavedGameModel gameModel = getModel();
        savedGamesDao.add(gameModel);
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
        SavedGameModel gameModel = getModel();
        savedGamesDao.add(gameModel);
        // when
        SavedGameModel testedGame = savedGamesDao.get(gameModel.getSaveName());
        //
        assertNotNull(testedGame);
        checkAssertions(gameModel, testedGame);
        assertEquals(gameModel.getId(), testedGame.getId());
    }

    @Test
    void whenGetSavedGameByInvalidName_ReturnNull() {
        // given
        SavedGameModel gameModel = getModel();
        savedGamesDao.add(gameModel);
        // when
        SavedGameModel testedGame = savedGamesDao.get("Invalid name");
        // then
        assertNull(testedGame);
    }

    @Test
    void whenGetSavedGameFromEmptyDB_ReturnNull() {
        // given
        // when
        SavedGameModel testedGame = savedGamesDao.get("Tested name");
        // then
        assertNull(testedGame);
    }

    @Test
    void getAll() {
        // given

        // when

        // then
    }

    private SavedGameModel getModel() {
        String name = "New saved Game";
        int gameStateId = databaseManager.getLastSavedGameId();
        return new SavedGameModel(gameStateId, name);
    }

    private void checkAssertions(SavedGameModel gameModel, SavedGameModel testedGame) {
        assertEquals(gameModel.getSaveName(), testedGame.getSaveName());
        assertEquals(gameModel.getGameStateId(), testedGame.getGameStateId());
    }
}