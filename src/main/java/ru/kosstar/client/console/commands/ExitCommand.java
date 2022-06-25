package ru.kosstar.client.console.commands;

import ru.kosstar.client.console.IO;

public class ExitCommand extends AbstractCommand {

    @Override
    public String execute(IO io) {
        System.exit(0);
        return null;
    }
}
