package ru.kosstar.client.console.commands;

import ru.kosstar.client.console.IO;
import ru.kosstar.data.Movie;

public class InsertCommand extends AbstractCommand {

    @Override
    public Movie execute(IO io) {
        return io.readMovie();
    }
}
