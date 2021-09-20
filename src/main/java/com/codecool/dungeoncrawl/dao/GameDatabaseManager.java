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
    private Statement stat;


    public void getConnection(String url, String name, String password, String user) throws SQLException, ClassNotFoundException {
        final String DRIVER = "org.postgresql.Driver";
        url = env.get("APP_DB_URL");
        name = env.get("APP_DB_NAME");
        password = env.get("APP_DB_PASSWORD");
        user = env.get("APP_DB_USER");
        try {
        Class.forName(DRIVER);} catch (ClassNotFoundException e) {
            System.out.println("No JDBC Driver Found");
            e.printStackTrace();
        }
        try {
            java.sql.Connection conn = DriverManager.getConnection(url);
            stat = conn.createStatement();
            System.out.println("Connection successed!");
        } catch (SQLException e) {
            System.out.println("Problem with database connection.");
            e.printStackTrace();
        }
        Connection con= DriverManager.getConnection(url,user,password);

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


}
