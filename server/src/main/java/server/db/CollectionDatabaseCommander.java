package server.db;


import lib.element.*;
import lib.exceptions.DatabaseElementException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class CollectionDatabaseCommander extends DatabaseCommander {
    private static CollectionDatabaseCommander instance;
    private final Connection connection;
    private final Properties requests = new Properties();

    private CollectionDatabaseCommander() throws SQLException {
        try {
            loadRequests();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.connection = getConnection();
    }

    private void loadRequests() throws IOException {
        requests.load(getClass().getClassLoader().getResourceAsStream("cityDB-requests.properties"));
    }

    public static CollectionDatabaseCommander getInstance() {
        if (instance == null) {
            try {
                instance = new CollectionDatabaseCommander();
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

    public City insertElement(City city) {
        int id;
        try {
            PreparedStatement statement = getStatementWithKeysReturn("db.insert");
            setImmutableFields(statement, city);
            statement.executeUpdate();
            ResultSet insertedRow = statement.getGeneratedKeys();
            id = getCityId(insertedRow);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new CityBuilder(city)
                .id(id)
                .build();
    }

    private PreparedStatement getStatementWithKeysReturn(String requestName) throws SQLException {
        return connection.prepareStatement(requests.getProperty(requestName), Statement.RETURN_GENERATED_KEYS);
    }

    private void setImmutableFields(PreparedStatement statement, City city) throws SQLException {
        statement.setString(1, city.getName());
        statement.setFloat(2, city.getCoordinates().getX());
        statement.setDouble(3, city.getCoordinates().getY());
        statement.setInt(4, city.getArea());
        statement.setLong(5, city.getPopulation());
        statement.setLong(6, city.getMetersAboveSeaLevel());
        statement.setFloat(7, city.getAgglomeration());
        try {
            statement.setString(8, city.getClimate().toString());
        } catch (NullPointerException e) {
            statement.setString(8, null);
        }
        try {
            statement.setString(9, city.getGovernment().toString());
        } catch (NullPointerException e) {
            statement.setString(9, null);
        }
        try {
            statement.setLong(10, city.getGovernor().getAge());
        } catch (NullPointerException e) {
            statement.setLong(10, 0);
        }
        statement.setString(11, city.getCreationDate());
        statement.setString(12, city.getCreator());
    }

    private int getCityId(ResultSet row) throws SQLException {
        row.next();
        return row.getInt(1);
    }


    public void updateElement(int id, City city) {
        try {
            PreparedStatement statement = getStatementByRequest("db.update_element");
            setImmutableFields(statement, city);
            statement.setInt(12, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseElementException("Такого id не существует или элемент Вам не принадлежит!");
        }
    }

    private PreparedStatement getStatementByRequest(String requestName) throws SQLException {
        return connection.prepareStatement(requests.getProperty(requestName));
    }

    public ArrayList<City> getData() {
        ArrayList<City> data = new ArrayList<City>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rows = statement.executeQuery(requests.getProperty("db.select"));
            while (rows.next()) {
                data.add(parseAndGetCity(rows));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    private City parseAndGetCity(ResultSet row) throws SQLException {
        CityBuilder cityBuilder = new CityBuilder();
        return cityBuilder
                .id(row.getInt(1))
                .name(row.getString(2))
                .coordinates(row.getFloat(3), row.getDouble(4))
                .area(row.getInt(5))
                .population(row.getLong(6))
                .metersAboveSeaLevel(row.getLong(7))
                .creationDate(row.getString(8))
                .agglomeration(row.getFloat(9))
                .climate(row.getString(10))
                .government(row.getString(11))
                .governor(row.getInt(12))
                .creator(row.getString(13))
                .build();
    }

    public void removeById(int id) {
        PreparedStatement statement = null;
        try {
            statement = getStatementByRequest("db.delete_by_id");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeByClimate(Climate climate, String creator) {
        try {
            PreparedStatement statement = getStatementByRequest("db.delete_by_climate");
            statement.setString(1, climate.toString());
            statement.setString(2, creator);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void clear(String creator) {
        try {
            PreparedStatement statement = getStatementByRequest("db.clear");
            statement.setString(1, creator);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeGreater(int area, String creator) {
        try {
            PreparedStatement statement = getStatementByRequest("db.remove_greater");
            statement.setInt(1, area);
            statement.setString(2, creator);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getMaxArea() {
        int area;
        try {
            PreparedStatement statement = getStatementByRequest("db.get_max_area");
            ResultSet rows = statement.executeQuery();
            rows.next();
            area = rows.getInt(1);
        } catch (SQLException e) {
            area = 0;
        }
        return area;
    }

    public int removeAnyBySeaLevel(long seaLevel, String creator) {
        int id = 0;
        try {
            PreparedStatement statement = getStatementByRequest("db.select_any_by_sea_level");
            statement.setLong(1, seaLevel);
            statement.setString(2, creator);
            ResultSet row = statement.executeQuery();
            removeById(getCityId(row));
        } catch (SQLException e) {
            throw new DatabaseElementException("Такого значения над водой у элемента, принадлежащего Вам, не существует!");
        }
        return id;
    }


}
