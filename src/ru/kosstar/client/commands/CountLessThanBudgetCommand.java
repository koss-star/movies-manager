package ru.kosstar.client.commands;

import ru.kosstar.client.IO;

public class CountLessThanBudgetCommand extends AbstractCommand {

    @Override
    public Long execute(IO io) {
        io.printString("Введите число: ");
        return io.readLong(0L);
    }
}
