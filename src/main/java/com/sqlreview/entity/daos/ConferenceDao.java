package com.sqlreview.entity.daos;

import com.sqlreview.connector.PostgreSqlConnection;
import com.sqlreview.dao.Dao;
import com.sqlreview.entity.Conference;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConferenceDao implements Dao<Conference, Integer> {

    private static final Logger LOGGER = Logger.getLogger(ConferenceDao.class.getName());

    private final  Optional<Connection> connection;

    public ConferenceDao(){
        this.connection = PostgreSqlConnection.getConnection();
    }
    @Override
    public Optional<Integer> save(Conference conference) {
        String message = "The Conference to be added should not be null";
        Conference nonNullConference = Objects.requireNonNull(conference, message);
        String sql = "INSERT INTO conference(confId, name, ranking) VALUES (?,?,?)";

        return connection.flatMap(conn ->{
            Optional<Integer> generatedId = Optional.empty();
            try (PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            )){
                statement.setInt(1,nonNullConference.getConfId());
                statement.setString(2, nonNullConference.getName());
                statement.setInt(3, nonNullConference.getRanking());

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
                        new Object[]{nonNullConference,
                                (numberOfInsertedRows > 0)});
            }catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return generatedId;
        });
    }

    @Override
    public void delete(Conference conference) {

    }

    @Override
    public void truncateTable() {
        String sql = "DELETE FROM conference";
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
            String sql = "select count(1) as isEmpty where exists (select * from conference)";
            Optional<Integer> isEmpty = Optional.empty();
            try (Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                if (resultSet.next()) {
                    int empty = resultSet.getInt("isEmpty");
                    isEmpty = Optional.of(empty);
                    if (isEmpty.get().intValue()==1){
                        LOGGER.log(Level.INFO, "The table is empty? FALSE");
                    }else{
                        LOGGER.log(Level.INFO, "The table is empty? TRUE");
                    }
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return isEmpty;
        });
    }

    @Override
    public Optional<Integer> isDuplicate(Integer a, Integer b) {
        return Optional.empty();
    }
}
