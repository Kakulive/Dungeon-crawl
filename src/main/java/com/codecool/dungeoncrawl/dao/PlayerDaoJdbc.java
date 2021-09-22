package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y, attack, armor, haskey, items ) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            populateStatement(player, statement);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, can't add new Player " + "'" + player.getPlayerName() + "'", e);
        }
    }

    @Override
    public void update(PlayerModel player, int id) { //TODO figure out how to get id to update the record
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE player " +
                    "SET " +
                    "player_name = ?, " +
                    "hp = ?, " +
                    "x = ?, " +
                    "y = ?, " +
                    "attack = ?, " +
                    "armor = ?, " +
                    "haskey = ?, " +
                    "items = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            populateStatement(player, statement);
            statement.setInt(9, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, can't update Player " + "'" + player.getPlayerName() + "'", e);
        }
    }

    @Override
    public PlayerModel get(int id) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT player_name, hp, x, y, attack, armor, haskey, items  " +
                    "FROM player " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return  null;
            }
            PlayerModel playerModel = getPlayerModel(resultSet);
            playerModel.setId(id);
            return playerModel;
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, can't read Player with id = " + id, e);
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT player_name, hp, x, y, attack, armor, items, haskey, id FROM player";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            List<PlayerModel> playersModel = new ArrayList<>();
            while (resultSet.next()) {
                PlayerModel playerModel = getPlayerModel(resultSet);
                playerModel.setId(resultSet.getInt(9));
                playersModel.add(playerModel);
            }
            return playersModel;
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, cannot read all authors", e);
        }
    }

    private void populateStatement(PlayerModel player, PreparedStatement statement) throws SQLException {
        statement.setString(1, player.getPlayerName());
        statement.setInt(2, player.getHp());
        statement.setInt(3, player.getX());
        statement.setInt(4, player.getY());
        statement.setInt(5, player.getAttack());
        statement.setInt(6, player.getArmor());
        statement.setBoolean(7, player.getHasKey());
        statement.setString(8, player.getItems());
    }

    private PlayerModel getPlayerModel(ResultSet resultSet) throws SQLException {
        return new PlayerModel(
                resultSet.getString(1),
                resultSet.getInt(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
                resultSet.getInt(5),
                resultSet.getInt(6),
                resultSet.getBoolean(7),
                resultSet.getString(8));
    }
}
