package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.data.User;
import ru.kosstar.server.MovieManager;

/**
 * Класс, описывающий команду, которая возвращает сумму значений поля бюджет для всех фильмов
 */
public class SumOfBudgetCommand extends AbstractCommand<Nothing, Long> {

    public SumOfBudgetCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public Long execute(User user) {
        return movieManager
                .getMovies()
                .values()
                .stream()
                .map(Movie::getBudget)
                .reduce(Long::sum)
                .orElse(0L);
    }
}
