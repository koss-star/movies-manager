package ru.kosstar.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import ru.kosstar.connection.CommandWithArgument;
import ru.kosstar.data.Movie;
import ru.kosstar.server.commands.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Класс для управления коллекцией фильмов с помощью команд
 */
public class MovieManager {
    private final Map<Integer, Movie> movies = new HashMap<>();
    private final Map<String, AbstractCommand<?, ?>> commands = new HashMap<>();
    private final Queue<AbstractCommand<?, ?>> commandHistory = new ArrayDeque<>();
    private final LocalDateTime initTime;
    private final AbstractCommand<?, ?> commandNotFound = new CommandNotFoundCommand(null, null, this);
    private final SaveCommand saveCommand = new SaveCommand("save", "сохранить коллекцию в файл", this);
    private final File moviesFile;

    /**
     * @param file файл с коллекцией фильмов
     * @throws InvalidFileException если файл не может быть открыт или его не существует
     */
    public MovieManager(File file) throws InvalidFileException {
        if (!file.canRead() || !file.canWrite())
            throw new InvalidFileException();
        readMoviesFromFile(file);
        this.moviesFile = file;
        initTime = LocalDateTime.now();
        createCommands();
    }

    public void saveMovies() {
        saveCommand.executeWithArg(moviesFile.getName());
    }

    private void readMoviesFromFile(File file) throws InvalidFileException {
        if (file == null)
            throw new InvalidFileException();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String collectionJson = reader.lines().collect(Collectors.joining());
                Movie[] movies = gson.fromJson(collectionJson, Movie[].class);
                for (Movie movie : movies) {
                    this.movies.put(movie.getId(), movie);
                }
            }
        } catch (IOException e) {
            throw new InvalidFileException("Произошла ошибка чтения из файла.");
        } catch (JsonSyntaxException e) {
            throw new InvalidFileException("Некорректный формат данных в файле.");
        }
    }

    private void createCommands() {
        commands.put("help", new HelpCommand("help", "вывести справку по доступным командам", this));
        commands.put("info", new InfoCommand("info", "вывести информацию о коллекции", this));
        commands.put("show", new ShowCommand("show", "вывести все элементы коллекции", this));
        commands.put("insert", new InsertCommand("insert", "добавить новый фильм", this));
        commands.put("update", new UpdateCommand("update", "изменить фильм по его id", this));
        commands.put("remove_key", new RemoveKeyCommand("remove_key", "удалить фильм из коллекции по его id", this));
        commands.put("clear", new ClearCommand("clear", "очистить коллекцию", this));
        commands.put("execute_script", new ExecuteScriptCommand("execute_script", "считать и исполнить скрипт из указанного файла", this));
        commands.put("remove_lower", new RemoveLowerCommand("remove_lower", "удалить из коллекции все фильмы, меньшие, чем заданный", this));
        commands.put("history", new HistoryCommand("history", "вывести последние 14 команд", this));
        commands.put("remove_lower_key", new RemoveLowerKeyCommand("remove_lower_key", "удалить из коллекции все фильмы, ключ которых меньше, чем заданный", this));
        commands.put("sum_of_budget", new SumOfBudgetCommand("sum_of_budget", "вывести сумму значений поля бюджет для всех фильмов", this));
        commands.put("count_less_than_budget", new CountLessThanBudgetCommand("count_less_than_budget", "вывести количество фильмов, бюджет которых меньше заданного", this));
        commands.put("count_greater_than_mpaa_rating", new CountGreaterThanMpaaRatingCommand("count_greater_than_mpaa_rating", "вывести количество элементов, значение поля mpaaRating которых больше заданного", this));
        commands.put("average_of_oscars_count", new AverageOfOscarsCountCommand("average_of_oscars_count", "вывести среднее значение оскаров", this));
        commands.put("filter_by_mpaa_rating", new FilterByMpaaRatingCommand("filter_by_mpaa_rating", "вывести фильмы, mpaa рейтинг которых равен заданному", this));
        commands.put("print_field_ascending_operator", new PrintFieldAscendingOperatorCommand("print_field_ascending_operator", "вывести всех операторов в порядке возрастания", this));
    }

    /**
     * Метод для получения коллекции фильмов
     *
     * @return коллекция фильмов
     */
    public Map<Integer, Movie> getMovies() {
        return movies;
    }

    /**
     * Метод для получения коллекции команд
     *
     * @return коллекция команд
     */
    public Map<String, AbstractCommand<?, ?>> getCommands() {
        return commands;
    }

    /**
     * Метод для получения списка выполненных команд над коллекцией фильмов
     *
     * @return список команд
     */
    public Queue<AbstractCommand<?, ?>> getCommandHistory() {
        return commandHistory;
    }

    /**
     * Метод для выполнения команд над коллекцией
     *
     * @param commandWithArgument команда для исполнения
     * @return результат выполнения команды
     */
    public Object executeCommand(CommandWithArgument commandWithArgument) throws FailedCommandExecutionException,
            IllegalArgumentException,
            UnsupportedOperationException {
        if (commandWithArgument.getArgument() == null)
            return executeCommand(commandWithArgument.getName());
        AbstractCommand<?, ?> command = commands.getOrDefault(commandWithArgument.getName(), commandNotFound);
        if (command != commandNotFound)
            pushCommandToHistory(command);
        return command.execute(commandWithArgument.getArgument());
    }

    private Object executeCommand(String commandName) throws FailedCommandExecutionException {
        AbstractCommand<?, ?> command = commands.getOrDefault(commandName, commandNotFound);
        if (command != commandNotFound)
            pushCommandToHistory(command);
        return command.execute();
    }

    /**
     * Метод для получения даты создания коллекции
     *
     * @return дата создания коллекции
     */
    public LocalDateTime getInitTime() {
        return initTime;
    }

    private void pushCommandToHistory(AbstractCommand<?, ?> command) {
        if (commandHistory.size() >= 14)
            commandHistory.poll();
        commandHistory.add(command);
    }
}
