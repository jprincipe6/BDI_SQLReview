package com.sqlreview.entity.daos;

import com.sqlreview.connector.PostgreSqlConnection;
import com.sqlreview.dao.Dao;
import com.sqlreview.entity.Writes;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WritesDao implements Dao<Writes, Integer> {

    private static final Logger LOGGER = Logger.getLogger(WritesDao.class.getName());

    private final  Optional<Connection> connection;

    public WritesDao(){
        this.connection = PostgreSqlConnection.getConnection();
    }
    @Override
    public Optional<Integer> save(Writes writes) {
        String message = "The Conference to be added should not be null";
        Writes nonNullWrites = Objects.requireNonNull(writes, message);
        String sql = "INSERT INTO writes(authorId, paperId) VALUES (?,?)";

        return connection.flatMap(conn ->{
            Optional<Integer> generatedId = Optional.empty();
            try (PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            )){
                statement.setInt(1,nonNullWrites.getAuthorId());
                statement.setInt(2, nonNullWrites.getPaperId());

                int numberOfInsertedRows = statement.executeUpdate();
                // Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }
                LOGGER.log(
                        Level.INFO,
                        "{0} created successfully? {1}",
                        new Object[]{nonNullWrites,
                                (numberOfInsertedRows > 0)});
            }catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return generatedId;
        });
    }

    @Override
    public void delete(Writes writes) {

    }

    @Override
    public void truncateTable() {
        String sql = "DELETE FROM writes";
        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                int numberOfDeletedRows = statement.executeUpdate();
                LOGGER.log(Level.INFO, "Was the all Table deleted successfully? {0}",
                        numberOfDeletedRows > 0);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public Optional<Integer> isEmptyTable() {
        return  connection.flatMap(conn -> {
            String sql = "select count(1) as isEmpty where exists (select * from writes)";
            Optional<Integer> isEmpty = Optional.empty();
            try (Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                if (resultSet.next()) {
                    int empty = resultSet.getInt("isEmpty");
                    isEmpty = Optional.of(empty);
                    LOGGER.log(Level.INFO, "The table is empty? {0}", empty);
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return isEmpty;
        });
    }
}
