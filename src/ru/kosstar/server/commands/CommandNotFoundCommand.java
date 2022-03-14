package ru.kosstar.server.commands;

import ru.kosstar.server.MovieManager;

public class CommandNotFoundCommand extends AbstractCommand<Nothing> {

    public CommandNotFoundCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String execute() {
        return "Команда не найдена.";
    }
}
