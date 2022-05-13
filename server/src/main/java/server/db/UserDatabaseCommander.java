package server.db;

import lib.UserData;
import lib.exceptions.AuthorizationException;
import lib.exceptions.MailException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Random;

public class UserDatabaseCommander extends DatabaseCommander {
    private static UserDatabaseCommander instance = null;
    private final Connection connection;
    ;
    private static final String INSERT_VALUE = "INSERT INTO " +
            "users(login,password,salt,email)" +
            "VALUES(?,?,?,?)";
    private static final String SELECT_VALUE = "SELECT COUNT(*) FROM users WHERE login=?";
    private static final String SELECT_ROW = "SELECT * FROM users WHERE login=?";
    private static final String UPDATE_PASSWORD = "UPDATE users SET password=?, salt=? WHERE login=?";


    private UserDatabaseCommander() throws SQLException {
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
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "login VARCHAR(50) PRIMARY KEY," +
                "password VARCHAR(50)," +
                "salt VARCHAR(10)," +
                "email VARCHAR(40)" +
                ")";

        try {
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertElement(UserData userData) {

        if (checkUserData(userData)) {
            throw new AuthorizationException("Данный логин занят!");
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_VALUE);
            MessageDigest messageDigest;
            String salt = generateRandomString();

            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update((userData.getPassword() + salt).getBytes());

            statement.setString(1, userData.getLogin());
            statement.setString(2, toHexBytes(messageDigest.digest()));
            statement.setString(3, salt);
            statement.setString(4, userData.getEmail());
            statement.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean checkUserData(UserData userData) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_VALUE);
            statement.setString(1, userData.getLogin());
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt(1) != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void checkUserPassword(UserData userData) {

        PreparedStatement statement = null;
        MessageDigest messageDigest = null;
        String salt;
        try {
            statement = connection.prepareStatement(SELECT_ROW);

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
            statement = connection.prepareStatement(UPDATE_PASSWORD);
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
            statement = connection.prepareStatement(SELECT_ROW);
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
