package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameStateModel state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id ) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            populateStatement(state, statement);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, can't save the game ", e);
        }
    }

    @Override
    public void update(GameStateModel state, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE game_state " +
                    "SET " +
                    "current_map = ?, " +
                    "saved_at = ?, " +
                    "player_id = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            populateStatement(state, statement);
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, can't update game ", e);
        }
    }

    @Override
    public GameStateModel get(int id) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT id, current_map, saved_at, player_id  " +
                    "FROM game_state " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            GameStateModel gameStateModel = getGameStateModel(resultSet);
            gameStateModel.setId(id);
            return gameStateModel;
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, can't read GameState with id = " + id, e);
        }
    }

    @Override
    public List<GameStateModel> getAll() {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT id, current_map, saved_at, player_id " +
                    "FROM game_state";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            List<GameStateModel> gameStateModelList = new ArrayList<>();
            while (resultSet.next()) {
                GameStateModel gameStateModel = getGameStateModel(resultSet);
                gameStateModel.setId(resultSet.getInt(1));
                gameStateModelList.add(gameStateModel);
            }
            return gameStateModelList;
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, cannot read all authors", e);
        }
    }

    private GameStateModel getGameStateModel(ResultSet resultSet) throws SQLException {
        int playerId = resultSet.getInt(4);
        PlayerDaoJdbc playerDaoJdbc = new PlayerDaoJdbc(dataSource);
        PlayerModel playerModel = new PlayerModel(playerDaoJdbc.get(playerId));
        return new GameStateModel(
                resultSet.getString(2),
                resultSet.getDate(3),
                playerModel
        );
    }

    private void populateStatement(GameStateModel state, PreparedStatement statement) throws SQLException {
        statement.setString(1, state.getCurrentMap());
        statement.setDate(2, state.getSavedAt());
        statement.setInt(3, state.getPlayer().getId());
    }
}
