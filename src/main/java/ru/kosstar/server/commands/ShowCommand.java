package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.data.User;
import ru.kosstar.server.MovieManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, описывающий команду, которая выводит все элементы коллекции фильмов
 */
public class ShowCommand extends AbstractCommand<Nothing, List<Movie>> {

    public ShowCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public List<Movie> execute(User user) {
        return movieManager
                .getMovies()
                .values()
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }
}
