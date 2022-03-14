package ru.kosstar.client.commands;

import ru.kosstar.client.IO;

public class SaveCommand extends AbstractCommand {

    @Override
    public String execute(IO io) {
        return io.readNotNullString();
    }
}
