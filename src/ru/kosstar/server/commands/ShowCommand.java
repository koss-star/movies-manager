package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.server.MovieManager;

import java.util.stream.Collectors;

/**
 * Класс, описывающий команду, которая выводит все элементы коллекции фильмов
 */
public class ShowCommand extends AbstractCommand<Nothing> {

    public ShowCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String execute() {
        return movieManager
                .getMovies()
                .values()
                .stream()
                .sorted()
                .map(Movie::toString)
                .collect(Collectors.joining("\n"));
    }
}
