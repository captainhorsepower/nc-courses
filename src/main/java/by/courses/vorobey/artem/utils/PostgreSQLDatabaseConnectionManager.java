package by.courses.vorobey.artem.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLDatabaseConnectionManager implements DatabaseConnectionManager {

    // I will change it to some kind of loaded properties one day
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DATABASE_NAME = "somejdbc";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "artem";

    private static final PostgreSQLDatabaseConnectionManager singletonInstance;

    static {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException exc) {
            System.out.println("failed to load Driver, aborting...");
            exc.printStackTrace();
            System.exit(1);
        }
        singletonInstance = new PostgreSQLDatabaseConnectionManager();
    }

    private PostgreSQLDatabaseConnectionManager() { }



    public static PostgreSQLDatabaseConnectionManager getManager() {
        return singletonInstance;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    DATABASE_URL + DATABASE_NAME, LOGIN, PASSWORD);
        } catch (SQLException e) {
            System.out.println("failed to connect");
            e.printStackTrace();
        }

        return connection;
    }
}
