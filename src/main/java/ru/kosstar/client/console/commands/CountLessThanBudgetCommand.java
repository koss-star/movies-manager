package ru.kosstar.client.console.commands;

import ru.kosstar.client.console.IO;

public class CountLessThanBudgetCommand extends AbstractCommand {

    @Override
    public Long execute(IO io) {
        io.printString("Введите число: ");
        return io.readLong(0L);
    }
}
