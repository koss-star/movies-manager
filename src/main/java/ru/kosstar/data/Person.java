package ru.kosstar.data;

import java.io.Serializable;

/**
 * Класс, определяющий режиссёра
 */
public class Person implements Comparable<Person>, Serializable {
    private int id;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private double growthInMetres; //Значение поля не может быть больше 3.0 или меньше 0
    private Country nationality;
    private int filmCount; //Значение поля не может быть меньше 1

    /**
     * @param name           имя
     * @param growthInMetres рост в метрах
     * @param nationality    национальность
     * @param filmCount      количество снятых фильмов
     */
    public Person(int id, String name, double growthInMetres, Country nationality, int filmCount) {
        this.id = id;
        setName(name);
        setGrowthInMetres(growthInMetres);
        setNationality(nationality);
        setFilmCount(filmCount);
    }

    /**
     * @param name имя
     */
    public Person(String name) {
        setName(name);
    }

    public int getId() {
        return id;
    }

    /**
     * Метод для задания имени режиссёра
     *
     * @param name имя режиссёра
     * @throws IllegalArgumentException если поле принимает недопустимое значение
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Значение поля не может быть пустым.");
        this.name = name;
    }

    /**
     * Метод для задания роста режиссёра в метрах
     *
     * @param growthInMetres раст режиссёра в метрах
     * @throws IllegalArgumentException если поле принимает недопустимое значение
     */
    public void setGrowthInMetres(double growthInMetres) throws IllegalArgumentException {
        if (growthInMetres > 3.0 || growthInMetres <= 0)
            throw new IllegalArgumentException("Значение поля не может быть больше 3.0 или меньше 0.");
        this.growthInMetres = growthInMetres;
    }

    /**
     * Метод для задания национальности режиссёра
     *
     * @param nationality национальность режиссёра
     */
    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    /**
     * Метод для задания количества снятых фильмов
     *
     * @param filmCount количество снятых фильмов
     * @throws IllegalArgumentException если поле принимает недопустимое значение
     */
    public void setFilmCount(int filmCount) throws IllegalArgumentException {
        if (filmCount <= 0)
            throw new IllegalArgumentException("Значение поля не может быть меньше 0.");
        this.filmCount = filmCount;
    }

    /**
     * Метод для получения имени режиссёра
     *
     * @return имя режиссёра
     */
    public String getName() {
        return name;
    }

    /**
     * Метод для получения роста режиссёра в метрах
     *
     * @return рост режиссёра в метрах
     */
    public double getGrowthInMetres() {
        return growthInMetres;
    }

    /**
     * Метод для получения национальности режиссёра
     *
     * @return национальность режиссёра
     */
    public Country getNationality() {
        return nationality;
    }

    /**
     * Метод для получения количества снятых фильмов
     *
     * @return количество снятых фильмов
     */
    public int getFilmCount() {
        return filmCount;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", growthInMetres=" + growthInMetres +
                ", nationality=" + nationality +
                ", filmCount=" + filmCount +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }

    public void copy(Person p) {
        if (this.id != p.id)
            return;
        this.name = p.name;
        this.growthInMetres = p.growthInMetres;
        this.nationality = p.nationality;
        this.filmCount = p.filmCount;
    }
}
