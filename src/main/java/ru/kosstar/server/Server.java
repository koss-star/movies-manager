package ru.kosstar.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kosstar.connection.ClientMessage;
import ru.kosstar.connection.ServerMessage;

import java.io.*;
import java.net.SocketException;
import java.util.Scanner;

/**
 * Класс, определяющий серверное приложение, который объединяет
 * все необходимые модули для выполнения команд над коллекцией фильмов
 */
public class Server {
    private static int port = 2406;
    private static final Logger logger = LogManager.getLogger(Server.class);
    MovieManager movieManager;
    ClientManager clientManager;
    NetworkConnection networkConnection;

    /**
     * @param file файл с коллекцией фильмов
     * @throws InvalidFileException если файл не может быть открыт или его не существует
     */
    public Server(File file, int port) throws SocketException, InvalidFileException {
        this.movieManager = new MovieManager(file);
        this.networkConnection = new NetworkConnection(port);
        this.clientManager = new ClientManager(movieManager);
        logger.info("Инициализация сервера");
    }

    private void executeCommand(String cmd) {
        if (cmd == null || cmd.equals(""))
            return;
        switch (cmd) {
            case "save":
                logger.info("Сохранение коллекции.");
                movieManager.saveMovies();
                break;
            case "exit":
                executeCommand("save");
                System.exit(0);
                break;
        }
    }

    public static void main(String[] args) {
        if (args.length != 0)
            Server.port = Integer.parseInt(args[0]);
        Server server;
        try {
            server = new Server(new File("movies.json"), Server.port);
        } catch (SocketException | InvalidFileException e) {
            System.out.println(e.getMessage());
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                ClientMessage receivedMessage = server.networkConnection.receiveMessage();
                ServerMessage sendingMessage;
                if (receivedMessage != null) {
                    sendingMessage = server.clientManager.handleClientMessage(receivedMessage);
                    if (sendingMessage != null) {
                        server.networkConnection.sendMessage(sendingMessage);
                    }
                }
                if (reader.ready()) {
                    server.executeCommand(reader.readLine().trim());
                }
            }
        } catch (IOException ignored) {
        }
    }
}


