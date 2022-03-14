package ru.kosstar.client;

import ru.kosstar.client.commands.*;
import ru.kosstar.exceptions.InterruptionOfCommandException;

import java.io.File;
import java.util.*;

/**
 * Класс, который взаимодействует с клиентом
 */
public class Client {
    private static final Map<String, AbstractCommand> commands = new HashMap<>();
    private final IO io;

    /**
     * Конструктор с заданным модулем ввода/вывода
     *
     * @param io объект, представляющий модуль ввода/вывода
     */
    public Client(IO io) {
        this.io = io;
        createCommands();
    }

    /**
     * Конструктор со стандартным потоком ввода/вывода
     */
    public Client() {
        this(new IO(System.in, System.out));
    }

    /**
     * Метод для считывания имени файла, в котором лежат данные с коллекцией фильмов
     *
     * @return считанное имя файла
     */
    public File readFileName() {
        File file = null;
        String fileName = System.getenv("MOVIE_MANAGER_FILE");
        if (fileName != null && !fileName.equals(""))
            file = new File(fileName);
        if (file == null || !file.exists() || !file.canRead())
            file = io.readFileName();
        return file;
    }

    /**
     * Метод для считывания названия команды
     *
     * @return прочитанное название команды
     */
    public String readCommand() throws InterruptionOfCommandException {
        try {
            printString("Введите команду:");
            return io.readNotNullString();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Метод для считывания аргументов команды
     *
     * @param commandName название команды
     * @return результат выполнения команды
     * @throws InterruptionOfCommandException если необходимо
     *                                        прервать выполнение команды из-за некорректного ввода данных
     */
    public Object readCommandArg(String commandName) throws InterruptionOfCommandException {
        AbstractCommand command = commands.get(commandName);
        if (command == null)
            return null;
        else
            return command.execute(io);
    }

    public void printString(String str) {
        io.printString(str);
    }

    private void createCommands() {
        commands.put("count_greater_than_mpaa_rating", new CountGreaterThanMpaaRatingCommand());
        commands.put("count_less_than_budget", new CountLessThanBudgetCommand());
        commands.put("execute_script", new ExecuteScriptCommand());
        commands.put("filter_by_mpaa_rating", new FilterByMpaaRatingCommand());
        commands.put("insert", new InsertCommand());
        commands.put("remove_key", new RemoveKeyCommand());
        commands.put("remove_lower", new RemoveLowerCommand());
        commands.put("remove_lower_key", new RemoveLowerKeyCommand());
        commands.put("save", new SaveCommand());
        commands.put("update", new UpdateCommand());
        commands.put("exit", new ExitCommand());
    }
}
