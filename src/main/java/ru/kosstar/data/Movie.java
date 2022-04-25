package ru.kosstar.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

/**
 * Класс, определяющий фильм
 */
public class Movie implements Comparable<Movie>, Serializable {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String owner;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private int productionYear; //Значение поля должно быть больше 1894 и меньше следующего года
    private Country country;
    private MovieGenre genre;
    private Person director; //Поле не может быть null
    private long budget; //Значение поля не может быть меньше 0
    private long fees; //Значение поля не может быть меньше 0
    private MpaaRating mpaaRating;
    private int durationInMinutes; //Значение поля не может быть меньше 1
    private int oscarsCount; //Значение поля не может быть меньше 0
    private final LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    /**
     * @param name              название
     * @param productionYear    год создания
     * @param country           страна производства
     * @param genre             жанр
     * @param director          режиссёр
     * @param budget            бюджет
     * @param fees              сборы
     * @param mpaaRating        MPAA рейтинг
     * @param durationInMinutes продолжительность в минутах
     * @param oscarsCount       количество оскаров
     */
    public Movie(int id, String owner, String name, int productionYear, Country country, MovieGenre genre,
                 Person director, long budget, long fees, MpaaRating mpaaRating,
                 int durationInMinutes, int oscarsCount, LocalDateTime creationDate) {
        this.id = id;
        this.owner = owner;
        setName(name);
        setProductionYear(productionYear);
        setCountry(country);
        setGenre(genre);
        setDirector(director);
        setBudget(budget);
        setFees(fees);
        setMpaaRating(mpaaRating);
        setDurationInMinutes(durationInMinutes);
        setOscarsCount(oscarsCount);
        this.creationDate = creationDate;
    }

    /**
     * @param name     название
     * @param director режиссёр
     */
    public Movie(String name, Person director) {
        setName(name);
        setDirector(director);
        creationDate = LocalDateTime.now();
    }

    public String getOwner() {
        return owner;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод для задания названия фильма
     *
     * @param name название фильма
     */
    public void setName(String name) {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Значение поля не может быть пустым.");
        this.name = name;
    }

    /**
     * Метод для задания года создания фильма
     *
     * @param productionYear год создания фильма
     * @throws IllegalArgumentException если поле принимает недопустимое значение
     */
    public void setProductionYear(int productionYear) throws IllegalArgumentException {
        if (productionYear < 1895 || productionYear > Calendar.getInstance().get(Calendar.YEAR))
            throw new IllegalArgumentException("Значение поля не может быть меньше 1895 или больше текущего года.");
        this.productionYear = productionYear;
    }

    /**
     * Метод для задания страны производства
     *
     * @param country страна производства
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Метод для задания жанра фильма
     *
     * @param genre жанр фильма
     */
    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    /**
     * Метод для задания данных о режиссёре фильма
     *
     * @param director режиссёр фильма
     */
    public void setDirector(Person director) {
        this.director = director;
    }

    /**
     * Метод для задания данных о бюджете фильма
     *
     * @param budget бюджет фильма
     * @throws IllegalArgumentException если поле принимает недопустимое значение
     */
    public void setBudget(long budget) throws IllegalArgumentException {
        if (budget < 0)
            throw new IllegalArgumentException("Значение поля не может быть меньше 0.");
        this.budget = budget;
    }

    /**
     * Метод для задания сборов фильма
     *
     * @param fees сборы фильма
     * @throws IllegalArgumentException если поле принимает недопустимое значение
     */
    public void setFees(long fees) throws IllegalArgumentException {
        if (fees < 0)
            throw new IllegalArgumentException("Значение поля не может быть меньше 0.");
        this.fees = fees;
    }

    /**
     * Метод для задания MPAA рейтинга фильма
     *
     * @param mpaaRating MPAA рейтинг фильма
     */
    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    /**
     * Метод для задания продолжительности фильма в минутах
     *
     * @param durationInMinutes продолжительность фильма в минутах
     * @throws IllegalArgumentException если поле принимает недопустимое значение
     */
    public void setDurationInMinutes(int durationInMinutes) throws IllegalArgumentException {
        if (durationInMinutes <= 0)
            throw new IllegalArgumentException("Значение поля не может быть меньше 1.");
        this.durationInMinutes = durationInMinutes;
    }

    /**
     * Метод для задания количества оскаров фильма
     *
     * @param oscarsCount количество оскаров фильма
     * @throws IllegalArgumentException если поле принимает недопустимое значение
     */
    public void setOscarsCount(int oscarsCount) throws IllegalArgumentException {
        if (oscarsCount < 0)
            throw new IllegalArgumentException("Значение поле не может быть меньше 0.");
        this.oscarsCount = oscarsCount;
    }

    /**
     * Метод ля получения id фильма
     *
     * @return id фильма
     */
    public int getId() {
        return id;
    }

    /**
     * Метод для получения названия фильма
     *
     * @return название фильма
     */
    public String getName() {
        return name;
    }

    /**
     * Метод для получения года создания фильма
     *
     * @return год создания фильма
     */
    public int getProductionYear() {
        return productionYear;
    }

    /**
     * Метод для получения страны производства фильма
     *
     * @return страна производства фильма
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Метод для получения жанра фильма
     *
     * @return жанр фильма
     */
    public MovieGenre getGenre() {
        return genre;
    }

    /**
     * Метод для получения данных о режиссёре фильма
     *
     * @return данные режиссёра фильма
     */
    public Person getDirector() {
        return director;
    }

    /**
     * Метод для получения значения бюджета фильма
     *
     * @return бюджет фильма
     */
    public long getBudget() {
        return budget;
    }

    /**
     * Метод для получения значения сборов фильма
     *
     * @return сборы фильма
     */
    public long getFees() {
        return fees;
    }

    /**
     * Метод для получения MPAA рейтинга фильма
     *
     * @return MPAA рейтинг фильма
     */
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    /**
     * Метод для получения продолжительности фильма в минутах
     *
     * @return продолжительность фильма в минутах
     */
    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    /**
     * Метод для получения количества оскаров фильма
     *
     * @return количество оскаров фильма
     */
    public long getOscarsCount() {
        return oscarsCount;
    }


    /**
     * Метод для получения даты добавления фильма
     *
     * @return дата добавления фильма
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    @Override
    public int compareTo(Movie o) {
        return this.name.compareTo(o.name);
    }

    public void copy(Movie m) {
        this.name = m.name;
        this.productionYear = m.productionYear;
        this.country = m.country;
        this.genre = m.genre;
        this.director = m.director;
        this.budget = m.budget;
        this.fees = m.fees;
        this.mpaaRating = m.mpaaRating;
        this.durationInMinutes = m.durationInMinutes;
        this.oscarsCount = m.oscarsCount;
    }
}
