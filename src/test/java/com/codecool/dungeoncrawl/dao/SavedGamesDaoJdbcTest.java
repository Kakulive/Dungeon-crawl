package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.SavedGameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

//    @BeforeEach
//    public void clearDB() throws SQLException {
//        databaseManager = new GameDatabaseManager();
//        databaseManager.setup();
//        savedGamesDao = new SavedGamesDaoJdbc(databaseManager.connect());
//        try (Connection connection = databaseManager.connect().getConnection()) {
//            String sql = "TRUNCATE saved_games CASCADE;";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error, cannot connect database", e);
//        }
//    }

    // Just for test
    @BeforeEach
    private void connectDB() throws SQLException{
        databaseManager = new GameDatabaseManager();
        databaseManager.setup();
        savedGamesDao = new SavedGamesDaoJdbc(databaseManager.connect());
    }

    @Test
    void add() {
        // given
        String name = "New saved Game";
        int gameStateId = databaseManager.getLastSavedGameId();
        SavedGameModel gameModel = new SavedGameModel(gameStateId, name);
        // when
        savedGamesDao.add(gameModel);
        // then
        List<SavedGameModel> savedGames = databaseManager.getAllSavedGames();
        assertNotNull(savedGames);
//        assertEquals(1, savedGames.size());
        SavedGameModel testedGame = savedGames.get(savedGames.size() - 1);
        checkAssertions(gameModel, testedGame);
        assertNotNull(testedGame.getGameStateId());
    }

    @Test
    void update() {
        // given

        // when

        // then
    }

    @Test
    void get() {
        // given

        // when

        // then
    }

    @Test
    void getAll() {
        // given

        // when

        // then
    }

    private void checkAssertions(SavedGameModel gameModel, SavedGameModel testedGame) {
        assertEquals(gameModel.getSaveName(), testedGame.getSaveName());
        assertEquals(gameModel.getGameStateId(), testedGame.getGameStateId());
    }
}