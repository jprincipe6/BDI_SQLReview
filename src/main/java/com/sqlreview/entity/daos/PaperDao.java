package com.sqlreview.entity.daos;

import com.sqlreview.connector.PostgreSqlConnection;
import com.sqlreview.dao.Dao;
import com.sqlreview.entity.Paper;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaperDao implements Dao<Paper, Integer> {

    private static final Logger LOGGER =
            Logger.getLogger(PaperDao.class.getName());

    private final  Optional<Connection> connection;

    public PaperDao (){
        this.connection = PostgreSqlConnection.getConnection();
    }

    @Override
    public Optional<Integer> save(Paper paper) {
        String message = "The Paper to be added should not be null";
        Paper nonNullPaper = Objects.requireNonNull(paper, message);
        String sql = "INSERT INTO paper(paperId, title, abstract) VALUES (?,?,?)";

        return connection.flatMap(conn ->{
            Optional<Integer> generatedId = Optional.empty();
            try (PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            )){
                statement.setInt(1,nonNullPaper.getPaperId());
                statement.setString(2, nonNullPaper.getTitle());
                statement.setString(3, nonNullPaper.getAbStract());

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
                        new Object[]{nonNullPaper,
                                (numberOfInsertedRows > 0)});
            }catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return generatedId;
        });
    }

    @Override
    public void delete(Paper paper) {
        String message = "The Paper to be deleted should not be null";
        Paper nonNullPaper = Objects.requireNonNull(paper, message);
        String sql = "DELETE FROM paper WHERE paperId = ?";
        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullPaper.getPaperId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the paper deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void truncateTable() {
        String sql = "DELETE FROM paper";
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

}
