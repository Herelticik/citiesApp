package lib.element;

import java.time.LocalDate;

public class CityBuilder {
    private int id;
    private String name;
    private Coordinates coordinates;
    private String creationDate;
    private int area;
    private long population;
    private Long metersAboveSeaLevel;
    private float agglomeration;
    private Climate climate;
    private Government government;
    private Human governor;
    private String creator;

    public CityBuilder() {
    }

    public CityBuilder(City city) {
        this.id = city.getId();
        this.name = city.getName();
        this.coordinates = city.getCoordinates();
        this.area = city.getArea();
        this.population = city.getPopulation();
        this.metersAboveSeaLevel = city.getMetersAboveSeaLevel();
        this.agglomeration = city.getAgglomeration();
        this.climate = city.getClimate();
        this.government = city.getGovernment();
        this.governor = city.getGovernor();
        this.creationDate = city.getCreationDate();
        this.creator = city.getCreator();
    }

    public CityBuilder id(int id) {
        this.id = id;
        return this;
    }

    public CityBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CityBuilder coordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public CityBuilder coordinates(float x, double y) {
        this.coordinates = new Coordinates(x, y);
        return this;
    }

    public CityBuilder creationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public CityBuilder area(int area) {
        this.area = area;
        return this;
    }

    public CityBuilder population(long population) {
        this.population = population;
        return this;
    }

    public CityBuilder metersAboveSeaLevel(Long metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        return this;
    }

    public CityBuilder agglomeration(float agglomeration) {
        this.agglomeration = agglomeration;
        return this;
    }

    public CityBuilder climate(Climate climate) {
        this.climate = climate;
        return this;
    }

    public CityBuilder climate(String climate) {
        try {
            this.climate = Climate.valueOf(climate);
        } catch (NullPointerException e) {
            this.climate = null;
        }
        return this;
    }

    public CityBuilder government(Government government) {
        this.government = government;
        return this;
    }

    public CityBuilder government(String government) {
        try {
            this.government = Government.valueOf(government);
        } catch (NullPointerException e) {
            this.government = null;
        }
        return this;
    }

    public CityBuilder governor(Human governor) {
        this.governor = governor;
        return this;
    }

    public CityBuilder governor(int governorAge) {
        this.governor = new Human(governorAge);
        return this;
    }

    public CityBuilder creator(String creator) {
        this.creator = creator;
        return this;
    }

    public City build() {
        if (creationDate == null) {
            creationDate = LocalDate.now().toString();
        }
        if (name == null || coordinates == null) {
            throw new NullPointerException();
        }
        return new City(id, name, coordinates, area, population, metersAboveSeaLevel, agglomeration, climate, government, governor, creationDate, creator);
    }


}
