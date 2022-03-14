package ru.kosstar.data;

/**
 * Класс, определяющий координаты
 */
public class Coordinates {
    private Double x; //Значение поля должно быть больше -650. Поле не может быть null
    private Float y; //Поле не может быть null

    /**
     * @param x значение координаты x
     * @param y значение координаты y
     * @throws IllegalArgumentException если поле принимает недопустимые значения
     */
    public Coordinates(Double x, Float y) throws IllegalArgumentException {
        setX(x);
        setY(y);
    }

    /**
     * Метод для задания координаты x
     *
     * @param x значение координаты x
     * @throws IllegalArgumentException если поле принимает недопустимые значения
     */
    public void setX(Double x) throws IllegalArgumentException {
        if (x == null || x < -649)
            throw new IllegalArgumentException("Значение поля не может быть меньше -651.");
        this.x = x;
    }

    /**
     * Метод для задания координаты y
     *
     * @param y значение координаты y
     * @throws IllegalArgumentException если поле принимает недопустимые значения
     */
    public void setY(Float y) throws IllegalArgumentException {
        if (y == null)
            throw new IllegalArgumentException("Значение поля не может быть пустым.");
        this.y = y;
    }

    /**
     * Метод для получения значения координаты x
     *
     * @return значение координаты x
     */
    public Double getX() {
        return x;
    }

    /**
     * Метод для получения значения координаты y
     *
     * @return значение координаты y
     */
    public Float getY() {
        return y;
    }

    @Override
    public String toString() {
        return '{' + "\n" +
                "x: " + x + "\n" +
                "y: " + y + "\n" +
                '}';
    }
}
