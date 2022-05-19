package server.db;

import lib.UserData;
import lib.exceptions.AuthorizationException;
import lib.exceptions.MailException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class UserDatabaseCommander extends DatabaseCommander {
    private static UserDatabaseCommander instance = null;
    private final Connection connection;
    private final Properties requests = new Properties();

    private UserDatabaseCommander() throws SQLException {
        try {
            loadRequests();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.connection = getConnection();
    }

    private void loadRequests() throws IOException {
        requests.load(getClass().getClassLoader().getResourceAsStream("usersDB-requests.properties"));
    }

    public static UserDatabaseCommander getInstance() {
        if (instance == null) {
            try {
                instance = new UserDatabaseCommander();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    @Override
    public void createTableIfNotExist() {
        try {
            connection.createStatement().execute(requests.getProperty("db.create"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertElement(UserData userData) {
        String salt = generateRandomString();
        try {
            PreparedStatement statement = getStatementByRequest("db.insert");
            statement.setString(1, userData.getLogin());
            statement.setString(2, getHashedPassword(salt, userData.getPassword()));
            statement.setString(3, salt);
            statement.setString(4, userData.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new AuthorizationException("Данный логин занят!");
        }
    }

    private String generateRandomString() {
        byte[] array = new byte[20];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private PreparedStatement getStatementByRequest(String requestName) throws SQLException {
        return connection.prepareStatement(requests.getProperty(requestName));
    }


    private String getHashedPassword(String salt, String password) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update((password + salt).getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return toHexBytes(messageDigest.digest());
    }

    private String toHexBytes(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
        }
        return result.toString();
    }

    public void checkUserPassword(UserData userData) {
        String salt;
        try {
            PreparedStatement statement = getStatementByRequest("db.select_user");
            statement.setString(1, userData.getLogin());
            ResultSet row = statement.executeQuery();
            row.next();
            salt = row.getString(3);
            String hashedPassword = getHashedPassword(salt, userData.getPassword());
            if (!hashedPassword.equals(row.getString(2))) {
                throw new AuthorizationException("Пароль или логин неверен!");
            }
        } catch (SQLException e) {
            throw new AuthorizationException("Пароль или логин неверен!");
        }
    }

    public void updatePassword(UserData userData, String password) {
        try {
            PreparedStatement statement = getStatementByRequest("db.update_password");
            String salt = generateRandomString();
            statement.setString(1, getHashedPassword(salt, password));
            statement.setString(2, salt);
            statement.setString(3, userData.getLogin());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public String getEmail(UserData userData) {
        String email;
        try {
            PreparedStatement statement = getStatementByRequest("db.select_user");
            statement.setString(1, userData.getLogin());
            ResultSet row = statement.executeQuery();
            row.next();
            email = row.getString(4);
        } catch (SQLException e) {
            throw new MailException("Пользователя с таким почтовым адресом не существует!");
        }
        return email;
    }


}
