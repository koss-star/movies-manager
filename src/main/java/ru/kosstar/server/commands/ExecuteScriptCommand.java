package ru.kosstar.server.commands;

import ru.kosstar.connection.CommandWithArgument;
import ru.kosstar.server.FailedCommandExecutionException;
import ru.kosstar.server.MovieManager;

import java.util.*;

/**
 * Класс, описывающий команду, которая считывает и исполняет скрипт
 */
public class ExecuteScriptCommand extends AbstractCommand<List<CommandWithArgument>, List<Object>> {

    public ExecuteScriptCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public List<Object> executeWithArg(List<CommandWithArgument> argument) throws FailedCommandExecutionException {
        List<Object> result = new ArrayList<>();
        for (CommandWithArgument commandWithArgument : argument) {
            String name = commandWithArgument.getName();
            Object argumentCommand = commandWithArgument.getArgument();
            result.add(movieManager.executeCommand(new CommandWithArgument(name, argumentCommand)));
        }
        return result;
    }
}
