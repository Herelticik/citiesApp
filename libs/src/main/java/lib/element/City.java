package lib.element;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс города
 */
public class City implements Serializable {
    private final int id;
    private final String name;
    private final Coordinates coordinates;
    private final String creationDate;
    private final int area;
    private final long population;
    private final Long metersAboveSeaLevel;
    private final float agglomeration;
    private final Climate climate;
    private final Government government;
    private final Human governor;
    private final String creator;

    /**
     * Задание параметров объекту
     *
     * @param name                Название
     * @param coordinates         Координаты
     * @param area                Площадь
     * @param population          Численность населения
     * @param metersAboveSeaLevel Уровень над водой
     * @param agglomeration       Аггломерация
     * @param climate             Климат
     * @param government          Тип правления
     * @param governor            Основатель
     */
    public City(int id, String name, Coordinates coordinates, int area, long population, Long metersAboveSeaLevel, float agglomeration, Climate climate, Government government, Human governor, String creationDate, String creator) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        this.climate = climate;
        this.government = government;
        this.governor = governor;
        this.creationDate = creationDate;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Government getGovernment() {
        return government;
    }

    public float getAgglomeration() {
        return agglomeration;
    }

    public long getPopulation() {
        return population;
    }

    public int getId() {
        return id;
    }

    public Long getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }


    public Climate getClimate() {
        return climate;
    }

    public Human getGovernor() {
        return governor;
    }


    public int getArea() {
        return area;
    }

    public String getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate='" + creationDate + '\'' +
                ", area=" + area +
                ", population=" + population +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                ", agglomeration=" + agglomeration +
                ", climate=" + climate +
                ", government=" + government +
                ", governor=" + governor +
                ", creator='" + creator + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
