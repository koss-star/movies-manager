package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.data.User;
import ru.kosstar.server.MovieManager;

import java.util.IntSummaryStatistics;

/**
 * Класс, описывающий команду, которая возвращает информацию о коллекции
 */
public class InfoCommand extends AbstractCommand<Nothing, String> {

    public InfoCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String execute(User user) {
        IntSummaryStatistics statistics = movieManager
                .getMovies()
                .values()
                .stream()
                .mapToInt(Movie::getProductionYear)
                .summaryStatistics();

        if (statistics.getCount() == 0) {
            statistics.accept(0);
        }
        return "Тип коллекции: "
                + movieManager.getMovies().getClass().getSimpleName()
                + "\n"
                + "Количество фильмов: "
                + movieManager.getMovies().size()
                + "\n"
                + "Года производства фильмов: "
                + statistics.getMin()
                + " - "
                + statistics.getMax()
                + "\n";
    }
}
