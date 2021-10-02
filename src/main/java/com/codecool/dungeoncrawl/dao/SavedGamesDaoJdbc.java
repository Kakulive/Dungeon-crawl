package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.model.SavedGameModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SavedGamesDaoJdbc implements SavedGamesDao{
    private DataSource dataSource;

    public SavedGamesDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(SavedGameModel savedGameModel) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO saved_games (game_state_id, save_name) " +
                    "VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,savedGameModel.getGameStateId());
            statement.setString(2,savedGameModel.getSaveName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            savedGameModel.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, can't add new save " + "'" + savedGameModel.getSaveName() + "'", e);
        }
    }

    @Override
    public void update(SavedGameModel savedGameModel) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE saved_games " +
                    "SET " +
                    "game_state_id = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, savedGameModel.getGameStateId());
            statement.setInt(2, savedGameModel.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, can't update save ", e);
        }
    }

    @Override
    public SavedGameModel get(String saveName) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT id, game_state_id, save_name  " +
                    "FROM saved_games " +
                    "WHERE save_name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, saveName);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            } else {
                SavedGameModel savedGameModel = getSaveGameModel(resultSet);
                savedGameModel.setId(resultSet.getInt(1));
                return savedGameModel;
            }
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, can't read save with name = " + saveName, e);
        }
    }

    @Override
    public List<SavedGameModel> getAll() {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT id, game_state_id, save_name " +
                    "FROM saved_games";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            List<SavedGameModel> savedGameModels = new ArrayList<>();
            while (resultSet.next()) {
                SavedGameModel savedGameModel = getSaveGameModel(resultSet);
                savedGameModel.setId(resultSet.getInt(1));
                savedGameModels.add(savedGameModel);
            }
            return savedGameModels;
        } catch (SQLException e) {
            // TODO Flash message? Make it in another window or in the same?
            throw new RuntimeException("Error, cannot read all authors", e);
        }
    }

    private SavedGameModel getSaveGameModel(ResultSet resultSet) throws SQLException {
        return new SavedGameModel(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getString(3)
        );
    }
}
