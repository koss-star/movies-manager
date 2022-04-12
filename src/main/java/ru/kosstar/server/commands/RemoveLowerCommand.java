package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.server.FailedCommandExecutionException;
import ru.kosstar.server.MovieManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, описывающий команду, которая удаляет из коллекции все фильмы, меньшие, чем заданный
 */
public class RemoveLowerCommand extends AbstractCommand<String, String> {

    public RemoveLowerCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String executeWithArg(String argument) throws FailedCommandExecutionException {
        List<Integer> list = movieManager
                .getMovies()
                .values()
                .stream()
                .filter(f -> f.getName().compareTo(argument) < 0)
                .map(Movie::getId)
                .collect(Collectors.toList());
        list.forEach(movieManager.getMovies()::remove);
        if (list.isEmpty())
            throw new FailedCommandExecutionException("Ни один фильм не удалён.");
        return "Удалено " + list.size() + " фильм(-ов/-а).";
    }
}
