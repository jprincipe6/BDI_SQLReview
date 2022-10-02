package com.sqlreview.entity.daos;

import com.sqlreview.connector.PostgreSqlConnection;
import com.sqlreview.dao.Dao;
import com.sqlreview.entity.Author;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorDao implements Dao<Author, Integer> {

    private static final Logger LOGGER = Logger.getLogger(Author.class.getName());

    private final  Optional<Connection> connection;

    public AuthorDao(){
        this.connection = PostgreSqlConnection.getConnection();
    }


    @Override
    public Optional<Integer> save(Author author) {
        String message = "The Author to be added should not be null";
        Author nonNullAuthor = Objects.requireNonNull(author, message);
        String sql = "INSERT INTO author(authorId, name, email, affiliation) VALUES (?,?,?,?)";

        return connection.flatMap(conn ->{
            Optional<Integer> generatedId = Optional.empty();
            try (PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            )){
                statement.setInt(1,nonNullAuthor.getAuthorId());
                statement.setString(2, nonNullAuthor.getName());
                statement.setString(3, nonNullAuthor.getEmail());
                statement.setString(4, nonNullAuthor.getAffiliation());

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
                        new Object[]{nonNullAuthor,
                                (numberOfInsertedRows > 0)});
            }catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return generatedId;
        });
    }

    @Override
    public void delete(Author author) {

    }

    @Override
    public void truncateTable() {
        String sql = "DELETE FROM author";
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
            String sql = "select count(1) as isEmpty where exists (select * from author)";
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
