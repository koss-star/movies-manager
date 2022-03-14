package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.server.MovieManager;

/**
 * Класс, описывающий команду, которая возвращает сумму значений поля бюджет для всех фильмов
 */
public class SumOfBudgetCommand extends AbstractCommand<Nothing> {

    public SumOfBudgetCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String execute() {
        return String.valueOf(movieManager
                .getMovies()
                .values()
                .stream()
                .map(Movie::getBudget)
                .reduce(Long::sum)
                .orElse(0L)
        );
    }
}
