package lib.element;


import java.io.Serializable;

/**
 * Класс координат
 */
public class Coordinates implements Serializable {
    /**
     * Положение по оси X
     */
    private float x; //Максимальное значение поля: 161
    /**
     * Положение по оси Y
     */
    private double y; //Значение поля должно быть больше -767
    /**
     * Верхняя граница X
     */
    public final static int MAX_X = 161;
    /**
     * Нижняя граница Y
     */
    public final static int SUPREMUM_Y = -767;

    /**
     * Ввод координат
     *
     * @param x Положение по X
     * @param y Положение по Y
     */
    public Coordinates(float x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean isValid(){
        return x <= Coordinates.MAX_X && y > Coordinates.SUPREMUM_Y;
    }

    public float getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Вывод в строку
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
