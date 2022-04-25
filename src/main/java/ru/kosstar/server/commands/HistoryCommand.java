package ru.kosstar.server.commands;

import ru.kosstar.data.User;
import ru.kosstar.server.MovieManager;

import java.util.stream.Collectors;

/**
 * Класс, описывающий команду, которая возвращает последние 14 команд
 */
public class HistoryCommand extends AbstractCommand<Nothing, String> {

    public HistoryCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String execute(User user) {
        return movieManager
                .getCommandHistory(user)
                .stream()
                .map(cmd -> cmd.name)
                .collect(Collectors.joining("\n"));
    }
}
