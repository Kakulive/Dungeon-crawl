package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameStateModel;

import javax.sql.DataSource;
import java.sql.*;
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
    public void update(GameStateModel state) {

    }

    @Override
    public GameStateModel get(int id) {
        return null;
    }

    @Override
    public List<GameStateModel> getAll() {
        return null;
    }

    private void populateStatement(GameStateModel state, PreparedStatement statement) throws SQLException {
        statement.setString(1, state.getCurrentMap());
        statement.setDate(2, state.getSavedAt());
        statement.setInt(3, state.getPlayer().getId());


    }
}
