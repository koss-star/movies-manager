package ru.kosstar.data;

/**
 * Класс, определяющий режиссёра
 */
public class Person implements Comparable<Person> {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private double growthInMetres; //Значение поля не может быть больше 3.0 или меньше 0
    private Country nationality;
    private int filmCount; //Значение поля не может быть меньше 1
    private String passportID;

    /**
     * @param name           имя
     * @param growthInMetres рост в метрах
     * @param nationality    национальность
     * @param filmCount      количество снятых фильмов
     * @param passportID     паспорт
     */
    public Person(String name, double growthInMetres, Country nationality, int filmCount, String passportID) {
        setName(name);
        setGrowthInMetres(growthInMetres);
        setNationality(nationality);
        setFilmCount(filmCount);
        setPassportID(passportID);
    }

    /**
     * @param name имя
     */
    public Person(String name) {
        setName(name);
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
     * Метод для задания паспорта режиссёра
     *
     * @param passportID паспорт режиссёра
     * @throws IllegalArgumentException если поле принимает недопустимое значение
     */
    public void setPassportID(String passportID) throws IllegalArgumentException {
        if (passportID.length() < 10)
            throw new IllegalArgumentException("Длинна поля не может быть меньше 10.");
        this.passportID = passportID;
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

    /**
     * Метод для получения паспорта режиссёра
     *
     * @return паспорт режиссёра
     */
    public String getPassportID() {
        return passportID;
    }

    @Override
    public String toString() {
        return '{' + "\n" +
                "name: " + name + "\n" +
                "growthInMetres: " + growthInMetres + "\n" +
                "nationality: " + nationality + "\n" +
                "filmCount: " + filmCount + "\n" +
                "passportID: " + passportID + "\n" +
                "}";
    }

    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }
}
