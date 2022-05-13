package lib.element;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс города
 */
public class City implements Serializable {
    /**
     * ID объекта
     */
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /**
     * Название города
     */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * Координаты города
     */
    private Coordinates coordinates; //Поле не может быть null
    /**
     * Дата добавления в коллекцию
     */
    private String creationDate;
    /**
     * Площадь
     */
    private int area; //Значение поля должно быть больше 0
    /**
     * Численность населения
     */
    private long population; //Значение поля должно быть больше 0
    /**
     * Уровень над водой
     */
    private Long metersAboveSeaLevel;
    /**
     * Аггломерация
     */
    private float agglomeration;
    /**
     * Климат
     */
    private Climate climate; //Поле может быть null
    /**
     * Тип правления
     */
    private Government government; //Поле может быть null
    /**
     * Правитель
     */
    private Human governor; //Поле может быть null
    /**
     * Последний ID
     */

    private String creator;
    private static int lastCityId = 0;

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
    public City(String name, Coordinates coordinates, int area, long population, Long metersAboveSeaLevel, float agglomeration, Climate climate, Government government, Human governor) {
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        this.climate = climate;
        this.government = government;
        this.governor = governor;
    }


    public City(int id, String name, Coordinates coordinates, int area, long population, Long metersAboveSeaLevel, float agglomeration, Climate climate, Government government, Human governor, String creationDate) {
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
    }

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

    /**
     * Установить номер последнего ID
     *
     * @param lastCityId Последнее ID
     */
    public static void setLastCityId(int lastCityId) {
        City.lastCityId = lastCityId;
    }

    public static int getLastCityId() {
        return lastCityId++;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Задать id объекту
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Получить id объекта
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Получить уровень над водой
     *
     * @return Уровень над водой
     */
    public Long getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    /**
     * Получить тип климата
     *
     * @return тип климата
     */
    public Climate getClimate() {
        return climate;
    }

    public Human getGovernor() {
        return governor;
    }

    /**
     * Получить площадь
     *
     * @return Площадь
     */
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

    /**
     * Переопределение вывода класса
     *
     * @return Строковое представление класса
     */


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

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
