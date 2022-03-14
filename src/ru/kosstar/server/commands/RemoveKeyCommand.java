package ru.kosstar.server.commands;

import ru.kosstar.server.MovieManager;

/**
 * Класс, описывающий команду, которая удаляет фильм из коллекции по его id
 */
public class RemoveKeyCommand extends AbstractCommand<Integer> {

    public RemoveKeyCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String executeWithArg(Integer argument) {
        movieManager.getMovies().remove(argument);
        return "Фильм удалён.";
    }
}
