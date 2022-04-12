package ru.kosstar.client.commands;

import ru.kosstar.client.IO;

public class RemoveLowerKeyCommand extends AbstractCommand {

    @Override
    public Integer execute(IO io) {
        io.printString("Введите id фильма: ");
        return io.readInt(0);
    }
}
