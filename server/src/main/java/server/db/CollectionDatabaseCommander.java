package server.db;


import lib.element.*;
import lib.exceptions.DatabaseElementException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CollectionDatabaseCommander extends DatabaseCommander {
    private static CollectionDatabaseCommander instance = null;
    private final Connection connection;
    private static final String INSERT_VALUE = "INSERT INTO " +
            "cities(name,x,y,area,population,metersAboveSeaLevel,creationDate,agglomeration,climate,government,governorAge,creator)" +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String DELETE_BY_ID = "DELETE FROM cities WHERE id=?";
    private static final String DELETE_BY_CLIMATE = "DELETE FROM cities WHERE climate=? and creator=?";
    private static final String CLEAR = "DELETE FROM cities WHERE creator=?";
    private static final String REMOVE_GREATER = "DELETE FROM cities WHERE area>? and creator=?";
    private static final String UPDATE_ELEMENT = "UPDATE cities SET " +
            "name=?,x=?,y=?,area=?,population=?,metersAboveSeaLevel=?,agglomeration=?,climate=?,government=?,governorAge=? " +
            "WHERE id=?";
    private static final String SELECT_ELEMENTS = "SELECT * FROM cities";

    private static final String GET_ELEMENT_BY_ID = "SELECT * FROM cities WHERE id=?";
    private static final String GET_MAX_AREA = "SELECT MAX(area) FROM cities";

    private static final String SELECT_ANY_BY_SEA_LEVEL = "SELECT id FROM cities WHERE metersAboveSeaLevel=? AND creator=? LIMIT 1";

    private CollectionDatabaseCommander() throws SQLException {
        this.connection = getConnection();
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
        String sql = "CREATE TABLE IF NOT EXISTS cities(" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(50)," +
                "x FLOAT," +
                "y DOUBLE PRECISION ," +
                "area INT," +
                "population BIGINT," +
                "metersAboveSeaLevel BIGINT," +
                "creationDate VARCHAR(12)," +
                "agglomeration FLOAT," +
                "climate VARCHAR(10) NULL," +
                "government VARCHAR(10) NULL," +
                "governorAge INTEGER NULL," +
                "creator VARCHAR(50)" +
                ")";

        try {
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public City insertElement(City city) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_VALUE, Statement.RETURN_GENERATED_KEYS);
            city.setCreationDate(LocalDate.now().toString());
            statement.setString(1, city.getName());
            statement.setFloat(2, city.getCoordinates().getX());
            statement.setDouble(3, city.getCoordinates().getY());
            statement.setInt(4, city.getArea());
            statement.setLong(5, city.getPopulation());
            statement.setLong(6, city.getMetersAboveSeaLevel());
            statement.setString(7, city.getCreationDate());
            statement.setFloat(8, city.getAgglomeration());
            try {
                statement.setString(9, city.getClimate().toString());
            } catch (NullPointerException e) {
                statement.setString(9, null);
            }
            try {
                statement.setString(10, city.getGovernment().toString());
            } catch (NullPointerException e) {
                statement.setString(10, null);
            }
            try {
                statement.setLong(11, city.getGovernor().getAge());
            } catch (NullPointerException e) {
                statement.setLong(11, 0);
            }
            statement.setString(12, city.getCreator());
            statement.executeUpdate();
            ResultSet newData = statement.getGeneratedKeys();
            while (newData.next()) {
                city.setId(newData.getInt(1));
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return city;

    }

    public void updateElement(int id, City city) {

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_ELEMENT);
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
            statement.setInt(11, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseElementException("Такого id не существует!");
        }


    }

    public ArrayList<City> getData() {
        ArrayList<City> data = new ArrayList<City>();

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SELECT_ELEMENTS);
            while (set.next()) {
                int id = set.getInt(1);
                String name = set.getString(2);
                Coordinates coordinates = new Coordinates(set.getFloat(3), set.getDouble(4));
                int area = set.getInt(5);
                long population = set.getLong(6);
                long metersAboveSeaLevel = set.getLong(7);
                String creationDate = set.getString(8);
                float agglomeration = set.getFloat(9);
                Climate climate;
                Government government;
                Human governor;
                try {
                    climate = Climate.valueOf(set.getString(10));
                } catch (NullPointerException e) {
                    climate = null;
                }
                try {
                    government = Government.valueOf(set.getString(11));
                } catch (NullPointerException e) {
                    government = null;
                }
                try {
                    governor = new Human(set.getInt(12));
                } catch (NullPointerException e) {
                    governor = null;
                }
                String creator = set.getString(13);
                data.add(new City(id, name, coordinates, area, population, metersAboveSeaLevel, agglomeration, climate, government, governor, creationDate, creator));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return data;
    }


    public void removeById(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void removeByClimate(Climate climate, String user) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_BY_CLIMATE);
            statement.setString(1, climate.toString());
            statement.setString(2, user);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void clear(String user) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CLEAR);
            statement.setString(1, user);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeGreater(int area, String user) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(REMOVE_GREATER);
            statement.setInt(1, area);
            statement.setString(2, user);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getMaxArea() {
        int area;
        try {
            PreparedStatement statement = connection.prepareStatement(GET_MAX_AREA);
            ResultSet set = statement.executeQuery();
            set.next();
            area = set.getInt(1);
        } catch (SQLException e) {
            area = 0;
        }
        return area;
    }


    public void checkElementAccess(int id, String user) {
        String creator = getCreatorById(id);
        if (!user.equals(creator)) {
            throw new DatabaseElementException("Этот элемент Вам не принаделжит!");
        }
    }

    private String getCreatorById(int id) {
        String creator;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(GET_ELEMENT_BY_ID);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            set.next();
            creator = set.getString(13);
        } catch (SQLException e) {
            throw new DatabaseElementException("Такого id не существует!");
        }
        return creator;
    }

    public int removeAnyBySeaLevel(long seaLevel, String user) {
        int id;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_ANY_BY_SEA_LEVEL);
            statement.setLong(1, seaLevel);
            statement.setString(2, user);
            ResultSet set = statement.executeQuery();
            set.next();
            id = set.getInt(1);
            removeById(id);
        } catch (SQLException e) {
            throw new DatabaseElementException("Такого значения над водой у элемента, принадлежащего Вам, не существует!");
        }
        return id;
    }

}
