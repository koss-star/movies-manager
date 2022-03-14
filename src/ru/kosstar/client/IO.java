package ru.kosstar.client;

import ru.kosstar.data.*;
import ru.kosstar.exceptions.InterruptionOfCommandException;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Класс, реализующий модуль ввода/вывода
 */
public class IO {
    private final Scanner in;
    private final PrintStream out;

    /**
     * @param in  поток ввода
     * @param out поток вывода
     */
    public IO(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Метод для считывания строки
     *
     * @return прочитанная строка
     */
    public String readString() {
        return in.nextLine().trim();
    }

    public boolean hasNext() {
        return in.hasNext();
    }

    /**
     * Метод для считывания не нулевой строки
     *
     * @return прочитанная строка
     */
    public String readNotNullString() {
        while (true) {
            String input = readString();
            if (!input.equals(""))
                return input;
            printString("Строка не может быть пустой. Введите ещё раз: ");
        }
    }

    /**
     * Метод для считывания перечисления
     *
     * @param enumName     наименование перечисления
     * @param enumValues   массив констант данного перечисления
     * @param defaultValue значение по умолчанию
     * @param <T>          тип перечисления
     * @return прочитанное значение, либо значение по умолчанию
     */
    public <T extends Enum<T>> T readEnum(String enumName, T[] enumValues, Class<T> enumClass, T defaultValue) {
        if (enumValues.length == 0)
            throw new IllegalArgumentException();
        printString(
                "Введите " + enumName + " ("
                        + Arrays.stream(enumValues)
                        .map(Objects::toString)
                        .collect(Collectors
                                .joining(", "))
                        + "): "
        );
        while (true) {
            String input = readString();
            if (input.equals(""))
                return defaultValue;
            try {
                return (T) Enum.valueOf(enumClass, input.toUpperCase());
            } catch (IllegalArgumentException e) {
                printString("Некорректное значение. Введите ещё раз: ");
            }
        }
    }

    /**
     * Метод для считывания данных о режиссёре
     *
     * @return считанный объект класса Person
     */
    public Person readPerson() {
        Person person = new Person("Default");
        printString("Введите имя режиссёра: ");
        readField(person::setName, this::readNotNullString);
        printString("Введите рост режиссёра (например, 1.87): ");
        readField(person::setGrowthInMetres, () -> readDouble(1.7));
        readField(person::setNationality,
                () -> readEnum("место рождения режиссёра",
                        Country.values(), Country.class, null));
        printString("Введите количество снятых фильмов: ");
        readField(person::setFilmCount, () -> readInt(1));
        printString("Введите паспорт id: ");
        readField(person::setPassportID, this::readNotNullString);
        return person;
    }

    /**
     * Метод для считывания данных о фильме
     *
     * @return считанный объект класса Movie
     */
    public Movie readMovie() {
        Movie movie = new Movie("Default", null);
        printString("Введите название фильма: ");
        readField(movie::setName, this::readNotNullString);
        readField(movie::setOperator, this::readPerson);
        printString("Введите год создания фильма: ");
        readField(movie::setProductionYear, () -> readInt(Calendar.getInstance().get(Calendar.YEAR)));
        readField(movie::setCountry,
                () -> readEnum("страну производства фильма:",
                        Country.values(), Country.class, null));
        readField(movie::setGenre, () -> readEnum("жанр фильма:",
                MovieGenre.values(), MovieGenre.class, null));
        printString("Введите бюджет фильма: ");
        readField(movie::setBudget, () -> readLong(1000000L));
        printString("Введите кассовые сборы фильма: ");
        readField(movie::setFees, () -> readLong(1000000L));
        readField(movie::setMpaaRating, () -> readEnum("MPAA рейтинг фильма:",
                MpaaRating.values(), MpaaRating.class, null));
        printString("Введите продолжительность фильма в минутах: ");
        readField(movie::setDurationInMinutes, () -> readInt(120));
        printString("Введите количество оскаров фильма: ");
        readField(movie::setOscarsCount, () -> readInt(0));
        printString("Введите координаты фильма: ");
        readField(movie::setCoordinates, this::readNotNullCoordinates);
        return movie;
    }

    /**
     * Метод для считывания целого числа, значение которого не может быть null
     *
     * @return считанное целое число
     */
    public int readInt() {
        while (true) {
            Integer i = readInt(null);
            if (i == null)
                printString("Значение не может быть пустым. Введите целое число: ");
            else
                return i;
        }
    }

    /**
     * Метод для считывания числа типа int
     *
     * @param defaultValue значение по умолчанию
     * @return считанное значение
     */
    public Integer readInt(Integer defaultValue) {
        try {
            while (true) {
                String input = readString();
                if (input.equals("")) return defaultValue;
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    printString("Некорректное значение. Введите целое число: ");
                }
            }
        } catch (NoSuchElementException e) {
            return defaultValue;
        }
    }

