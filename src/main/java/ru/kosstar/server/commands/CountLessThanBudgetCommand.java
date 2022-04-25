package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.data.User;
import ru.kosstar.server.MovieManager;

/**
 * Класс, описывающий команду, считающую количество фильмов, бюджет которых меньше заданного
 */
public class CountLessThanBudgetCommand extends AbstractCommand<Number, Long> {

    public CountLessThanBudgetCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public Long executeWithArg(User user, Number argument) {
        long budget = argument.longValue();
        return movieManager
                .getMovies()
                .values()
                .stream()
                .map(Movie::getBudget)
                .filter(f -> (f < budget))
                .count();
    }
}
