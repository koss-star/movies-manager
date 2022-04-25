package ru.kosstar.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kosstar.client.commands.*;
import ru.kosstar.connection.ResponseType;
import ru.kosstar.connection.ServerMessage;
import ru.kosstar.data.User;
import ru.kosstar.exceptions.InterruptionOfCommandException;
import ru.kosstar.server.Server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Класс, который взаимодействует с клиентом
 */
public class Client {

    private static final Logger logger = LogManager.getLogger("ClientLogger");
    private static int serverPort = 2406;
    private static final Map<String, AbstractCommand> commands = new HashMap<>();
    private final IO io;
    private final NetworkConnection connection;

    /**
     * Конструктор с заданным модулем ввода/вывода
     *
     * @param io            объект, представляющий модуль ввода/вывода
     * @param remoteAddress адрес сервера
     * @throws InternalClientException если не удалось создать объект клиента
     */
    public Client(IO io, InetSocketAddress remoteAddress) throws InternalClientException {
        this.io = io;
        createCommands();
        try {
            if (remoteAddress != null)
                connection = new NetworkConnection(remoteAddress);
            else
                connection = null;
        } catch (IOException e) {
            throw new InternalClientException();
        }
    }

    /**
     * Конструктор для создания клиента без соединения к серверу
     */
    public Client(IO io) throws InternalClientException {
        this(io, null);
    }

    /**
     * Метод для считывания названия команды
     *
     * @return прочитанное название команды
     */
    public String readCommand() {
        try {
            io.printString("Введите команду:");
            return io.readNotEmptyString();
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

    private void exit() {
        ((ExitCommand) commands.get("exit")).execute(io);
    }

    private static void checkServerMessage(ServerMessage serverMessage) throws InternalServerException, InternalClientException {
        ResponseType type = serverMessage.getType();
        switch (type) {
            case NOT_AVAILABLE:
            case INTERNAL_SERVER_ERROR:
                throw new InternalServerException();
            case INVALID_REQUEST:
                throw new InternalClientException();
        }
    }

    private static String handleCommandResult(ServerMessage serverMessage) throws InternalClientException {
        ResponseType type = serverMessage.getType();
        switch (type) {
            case OK:
                return serverMessage.getMessageBody().toString();
            case ILLEGAL_ACCESS:
                return "Недостаточно прав.";
            case FAILED_EXECUTION:
                return serverMessage.getMessageBody().toString();
            default:
                logger.error("Этого тут быть не должно " + type);
                throw new InternalClientException();
        }
    }

    private User userSignIn() throws InternalClientException, InternalServerException {
        User user = User.readUserData(io);
        ServerMessage serverMessage = connection.signIn(user);
        checkServerMessage(serverMessage);
        if (serverMessage.isSuccess())
            return user;
        else
            return null;
    }

    private User userSignUp() throws InternalServerException, InternalClientException {
        User user = User.readUserData(io);
        ServerMessage serverMessage = connection.signUp(user);
        checkServerMessage(serverMessage);
        if (serverMessage.isSuccess())
            return user;
        else
            return null;
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

    private User readUser() throws InternalServerException, InternalClientException {
        User user = null;
        while (user == null) {
            try {
                io.printString("Войти (login) или зарегистрироваться (signup)? Для выхода введите \"exit\".");
                String serviceCommand = io.readNotEmptyString();
                if (serviceCommand.equalsIgnoreCase("login")) {
                    user = userSignIn();
                    if (user == null)
                        System.out.println("Неверный логин или пароль.");
                } else if (serviceCommand.equalsIgnoreCase("signup")) {
                    user = userSignUp();
                    if (user == null)
                        System.out.println("Пользователь с таким логином уже существует.");
                } else if (serviceCommand.equalsIgnoreCase("exit")) {
                    exit();
                }
            } catch (NoSuchElementException e) {
                exit();
            }
        }
        return user;
    }

    public static void main(String[] args) {
        if (args.length != 0)
            serverPort = Integer.parseInt(args[0]);
        try {
            InetSocketAddress serverAddr = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);
            Client client = new Client(new IO(System.in, System.out), serverAddr);
            User user = client.readUser();
            String commandName = client.readCommand();
            while (commandName != null) {
                try {
                    Object commandArg = client.readCommandArg(commandName);
                    ServerMessage receivedMessage = client.connection.sendCommand(user, commandName, commandArg);
                    Client.checkServerMessage(receivedMessage);
                    client.io.printString(handleCommandResult(receivedMessage));
                } catch (InterruptionOfCommandException e) {
                    if (e.getMessage() != null && !e.getMessage().equals(""))
                        client.io.printString(e.getMessage());
                    client.io.printString("Команда прервана.");
                }
                commandName = client.readCommand();
            }
        } catch (InternalServerException e) {
            logger.error("Ошибка сервера", e);
            System.out.println("Сервер недоступен.");
        } catch (InternalClientException | UnknownHostException e) {
            logger.error("Ошибка клиента", e);
            System.out.println("Возникла внутренняя ошибка приложения.");
        }
    }
}
