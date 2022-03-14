package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.server.MovieManager;

/**
 * Класс, описывающий команду, которая изменяет фильм по его id
 */
public class UpdateCommand extends AbstractCommand<Movie> {

    public UpdateCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String executeWithArg(Movie argument) {
        movieManager.getMovies().replace(argument.getId(), argument);
        if (movieManager.getMovies().containsKey(argument.getId()))
            return "Фильм с данным id " + argument.getId() + " изменён.";
        else
            return "Фильма с данным id " + argument.getId() + " не существует.";
    }
}
