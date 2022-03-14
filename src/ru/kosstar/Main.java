package ru.kosstar;

import ru.kosstar.client.Client;
import ru.kosstar.exceptions.InterruptionOfCommandException;
import ru.kosstar.exceptions.InvalidFileException;
import ru.kosstar.server.Server;

import java.io.File;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {
        Client client = new Client();
        File file = client.readFileName();
        Server server;
        try {
            server = new Server(file);
        } catch (InvalidFileException e) {
            if (e.getMessage() != null)
                client.printString(e.getMessage());
            return;
        }
        while (true) {
            try {
                String commandName = client.readCommand();
                if (commandName == null) {
                    return;
                }
                Object commandArg = client.readCommandArg(commandName);
                String commandResult = server.executeCommand(new CommandWithArgument(commandName, commandArg));
                client.printString(commandResult);
            } catch (InterruptionOfCommandException e) {
                if (!e.getMessage().equals(""))
                    client.printString(e.getMessage());
                client.printString("Команда прервана.");
            }
        }
    }
}
