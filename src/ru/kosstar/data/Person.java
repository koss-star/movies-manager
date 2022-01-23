package ru.kosstar.data;

import java.util.Comparator;

public class Person implements Comparable<Person> {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private double growthInMetres; //Значение поля не может быть больше 3.0 или меньше 1
    private Country nationality;
    private int filmCount; //Значение поля не может быть меньше 1
    private Color eyeColor;
    private Color hairColor;

    public Person(String name, double growthInMetres, Country nationality, int filmCount,
                  Color eyeColor, Color hairColor) {
        setName(name);
        setGrowthInMetres(growthInMetres);
        setNationality(nationality);
        setFilmCount(filmCount);
        setEyeColor(eyeColor);
        setHairColor(hairColor);
    }

    public Person(String name){
        setName(name);
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Значение поля не может быть пустым.");
        this.name = name;
    }

    public void setGrowthInMetres(double growthInMetres) throws IllegalArgumentException {
        if (growthInMetres > 3.0 || growthInMetres <= 0)
            throw new IllegalArgumentException("Значение поля не может быть больше 3.0 или меньше 1.");
        this.growthInMetres = growthInMetres;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public void setFilmCount(int filmCount) throws IllegalArgumentException {
        if (filmCount <= 0)
            throw new IllegalArgumentException("Значение поля не может быть меньше 0.");
        this.filmCount = filmCount;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public String getName() {
        return name;
    }

    public double getGrowthInMetres() {
        return growthInMetres;
    }

    public Country getNationality() {
        return nationality;
    }

    public int getFilmCount() {
        return filmCount;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }
}
