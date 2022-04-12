package ru.kosstar.server.commands;

import ru.kosstar.server.MovieManager;

/**
 * Класс, описывающий команду, которая очищает коллекцию
 */
public class ClearCommand extends AbstractCommand<Nothing, String> {

    public ClearCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String execute() {
        movieManager.getMovies().clear();
        return "Коллекция очищена.";
    }
}
