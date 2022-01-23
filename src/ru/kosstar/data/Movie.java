package ru.kosstar.data;

import java.time.LocalDateTime;
import java.util.Calendar;

public class Movie {
    private static int nextId = 1;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private int productionYear; //Значение поля должно быть больше 1894 и меньше 2023
    private Country country;
    private MovieGenre genre;
    private Person operator; //Поле не может быть null
    private long budget; //Значение поля не может быть меньше 0
    private long fees; //Значение поля не может быть меньше 0
    private MpaaRating mpaaRating;
    private int durationInMinutes; //Значение поля не может быть меньше 1
    private int oscarsCount; //Значение поля не может быть меньше 0
    private final java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    public Movie(String name, int productionYear, Country country, MovieGenre genre, Person operator,
                 long budget, long fees, MpaaRating mpaaRating, int durationInMinutes, int oscarsCount) {
        setId();
        setName(name);
        setProductionYear(productionYear);
        setCountry(country);
        setGenre(genre);
        setOperator(operator);
        setBudget(budget);
        setFees(fees);
        setMpaaRating(mpaaRating);
        setDurationInMinutes(durationInMinutes);
        setOscarsCount(oscarsCount);
        creationDate = LocalDateTime.now();
    }

    public Movie(String name, Person operator){
        setId();
        setName(name);
        setOperator(operator);
        creationDate = LocalDateTime.now();
    }

    private void setId() {
        this.id = Movie.nextId;
        Movie.nextId++;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Значение поля не может быть пустым.");
        this.name = name;
    }

    public void setProductionYear(int productionYear) throws IllegalArgumentException {
        if (productionYear < 1895 || productionYear > Calendar.getInstance().get(Calendar.YEAR))
            throw new IllegalArgumentException("Значение поля не может быть меньше 1895 или больше текущего года.");
        this.productionYear = productionYear;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public void setOperator(Person operator) throws IllegalArgumentException {
        this.operator = operator;
    }

    public void setBudget(long budget) throws IllegalArgumentException{
        if (budget < 0)
            throw new IllegalArgumentException("Значение поля не может быть меньше 0.");
        this.budget = budget;
    }

    public void setFees(long fees) throws IllegalArgumentException{
        if (fees < 0)
            throw new IllegalArgumentException("Значение поля не может быть меньше 0.");
        this.fees = fees;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public void setDurationInMinutes(int durationInMinutes) throws IllegalArgumentException {
        if (durationInMinutes <= 0)
            throw new IllegalArgumentException("Значение поля не может быть меньше 1.");
        this.durationInMinutes = durationInMinutes;
    }

    public void setOscarsCount(int oscarsCount) throws IllegalArgumentException {
        if (oscarsCount < 0)
            throw new IllegalArgumentException("Значение поле не может быть меньше 0.");
        this.oscarsCount = oscarsCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public Country getCountry() {
        return country;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public Person getOperator() {
        return operator;
    }

    public long getBudget() {
        return budget;
    }

    public long getFees() {
        return fees;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public long getOscarsCount() {
        return oscarsCount;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productionYear=" + productionYear +
                ", country=" + country +
                ", genre=" + genre +
                ", operator=" + operator +
                ", budget=" + budget +
                ", fees=" + fees +
                ", mpaaRating=" + mpaaRating +
                ", durationInMinutes=" + durationInMinutes +
                ", oscarsCount=" + oscarsCount +
                ", creationDate=" + creationDate +
                "}\n";
    }
}