    /**
     * Метод для считывания числа типа long
     *
     * @param defaultValue значение по умолчанию
     * @return считанное значение
     */
    public Long readLong(Long defaultValue) {
        try {
            while (true) {
                String input = readString();
                if (input.equals("")) return defaultValue;
                try {
                    return Long.parseLong(input);
                } catch (NumberFormatException e) {
                    printString("Некорректное значение. Введите целое число: ");
                }
            }
        } catch (NoSuchElementException e) {
            return defaultValue;
        }
    }

    /**
     * Метод для считывания числа типа double
     *
     * @param defaultValue значение по умолчанию
     * @return считанное значение
     */
    public Double readDouble(Double defaultValue) {
        try {
            while (true) {
                String input = in.nextLine().trim();
                if (input.equals("")) return defaultValue;
                try {
                    return Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    printString("Некорректное значение. Введите число: ");
                }
            }
        } catch (NoSuchElementException e) {
            return defaultValue;
        }
    }

    /**
     * Метод для считывания числа типа float
     *
     * @param defaultValue значение по умолчанию
     * @return считанное значение
     */
    public Float readFloat(Float defaultValue) {
        try {
            while (true) {
                String input = in.nextLine().trim();
                if (input.equals("")) return defaultValue;
                try {
                    return Float.parseFloat(input);
                } catch (NumberFormatException e) {
                    printString("Некорректное значение. Введите число: ");
                }
            }
        } catch (NoSuchElementException e) {
            return defaultValue;
        }
    }

    /**
     * Метод для считывания координат, которые не могут быть null
     *
     * @return считанные координаты
     */
    public Coordinates readNotNullCoordinates() {
        Coordinates coordinates = new Coordinates(0D, 0F);
        printString("Введите координату x:");
        readField(coordinates::setX, () -> readDouble(0D));
        printString("Введите координату y:");
        readField(coordinates::setY, () -> readFloat(0F));
        return coordinates;
    }

    /**
     * Метод для вывода сообщения
     *
     * @param str сообщение
     */
    public void printString(String str) {
        if (out != null)
            out.println(str);
    }

    /**
     * Метод для считывания поля
     *
     * @param setter метод для установки значения в данное поле
     * @param reader метод для считывания значения данного поля
     */
    private <T> void readField(Consumer<T> setter, Supplier<T> reader) {
        while (true) {
            try {
                setter.accept(reader.get());
                break;
            } catch (IllegalArgumentException e) {
                printString(e.getMessage());
            }
        }
    }

    public File readFileName() {
        File file = null;
        while (file == null) {
            printString("Введите имя файла: ");
            String fileName = readString();
            if (fileName.equals(""))
                return null;
            file = new File(fileName);
            if (!file.exists() || !file.canRead()) {
                printString("Файл не существует или не может быть прочитан.");
                file = null;
            }
        }
        return file;
    }
}
