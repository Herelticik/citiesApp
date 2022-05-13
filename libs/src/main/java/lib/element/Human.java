package lib.element;

import java.io.Serializable;

/**
 * Класс человека
 */
public class Human implements Serializable {
    /**
     * Возраст человека
     */
    private long age; //Значение поля должно быть больше 0

    /**
     * Установим возраст
     *
     * @param age Возраст человека
     */
    public Human(long age) {
        this.age = age;
    }

    public long getAge() {
        return age;
    }

    /**
     * Вывод информации о человеке
     */
    @Override
    public String toString() {
        return "Human{" +
                "age=" + age +
                '}';
    }
}
