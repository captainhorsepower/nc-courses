package by.courses.vorobey.artem.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface DatabaseConnectionManager {

    Connection getConnection();

    default void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
