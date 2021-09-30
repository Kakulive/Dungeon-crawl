package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
    Before running the test, make sure that the
    environment variables for the test are also set
 */

// TODO uncommented all

class PlayerDaoJdbcTest {
    private GameDatabaseManager databaseManager;
    private PlayerDao playerDao;

    // Commented during the test

//    @BeforeEach
//    public void clearDB() throws SQLException {
//        databaseManager = new GameDatabaseManager();
//        databaseManager.setup();
//        playerDao = new PlayerDaoJdbc(databaseManager.connect());
//        try (Connection connection = databaseManager.connect().getConnection()) {
//            String sql = "TRUNCATE player CASCADE;";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error, cannot connect database", e);
//        }
//    }

    // Just for the test
    @BeforeEach
    private void connectDB() throws SQLException {
        databaseManager = new GameDatabaseManager();
        databaseManager.setup();
        playerDao = new PlayerDaoJdbc(databaseManager.connect());
    }

    @Test
    void add_whenAddingNewPlayerViaDao_addsPlayerCorrectlyAndSetItsId() {
        // given
        PlayerModel player = new PlayerModel("John", 10, 5, 15, 15, 5, false, "sword;shield;");
        // when
//        playerDao.add(player);
        // then
        List<PlayerModel> savedPlayers = databaseManager.getAllSavedPlayers();
        assertNotNull(savedPlayers);
//        assertEquals(1, savedPlayers.size());  //
        PlayerModel testedPlayer = savedPlayers.get(savedPlayers.size() - 1);
        assertEquals("John", testedPlayer.getPlayerName());
        assertEquals(10, testedPlayer.getHp());
        assertEquals(5, testedPlayer.getX());
        assertEquals(15, testedPlayer.getY());
        assertEquals(15, testedPlayer.getAttack());
        assertEquals(5, testedPlayer.getArmor());
        assertFalse(testedPlayer.getHasKey());
        assertEquals("sword;shield;", testedPlayer.getItems());
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
        PlayerModel playerToAdd = new PlayerModel("John", 10, 5, 15, 15, 5, false, "sword;shield;");
        playerDao.add(playerToAdd);
        PlayerModel playerToUpdate = new PlayerModel("Brad Pitt", 50, 10, 10, 30, 40, true, "sword;shield;key;");
        List<PlayerModel> savedPlayers = databaseManager.getAllSavedPlayers();
        int playerId = savedPlayers.get(savedPlayers.size() - 1).getId();
        // when
        playerDao.update(playerToUpdate, playerId);
        // then
        List<PlayerModel> updatedPlayer = databaseManager.getAllSavedPlayers();
        PlayerModel testedPlayer = updatedPlayer.get(updatedPlayer.size() - 1);
        assertEquals("Brad Pitt", testedPlayer.getPlayerName());
        assertEquals(50, testedPlayer.getHp());
        assertEquals(10, testedPlayer.getX());
        assertEquals(10, testedPlayer.getY());
        assertEquals(30, testedPlayer.getAttack());
        assertEquals(40, testedPlayer.getArmor());
        assertTrue(testedPlayer.getHasKey());
        assertEquals("sword;shield;key;", testedPlayer.getItems());
        assertEquals(playerId, testedPlayer.getId());
    }

    @Test
    void whenUpdatePlayerDataWithPlayerThatIsNull_ThrowsNullPointerException() {
        // given
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
    void get_whenGetPlayerByIdViaDao_ReturnRequiredPlayerWithActualData() {
        // given
        PlayerModel playerToAdd = new PlayerModel("John", 10, 5, 15, 15, 5, false, "sword;shield;");
        playerDao.add(playerToAdd);
        List<PlayerModel> savedPlayers = databaseManager.getAllSavedPlayers();
        int playerId = savedPlayers.get(savedPlayers.size() - 1).getId();
        // when
        PlayerModel testedPlayer = playerDao.get(playerId);
        // then
        assertNotNull(testedPlayer);
        assertEquals("John", testedPlayer.getPlayerName());
        assertEquals(10, testedPlayer.getHp());
        assertEquals(5, testedPlayer.getX());
        assertEquals(15, testedPlayer.getY());
        assertEquals(15, testedPlayer.getAttack());
        assertEquals(5, testedPlayer.getArmor());
        assertFalse(testedPlayer.getHasKey());
        assertEquals("sword;shield;", testedPlayer.getItems());
        assertNotNull(testedPlayer.getId());
    }

    // TODO when given -1 to get throws

    @Test
    void getAll() {
        // given

        // when

        // then
    }
}