package ru.kosstar.server.commands;

import ru.kosstar.data.User;
import ru.kosstar.server.MovieManager;

import java.util.stream.Collectors;

/**
 * Класс, описывающий команду, которая возвращает справку по доступным командам
 */
public class HelpCommand extends AbstractCommand<Nothing, String> {

    public HelpCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String execute(User user) {
        return movieManager.getCommands()
                .values()
                .stream()
                .map(cmd -> cmd.name + ": " + cmd.description)
                .sorted()
                .collect(Collectors.joining("\n"));
    }
}
