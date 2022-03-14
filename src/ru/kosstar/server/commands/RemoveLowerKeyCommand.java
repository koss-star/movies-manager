package ru.kosstar.server.commands;

import ru.kosstar.data.Movie;
import ru.kosstar.server.MovieManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, определяющий команду, удаляющую из коллекции все фильмы, ключ которых меньше, чем заданный
 */
public class RemoveLowerKeyCommand extends AbstractCommand<Integer> {

    public RemoveLowerKeyCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String executeWithArg(Integer argument) {
        List<Integer> list = movieManager
                .getMovies()
                .values()
                .stream()
                .map(Movie::getId)
                .filter(id -> id < argument)
                .collect(Collectors.toList());
        list.forEach(movieManager.getMovies()::remove);
        if (!list.isEmpty())
            return "Удалено " + list.size() + " фильм(-ов/-а).";
        else
            return "Ни один фильм не удалён.";
    }
}
