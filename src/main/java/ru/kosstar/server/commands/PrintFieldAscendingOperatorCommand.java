package ru.kosstar.server.commands;

import ru.kosstar.data.*;
import ru.kosstar.server.MovieManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, описывающий команду, которая возвращает всех операторов в порядке возрастания
 */
public class PrintFieldAscendingOperatorCommand extends AbstractCommand<Nothing, List<Person>> {

    public PrintFieldAscendingOperatorCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public List<Person> execute() {
        return movieManager
                .getMovies()
                .values()
                .stream()
                .map(Movie::getOperator)
                .sorted()
                .distinct()
                .collect(Collectors.toList());
    }
}
