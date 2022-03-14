package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.server.MovieManager;

/**
 * Класс, описывающий команду, которая добавляет новый фильм
 */
public class InsertCommand extends AbstractCommand<Movie> {

    public InsertCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String executeWithArg(Movie movie) {
        movieManager.getMovies().put(movie.getId(), movie);
        return "Фильм успешно добавлен!";
    }
}
