package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.data.MpaaRating;
import ru.kosstar.data.User;
import ru.kosstar.server.MovieManager;

/**
 * Класс, описывающий команду, считающую количество фильмов,
 * значение поля mpaaRating которых больше заданного
 */
public class CountGreaterThanMpaaRatingCommand extends AbstractCommand<MpaaRating, Long> {

    public CountGreaterThanMpaaRatingCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public Long executeWithArg(User user, MpaaRating argument) {
        return movieManager
                .getMovies()
                .values()
                .stream()
                .map(Movie::getMpaaRating)
                .filter(f -> f.compareTo(argument) > 0)
                .count();
    }
}
