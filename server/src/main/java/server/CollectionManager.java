package server;


import lib.element.City;
import lib.element.CityAreaComparator;
import lib.element.CityBuilder;
import lib.element.Climate;
import server.db.CollectionDatabaseCommander;
import lib.exceptions.IncorrectCollectionException;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Класс для работы с коллекцией(исполнение команд)
 */
public class CollectionManager {
    /**
     * Дата инициализации
     */
    private final LocalDate initialDate = LocalDate.now();

    /**
     * Сортировщик
     */
    private final CityAreaComparator cityComparator = new CityAreaComparator();

    private ArrayList<City> collection;
    /**
     * Дата последнего сохранения
     */
    private final LocalDate lastSaveDate = LocalDate.now();
    ReentrantLock lock = new ReentrantLock();

    /**
     * Файл, который обрабатывается на данный момент
     */
    public CollectionManager() {
        CollectionDatabaseCommander database;
        database = CollectionDatabaseCommander.getInstance();
        database.createTableIfNotExist();
        updateCollection(database.getData());

    }

    /**
     * Вывод доступных команд
     */
    public String help() {
        return "help: вывести справку по доступным командам\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element}: добавить новый элемент в коллекцию\n" +
                "update id {element}: обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id: удалить элемент из коллекции по его id\n" +
                "clear: очистить коллекцию\n" +
                "execute_script file_name: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit: завершить программу (без сохранения в файл)\n" +
                "remove_greater {element}: удалить из коллекции все элементы, превышающие заданный\n" +
                "history: вывести последние 6 команд (без их аргументов)\n" +
                "remove_all_by_climate climate: удалить из коллекции все элементы, значение поля climate которого эквивалентно заданному\n" +
                "remove_any_by_meters_above_sea_level metersAboveSeaLevel: удалить из коллекции один элемент, значение поля metersAboveSeaLevel которого эквивалентно заданному\n" +
                "print_field_descending_climate: вывести значения поля climate всех элементов в порядке убывания";
    }

    public ArrayList<City> getCollectionArr() {
        return collection;
    }

    /**
     * Вывод информации о коллекции
     */
    public String info() {
        String result = "";
        result += "type: " + collection.getClass().toString() + "\n";
        result += "Initialisation date: " + initialDate + "\n";
        result += "Last save date: " + lastSaveDate + "\n";
        result += "Collection size: " + collection.size();
        return result;
    }

    /**
     * Показать элементы коллекции
     */
    public String show() {
        return collection
                .stream()
                .map(City::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Добавить элемент в коллекцию
     *
     * @param element Добавляемый элемент
     */
    public void add(City element) {
        lock.lock();
        collection.add(element);
        collection.sort(cityComparator);
        lock.unlock();
    }

    /**
     * Обновить элемент коллекции по id
     *
     * @param element Добавляемый элемент
     * @param id      ID добавляемого элемента
     */
    public void update(int id, City element) {
        collection = (ArrayList<City>) collection
                .stream()
                .filter((city -> city.getId() != id))
                .collect(Collectors.toList());
        element=new CityBuilder(element)
                .id(id)
                .build();
        add(element);

    }

    /**
     * Удалить элемент коллекции по id
     *
     * @param id ID удаляемого элемента
     */
    public void removeById(int id) {
        lock.lock();
        collection = (ArrayList<City>) collection
                .stream()
                .filter((city) -> city.getId() != id)
                .collect(Collectors.toList());
        lock.unlock();
    }

    /**
     * Добавить элемент коллекции, если он максимален
     *
     * @param city Добавляемый элемент
     */
    public void addIfMax(City city) {
        if (city.getArea() > collection.get(0).getArea()) {
            add(city);
        }
    }

    /**
     * Удалить элементы большие введённого элемента
     *
     * @param city Введённый элемент
     */
    public void removeGreater(City city) {
        lock.lock();
        collection = (ArrayList<City>) collection
                .stream()
                .filter(element -> !Objects.equals(element.getCreator(), city.getCreator()) || element.getArea() < city.getArea())
                .collect(Collectors.toList());
        lock.unlock();
    }

    /**
     * Удалить всё с таким параметром климата
     *
     * @param climate Введённый климат
     */
    public void removeAllByClimate(Climate climate, String user) {
        lock.lock();
        collection = (ArrayList<City>) collection
                .stream()
                .filter(element -> !Objects.equals(element.getCreator(), user) || element.getClimate() != climate)
                .collect(Collectors.toList());
        lock.unlock();
    }


    /**
     * Очистить коллекцию
     */
    public void clear(String user) {
        lock.lock();
        collection = (ArrayList<City>) collection
                .stream()
                .filter(element -> !Objects.equals(element.getCreator(), user))
                .collect(Collectors.toList());
        lock.unlock();
    }

    /**
     * Вывести поле климата всех элементов коллекции
     */
    public String printFieldDescendingClimate() {
        return collection
                .stream()
                .map((city) -> (city.getClimate() == null) ? "Не задан" : city.getClimate().toString())
                .sorted()
                .collect(Collectors.joining("\n"));
    }

    /**
     * Заменить коллекцию
     *
     * @param collection Замена старой коллекции
     */
    public void updateCollection(Collection<City> collection) {
        this.collection = new ArrayList<>(collection);
        collectionVerification();
    }

    /**
     * Проверить коллекцию на корректность
     */
    private void collectionVerification() {
        Set<City> collectionSet = new HashSet<>(collection);
        if (collectionSet.size() != collection.size()) {
            throw new IncorrectCollectionException("Найдено повторение ID! Коллекция некорректна!");
        }
    }

    public int getIndexById(int id) {
        return collection.indexOf(getElementById(id));
    }

    /**
     * Получить элемент по id
     *
     * @param id Введённый ID
     * @return Элемент по ID
     */
    private City getElementById(int id) {
        return collection.stream().filter(city -> city.getId() == id).findFirst().orElse(null);
    }


}
