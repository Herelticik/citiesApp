package lib.element;

import java.util.Comparator;

/**
 * Класс логики сортировки
 */
public class CityAreaComparator implements Comparator<City> {

    /**
     * Переопределение на сортировку по площади
     *
     * @return Сравнение элементов
     */
    @Override
    public int compare(City element1, City element2) {
        return -Integer.compare(element1.getArea(), element2.getArea());
    }
}
