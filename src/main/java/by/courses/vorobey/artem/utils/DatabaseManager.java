package by.courses.vorobey.artem.utils;

import java.sql.Connection;
import java.sql.Statement;

public interface DatabaseManager {

    Connection getConnection();
    void closeConnection(Connection connection);

}
