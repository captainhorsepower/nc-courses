package by.courses.vorobey.artem.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLDatabaseManager implements DatabaseManager {

    // I will change it to some kind of loaded properties one day
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DATABASE_NAME = "somejdbc";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "artem";

    private static final PostgreSQLDatabaseManager singletonInstance;

    private Connection connection;

    static {
        PostgreSQLDatabaseManager instance = null;
        try {

            Class.forName("org.postgresql.Driver");

            instance = new PostgreSQLDatabaseManager();

        } catch (ClassNotFoundException exc) {
            exc.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (instance != null) {
                singletonInstance = instance;
            } else {
                singletonInstance = null;
                System.exit(1);
            }
        }

    }

    private PostgreSQLDatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(
                DATABASE_URL + DATABASE_NAME, LOGIN, PASSWORD);
    }

    public static PostgreSQLDatabaseManager getManager() {
        return singletonInstance;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement createStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
