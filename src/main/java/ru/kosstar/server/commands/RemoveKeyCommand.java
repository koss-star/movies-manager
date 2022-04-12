package ru.kosstar.server.commands;

import ru.kosstar.server.FailedCommandExecutionException;
import ru.kosstar.server.MovieManager;

/**
 * Класс, описывающий команду, которая удаляет фильм из коллекции по его id
 */
public class RemoveKeyCommand extends AbstractCommand<Integer, String> {

    public RemoveKeyCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String executeWithArg(Integer argument) throws FailedCommandExecutionException {
        if (movieManager.getMovies().remove(argument) == null)
            throw new FailedCommandExecutionException("Фильм не найден.");
        return "Фильм удалён.";
    }
}
