package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;


public class GameDatabaseManager {
    private PlayerDao playerDao;
    private PlayerModel playerModel;
    private GameStateDao gameStateDao;
    private GameStateModel gameStateModel;
    static Map<String, String> env = System.getenv();
    private Statement stat;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        this.playerDao = new PlayerDaoJdbc(dataSource);
        this.gameStateDao = new GameStateDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {

        this.playerModel = new PlayerModel(player);
        this.playerDao.add(playerModel);
    }

    public void updatePlayer(Player player, int id) {
        this.playerModel = new PlayerModel(player);
        this.playerDao.update(playerModel, id);
    }

    public PlayerModel getSavedPlayer(int id) {
        PlayerModel playerData = this.playerDao.get(id);
        return playerData;
    }
    public List<PlayerModel> getAllSavedPlayers() {
        return this.playerDao.getAll();
    }

    public int getTheLastPlayerId(){ return this.playerDao.getTheLastPlayerId(); }

    public int getPlayerIdIfPlayerNameExist(String name) {return  this.playerDao.getPlayerIdIfPlayerNameExist(name); }

    public void saveGameState(GameMap map) {
        this.gameStateModel = new GameStateModel(map);
        playerDao.add(gameStateModel.getPlayer());
        this.gameStateDao.add(gameStateModel);
    }

    private DataSource connect() throws SQLException {

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = env.get("APP_DB_NAME");
        String dbUser = env.get("APP_DB_USER");
        String dbPassword = env.get("APP_DB_PASSWORD");
        String[] dbHost = {env.get("APP_DB_HOST")};
        String dbPortString = env.get("APP_DB_PORT");
        int[] dbPort =new int[1];
        dbPort[0] = Integer.parseInt(dbPortString);


        dataSource.setDatabaseName(dbName);
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setServerNames(dbHost);
        dataSource.setPortNumbers(dbPort);


        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");
        return dataSource;
    }


}
