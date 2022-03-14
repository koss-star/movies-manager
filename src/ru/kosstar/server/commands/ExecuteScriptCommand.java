package ru.kosstar.server.commands;

import ru.kosstar.CommandWithArgument;
import ru.kosstar.server.MovieManager;

import java.util.*;

/**
 * Класс, описывающий команду, которая считывает и исполняет скрипт
 */
public class ExecuteScriptCommand extends AbstractCommand<List<CommandWithArgument>> {

    public ExecuteScriptCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String executeWithArg(List<CommandWithArgument> argument) {
        List<String> result = new ArrayList<>();
        for (CommandWithArgument commandWithArgument : argument) {
            String name = commandWithArgument.getName();
            Object argumentCommand = commandWithArgument.getArgument();
            result.add(movieManager.executeCommand(new CommandWithArgument(name, argumentCommand)));
        }
        return String.join("\n", result);
    }
}
