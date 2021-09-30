package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;
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

class PlayerDaoJdbcTest {
    private GameDatabaseManager databaseManager;
    private PlayerDao playerDao;
    PlayerModel firstPlayer = new PlayerModel("John", 10, 5, 15, 15, 5, false, "sword;shield;");
    PlayerModel secondPlayer = new PlayerModel("Brad Pitt", 50, 10, 10, 30, 40, false, "sword;shield;key;");

    @BeforeEach
    public void clearDB() throws SQLException {
        databaseManager = new GameDatabaseManager();
        databaseManager.setup();
        playerDao = new PlayerDaoJdbc(databaseManager.connect());
        try (Connection connection = databaseManager.connect().getConnection()) {
            String sql = "TRUNCATE player CASCADE;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error, cannot connect database", e);
        }
    }

    @Test
    void add_whenAddingNewPlayerViaDao_addsPlayerCorrectlyAndSetItsId() {
        // given
        // when
        playerDao.add(firstPlayer);
        // then
        List<PlayerModel> savedPlayers = databaseManager.getAllSavedPlayers();
        assertNotNull(savedPlayers);
        assertEquals(1, savedPlayers.size());
        PlayerModel testedPlayer = savedPlayers.get(savedPlayers.size() - 1);
        checkAssertions(firstPlayer, testedPlayer);
        assertNotNull(testedPlayer.getId());
    }

    @Test
    void whenAddPlayerThatIsNull_ThrowsNullPointerException() {
        // given
        PlayerModel player = null;
        //when
        Executable e = () -> playerDao.add(player);
        //then
        NullPointerException exception = assertThrows(NullPointerException.class, e);
        assertNull(exception.getMessage());
    }

    @Test
    void update_whenUpdatePlayerDataViaDao_dataUpdatesCorrectly() {
        // given
        playerDao.add(firstPlayer);
        List<PlayerModel> savedPlayers = databaseManager.getAllSavedPlayers();
        int playerId = savedPlayers.get(savedPlayers.size() - 1).getId();
        // when
        playerDao.update(secondPlayer, playerId);
        // then
        List<PlayerModel> updatedPlayer = databaseManager.getAllSavedPlayers();
        PlayerModel testedPlayer = updatedPlayer.get(updatedPlayer.size() - 1);
        checkAssertions(secondPlayer, testedPlayer);
        assertEquals(playerId, testedPlayer.getId());
    }

    @Test
    void whenUpdatePlayerDataWithPlayerThatIsNull_ThrowsNullPointerException() {
        // given
        playerDao.add(firstPlayer);
        PlayerModel player = null;
        List<PlayerModel> savedPlayers = databaseManager.getAllSavedPlayers();
        int playerId = savedPlayers.get(savedPlayers.size() - 1).getId();
        //when
        Executable e = () -> playerDao.update(player, playerId);
        //then
        NullPointerException exception = assertThrows(NullPointerException.class, e);
        assertNull(exception.getMessage());
    }

    @Test
    void get_whenGetPlayerByIdViaDao_ReturnRequiredPlayerCorrectly() {
        // given
        playerDao.add(firstPlayer);
        List<PlayerModel> savedPlayers = databaseManager.getAllSavedPlayers();
        int playerId = savedPlayers.get(savedPlayers.size() - 1).getId();
        // when
        PlayerModel testedPlayer = playerDao.get(playerId);
        // then
        assertNotNull(testedPlayer);
        checkAssertions(firstPlayer, testedPlayer);
    }

    @Test
    void whenGetPlayerByInvalidId_ReturnNull() {
        // given
        int playerId = -1;
        // when
        PlayerModel testedPlayer = playerDao.get(playerId);
        // then
        assertNull(testedPlayer);
    }

    @Test
    void getAll_WhenGetAllPlayersViaDao_ReturnListOfAllPlayersCorrectly() {
        // given
        playerDao.add(firstPlayer);
        playerDao.add(secondPlayer);
        // when
        List<PlayerModel> savedPlayers = databaseManager.getAllSavedPlayers();
        // then
        assertNotNull(savedPlayers);
        assertEquals(2, savedPlayers.size());
        checkAssertions(firstPlayer, savedPlayers.get(0));
        checkAssertions(secondPlayer, savedPlayers.get(1));
    }

    private void checkAssertions(PlayerModel expectedPlayer, PlayerModel testedPlayer) {
        assertEquals(expectedPlayer.getPlayerName(), testedPlayer.getPlayerName());
        assertEquals(expectedPlayer.getHp(), testedPlayer.getHp());
        assertEquals(expectedPlayer.getX(), testedPlayer.getX());
        assertEquals(expectedPlayer.getY(), testedPlayer.getY());
        assertEquals(expectedPlayer.getAttack(), testedPlayer.getAttack());
        assertEquals(expectedPlayer.getArmor(), testedPlayer.getArmor());
        assertFalse(testedPlayer.getHasKey());
        assertEquals(expectedPlayer.getItems(), testedPlayer.getItems());
    }
}