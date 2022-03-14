package ru.kosstar.server;

import ru.kosstar.CommandWithArgument;
import ru.kosstar.exceptions.InvalidFileException;

import java.io.File;

/**
 * Класс, определяющий серверное приложение, который объединяет
 * все необходимые модули для выполнения команд над коллекцией фильмов
 */
public class Server {
    MovieManager manager;

    /**
     * @param file файл с коллекцией фильмов
     * @throws InvalidFileException если файл не может быть открыт или его не существует
     */
    public Server(File file) throws InvalidFileException {
        this.manager = new MovieManager(file);
    }

    /**
     * Метод для получения запроса от клиента на выполнение команды
     *
     * @param command команда
     * @return результат выполнения команды
     */
    public String executeCommand(CommandWithArgument command) {
        return manager.executeCommand(command);
    }
}


