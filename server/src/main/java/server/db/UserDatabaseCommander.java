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
    private final Properties requests=new Properties();

    private UserDatabaseCommander() throws SQLException {
        try {
            requests.load(getClass().getClassLoader().getResourceAsStream("usersDB-requests.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.connection = getConnection();
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
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(requests.getProperty("db.insert"));
            MessageDigest messageDigest;
            String salt = generateRandomString();
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update((userData.getPassword() + salt).getBytes());
            statement.setString(1, userData.getLogin());
            statement.setString(2, toHexBytes(messageDigest.digest()));
            statement.setString(3, salt);
            statement.setString(4, userData.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new AuthorizationException("Данный логин занят!");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

    public void checkUserPassword(UserData userData) {
        PreparedStatement statement = null;
        MessageDigest messageDigest = null;
        String salt;
        try {
            statement = connection.prepareStatement(requests.getProperty("db.select_user"));
            statement.setString(1, userData.getLogin());
            ResultSet result = statement.executeQuery();
            result.next();
            salt = result.getString(3);
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update((userData.getPassword() + salt).getBytes());
            if (!toHexBytes(messageDigest.digest()).equals(result.getString(2))) {
                throw new AuthorizationException("Пароль или логин неверен!");
            }
        } catch (SQLException e) {
            throw new AuthorizationException("Пароль или логин неверен!");
        } catch (NoSuchAlgorithmException ignored) {
        }
    }

    public void updatePassword(UserData userData, String password) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(requests.getProperty("db.update_password"));
            MessageDigest messageDigest;
            String salt = generateRandomString();
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update((password + salt).getBytes());
            statement.setString(1, toHexBytes(messageDigest.digest()));
            statement.setString(2, salt);
            statement.setString(3, userData.getLogin());
            statement.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public String getEmail(UserData userData) {
        PreparedStatement statement = null;
        String email;
        try {
            statement = connection.prepareStatement(requests.getProperty("db.select_user"));
            statement.setString(1, userData.getLogin());
            ResultSet set = statement.executeQuery();
            set.next();
            email = set.getString(4);
        } catch (SQLException e) {
            throw new MailException("Пользователя с таким почтовым адресом не существует!");
        }
        return email;
    }

    private String generateRandomString() {
        byte[] array = new byte[10];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private String toHexBytes(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
        }
        return result.toString();
    }


}
