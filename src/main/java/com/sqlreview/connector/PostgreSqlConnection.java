package com.sqlreview.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSqlConnection {
    private static final Logger LOGGER =
            Logger.getLogger(PostgreSqlConnection.class.getName());
    private static Optional<Connection> connection = Optional.empty();

    private static String URL = "jdbc:postgresql://localhost:5432/sqlReview";

    private static String USER = "postgres";

    private static String PASS = "Algebra1990";

    public static Optional<Connection> getConnection(){
        if(connection.isEmpty()){
            try {
                connection = Optional.ofNullable(
                        DriverManager.getConnection(URL, USER, PASS));
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }
}
