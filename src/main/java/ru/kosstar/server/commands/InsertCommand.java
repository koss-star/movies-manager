package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.data.User;
import ru.kosstar.server.FailedCommandExecutionException;
import ru.kosstar.server.MovieManager;

import java.sql.SQLException;

/**
 * Класс, описывающий команду, которая добавляет новый фильм
 */
public class InsertCommand extends AbstractCommand<Movie, String> {

    public InsertCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String executeWithArg(User user, Movie movie) throws FailedCommandExecutionException {
        try {
            movieManager.getMoviesLock().lock();
            movie.setOwner(user.getLogin());
            movie = movieManager.getMovieRepository().insert(movie);
        } catch (SQLException e) {
            throw new FailedCommandExecutionException(e);
        } finally {
            movieManager.getMoviesLock().unlock();
        }
        movieManager.getMovies().put(movie.getId(), movie);
        return "Фильм успешно добавлен!";
    }
}
