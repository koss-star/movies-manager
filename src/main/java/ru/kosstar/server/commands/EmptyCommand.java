package ru.kosstar.server.commands;

import ru.kosstar.server.MovieManager;

public class EmptyCommand extends AbstractCommand<Nothing, String> {

    public EmptyCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String execute() {
        return "";
    }
}
