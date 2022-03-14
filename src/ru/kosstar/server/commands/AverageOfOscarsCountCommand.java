package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.server.MovieManager;

/**
 * Класс, описывающий команду, которая считает среднее значение оскаров всех фильмов
 */
public class AverageOfOscarsCountCommand extends AbstractCommand<Nothing> {

    public AverageOfOscarsCountCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String execute() {
        return String.valueOf(
                movieManager
                        .getMovies()
                        .values()
                        .stream()
                        .mapToLong(Movie::getOscarsCount)
                        .average()
                        .orElse(0)
        );
    }
}
