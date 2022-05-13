package server.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DatabaseCommander {

    public Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String DB_URL = properties.getProperty("url");
        String USER = properties.getProperty("username");
        String PASS = properties.getProperty("password");
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
    abstract void createTableIfNotExist() throws SQLException;

}
