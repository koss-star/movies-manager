package ru.kosstar.server.commands;

import ru.kosstar.server.MovieManager;

/**
 * Суперкласс для всех команд серверного приложения,
 * которые занимаются выполнением команд пользователя
 *
 * @param <T> тип аргумента команды
 */
public abstract class AbstractCommand<T> implements Comparable<AbstractCommand<T>> {
    protected final String name;
    protected final String description;
    protected final MovieManager movieManager;

    /**
     * @param name         название команды
     * @param description  описание команды
     * @param movieManager объект класса, работающего с коллекцией фильмов
     */
    public AbstractCommand(String name, String description, MovieManager movieManager) {
        this.name = name;
        this.description = description;
        this.movieManager = movieManager;
    }

    /**
     * Метод для выполнения команды с аргументом
     *
     * @param argument аргумент команды
     * @return результат выполнения команды
     */
    public final String execute(Object argument) {
        if (argument != null) {
            try {
                return executeWithArg((T) argument);
            } catch (ClassCastException ignored) {
                throw new IllegalArgumentException("Недопустимый аргумент");
            }
        } else
            throw new IllegalArgumentException("Аргумент не может быть null");
    }

    /**
     * Метод для выполнения команды без аргументов
     *
     * @return результат выполнения команды
     */
    public String execute() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Метод для выполнения команды с аргументом
     *
     * @param argument аргумент команды
     * @return результат выполнения команды
     */
    public String executeWithArg(T argument) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    void undo() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int compareTo(AbstractCommand<T> o) {
        return this.name.compareTo(o.name);
    }
}

interface Nothing {
}
