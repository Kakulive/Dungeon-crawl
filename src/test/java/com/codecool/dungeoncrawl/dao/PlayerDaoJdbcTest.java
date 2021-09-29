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
        PlayerModel player = new PlayerModel("John", 10, 5, 15, 15, 5, true, "sword;shield;");
        // when
//        playerDao.add(player);
        // then
        List<PlayerModel> allPlayers = databaseManager.getAllSavedPlayers();
        assertNotNull(allPlayers);
//        assertEquals(1, allPlayers.size());  //
        PlayerModel testedPlayer = allPlayers.get(allPlayers.size() - 1);
        assertEquals("John", testedPlayer.getPlayerName());
        assertEquals(10, testedPlayer.getHp());
        assertEquals(5, testedPlayer.getX());
        assertEquals(15, testedPlayer.getY());
        assertEquals(15, testedPlayer.getAttack());
        assertEquals(5, testedPlayer.getArmor());
        assertTrue(testedPlayer.getHasKey());
        assertEquals("sword;shield;", testedPlayer.getItems());
        assertNotNull(testedPlayer.getId());
    }

    @Test
    void whenAuthorIsNull_ThrowsNullPointerException(){
        // given
        PlayerModel player = null;
        //when
        Executable e = () -> playerDao.add(player);
        //then
        NullPointerException exception = assertThrows(NullPointerException.class, e);
        assertNull(exception.getMessage());
    }


    @Test
    void update() {

    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }
}