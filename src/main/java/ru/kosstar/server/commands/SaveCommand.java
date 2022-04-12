package ru.kosstar.server.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.kosstar.server.MovieManager;

import java.io.*;

/**
 * Класс, описывающий команду, которая сохраняет коллекцию фильмов в файл
 */
public class SaveCommand extends AbstractCommand<String, String> {

    public SaveCommand(String name, String description, MovieManager movieManager) {
        super(name, description, movieManager);
    }

    @Override
    public String executeWithArg(String argument) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(argument))) {
                String jsonMovies = gson.toJson(movieManager.getMovies().values());
                writer.write(jsonMovies);
            }
        } catch (IOException e) {
            return "Произошла ошибка записи.";
        }
        return "Коллекция успешно сохранена.";
    }
}
