package ru.kosstar.client;

import ru.kosstar.client.commands.*;
import ru.kosstar.connection.ServerMessage;
import ru.kosstar.data.User;
import ru.kosstar.exceptions.InterruptionOfCommandException;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Класс, который взаимодействует с клиентом
 */
public class Client {
    private static int serverPort = 2406;
    private static final Map<String, AbstractCommand> commands = new HashMap<>();
    private final IO io;
    private final NetworkConnection connection;

    /**
     * Конструктор с заданным модулем ввода/вывода
     *
     * @param io            объект, представляющий модуль ввода/вывода
     * @param remoteAddress TODO
     * @throws ClientException если не удалось создать объект клиента
     */
    public Client(IO io, InetSocketAddress remoteAddress) throws ClientException {
        this.io = io;
        createCommands();
        try {
            if (remoteAddress != null)
                connection = new NetworkConnection(remoteAddress);
            else
                connection = null;
        } catch (IOException e) {
            throw new ClientException();
        }
    }

    /**
     * Конструктор для создания клиента без соединения к серверу
     */
    public Client(IO io) throws ClientException {
        this(io, null);
    }

    /**
     * Метод для считывания названия команды
     *
     * @return прочитанное название команды
     */
    public String readCommand() {
        io.printString("Введите команду:");
        return io.readNotEmptyString();
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

    public void exit() {
        ((ExitCommand) commands.get("exit")).execute(io);
    }

    private User userSignIn() {
        User user = User.readUserData(io);
        return connection.signIn(user) ? user : null;
    }

    private User userSignUp() {
        User user = User.readUserData(io);
        return connection.signUp(user) ? user : null;
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
        commands.put("update", new UpdateCommand());
        commands.put("exit", new ExitCommand());
    }

    private User readUser() {
        User user = null;
        while (user == null) {
//            io.printString("Войти или зарегистрироваться? Для выхода введите \"выход\".");
//            String serviceCommand = io.readNotEmptyString();
            String serviceCommand = "войти";
            if (serviceCommand.equalsIgnoreCase("войти")) {
                user = new User("", "");
//                user = userSignIn();
            } else if (serviceCommand.equalsIgnoreCase("зарегистрироваться")) {
                user = new User("", "");
//                user = userSignUp();
            } else if (serviceCommand.equalsIgnoreCase("выход")) {
                exit();
            }
        }
        return user;
    }

    public static void main(String[] args) {
        if (args.length != 0)
            serverPort = Integer.parseInt(args[0]);
        Client client = null;
        try {
            InetSocketAddress serverAddr = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);
            client = new Client(new IO(System.in, System.out), serverAddr);
        } catch (UnknownHostException | ClientException e) {
            System.out.println("Возникла внутренняя ошибка приложения.");
            System.exit(-1);
        }

        User user = client.readUser();
        String commandName = client.readCommand();
        while (commandName != null) {
            try {
                Object commandArg = client.readCommandArg(commandName);
                ServerMessage receivedMessage = client.connection.sendCommand(user, commandName, commandArg);
                client.io.printString(receivedMessage.getMessageBody().toString());
            } catch (InterruptionOfCommandException e) {
                if (e.getMessage() != null && !e.getMessage().equals(""))
                    client.io.printString(e.getMessage());
                client.io.printString("Команда прервана.");
            }
            commandName = client.readCommand();
        }
    }
}
