package ru.kosstar.server.commands;

import ru.kosstar.data.*;
import ru.kosstar.server.MovieManager;

import java.util.stream.Collectors;

/**
 * Класс, описывающий команду, которая возвращает всех операторов в порядке возрастания
 */
public class PrintFieldAscendingOperatorCommand extends AbstractCommand<Nothing> {

    public PrintFieldAscendingOperatorCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String execute() {
        return movieManager
                .getMovies()
                .values()
                .stream()
                .map(Movie::getOperator)
                .map(Person::getName)
                .sorted()
                .distinct()
                .collect(Collectors.joining("\n"));
    }
}
