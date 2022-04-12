package ru.kosstar.client.commands;

import ru.kosstar.client.IO;
import ru.kosstar.data.Movie;

public class UpdateCommand extends AbstractCommand {

    @Override
    public Object execute(IO io) {
        io.printString("Введите id фильма: ");
        int id = io.readInt();
        Movie movie = io.readMovie();
        movie.setId(id);
        return movie;
    }
}
