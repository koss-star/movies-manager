package ru.kosstar.client.commands;

import ru.kosstar.client.IO;

public class ExitCommand extends AbstractCommand {

    @Override
    public String execute(IO io) {
        System.exit(0);
        return null;
    }
}
