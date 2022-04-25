package ru.kosstar.server.commands;

import ru.kosstar.data.User;
import ru.kosstar.server.MovieManager;

public class CommandNotFoundCommand extends AbstractCommand<Nothing, String> {

    public CommandNotFoundCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String execute(User user) {
        return "Команда не найдена.";
    }
}
