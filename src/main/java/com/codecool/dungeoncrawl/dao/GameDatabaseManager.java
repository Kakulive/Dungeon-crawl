package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    static Map<String, String> env = System.getenv();
    Connection conn;


    public Connection getConnection() throws SQLException {
        final String DRIVER = "org.postgresql.Driver";
        String url = env.get("APP_DB_URL");
        String name = env.get("APP_DB_NAME");
        String password = env.get("APP_DB_PASSWORD");
        String user = env.get("APP_DB_USER");
//        Connection conn = null;
        try {
            Class.forName(DRIVER);} catch (ClassNotFoundException e) {
            System.out.println("No JDBC Driver Found");
            e.printStackTrace();
        }
        try {
            conn= DriverManager.getConnection(url,user,password);
//          Statement stat = conn.createStatement();
            System.out.println("Connection successed!");
        } catch (SQLException e) {
            System.out.println("Problem with database connection.");
            e.printStackTrace();
        }
        conn= DriverManager.getConnection(url,user,password);

        return conn;
    };


    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {

        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = env.get("APP_DB_NAME");
        String dbUser = env.get("APP_DB_USER");
        String dbPassword = env.get("APP_DB_PASSWORD");
        String dbUrl = env.get("APP_DB_URL");
        String dbPortString = env.get("APP_DB_PORT");
        int [] dbPort = new int[Integer.parseInt(dbPortString)];

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setPortNumbers(dbPort);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");
        return dataSource;
    }

    public Connection getConn() {
        return conn;
    }
}