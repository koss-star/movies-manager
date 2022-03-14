package ru.kosstar.client.commands;

import ru.kosstar.client.IO;

public class RemoveLowerCommand extends AbstractCommand {

    @Override
    public String execute(IO io) {
        io.printString("Введите название фильма: ");
        return io.readString();
    }
}
