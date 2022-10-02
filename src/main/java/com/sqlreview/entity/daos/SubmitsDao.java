package com.sqlreview.entity.daos;

import com.sqlreview.connector.PostgreSqlConnection;
import com.sqlreview.dao.Dao;
import com.sqlreview.entity.Submits;
import com.sqlreview.entity.Writes;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubmitsDao implements Dao<Submits, Integer> {

    private static final Logger LOGGER =
            Logger.getLogger(SubmitsDao.class.getName());

    private final  Optional<Connection> connection;

    public SubmitsDao (){
        this.connection = PostgreSqlConnection.getConnection();
    }

    @Override
    public Optional<Integer> save(Submits submits) {
        String message = "The Conference to be added should not be null";
        Submits nonNullSubmits = Objects.requireNonNull(submits, message);
        String sql = "INSERT INTO submits(paperId, confId, isAccepted, date_s) VALUES (?,?,?,?)";

        return connection.flatMap(conn ->{
            Optional<Integer> generatedId = Optional.empty();
            try (PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            )){
                statement.setInt(1,nonNullSubmits.getPaperId());
                statement.setInt(2, nonNullSubmits.getConfId());
                statement.setBoolean(3,nonNullSubmits.getAccepted());
                statement.setDate(4, nonNullSubmits.getDate());

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
                        new Object[]{nonNullSubmits,
                                (numberOfInsertedRows > 0)});
            }catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return generatedId;
        });
    }

    @Override
    public void delete(Submits submits) {

    }

    @Override
    public void truncateTable() {
        String sql = "DELETE FROM submits";
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
            String sql = "select count(1) as isEmpty where exists (select * from submits)";
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

    @Override
    public Optional<Integer> isDuplicate(Integer a, Integer b) {
        return  connection.flatMap(conn -> {
            String sql = "select count(1) as isDuplicate where exists " +
                    "(select * from submits where paperId=" +a+" and confId="+b+");";
            Optional<Integer> isDuplicate = Optional.empty();
            try (Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);

                if (resultSet.next()) {
                    int empty = resultSet.getInt("isDuplicate");
                    isDuplicate = Optional.of(empty);
                    LOGGER.log(Level.INFO, "It's duplicate? {0}", empty);
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return isDuplicate;
        });
    }
}
