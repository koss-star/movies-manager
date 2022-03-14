package ru.kosstar.client.commands;

import ru.kosstar.client.IO;
import ru.kosstar.data.Movie;

public class InsertCommand extends AbstractCommand {

    @Override
    public Movie execute(IO io) {
        return io.readMovie();
    }
}
