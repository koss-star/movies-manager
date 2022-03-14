package ru.kosstar.client.commands;

import ru.kosstar.client.IO;
import ru.kosstar.data.MpaaRating;

public class CountGreaterThanMpaaRatingCommand extends AbstractCommand {

    @Override
    public MpaaRating execute(IO io) {
        return io.readEnum("MPAA рейтинг", MpaaRating.values(), MpaaRating.class, MpaaRating.G);
    }
}
