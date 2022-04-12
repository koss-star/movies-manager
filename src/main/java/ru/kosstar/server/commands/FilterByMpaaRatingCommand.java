package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.data.MpaaRating;
import ru.kosstar.server.MovieManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, описывающий команду, возвращающую фильмы, MPAA рейтинг которых равен заданному
 */
public class FilterByMpaaRatingCommand extends AbstractCommand<MpaaRating, List<Movie>> {

    public FilterByMpaaRatingCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public List<Movie> executeWithArg(MpaaRating argument) {
        List<Movie> movies = movieManager
                .getMovies()
                .values()
                .stream()
                .filter(f -> f.getMpaaRating().equals(argument))
                .collect(Collectors.toList());
        if (movies.isEmpty())
            movies.add(null);
        return movies;
    }
}
