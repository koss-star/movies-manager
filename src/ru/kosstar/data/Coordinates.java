package ru.kosstar.data;

public class Coordinates {
    private Long x; //Максимальное значение поля: 438, Поле не может быть null
    private Double y; //Поле не может быть null

    public Coordinates(Long x, Double y) throws IllegalArgumentException {
        setX(x);
        setY(y);
    }

    public void setX(Long x) throws IllegalArgumentException {
        if (x == null || x > 438)
            throw new IllegalArgumentException("Значение поля не может быть пустым или больше 438.");
        this.x = x;
    }

    public void setY(Double y) throws IllegalArgumentException {
        if (y == null)
            throw new IllegalArgumentException("Значение поля не может быть пустым.");
        this.y = y;
    }
}
