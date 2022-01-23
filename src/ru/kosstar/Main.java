package ru.kosstar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import ru.kosstar.data.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//        String fileName = System.getenv("MOVIE_MANAGER_FILE");
        String fileName = "C:\\Users\\yanak\\OneDrive\\Рабочий стол\\Программирование\\lab5\\movies.json";
        if (fileName == null) {
            System.out.println("Вы не указали файл с коллекцией.");
            return;
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Map<Integer, Movie> hashtable = new Hashtable<>();
        /*Person frank = new Person("Frank Darabont", 1.83, Country.FRANCE, 60, Color.BROWN,
                Color.WHITE);
        Person peter = new Person("Peter Jackson", 1.65, Country.NEW_ZEALAND, 163,
                Color.BROWN, Color.BROWN);
        Movie movie1 = new Movie("The Green Mile", 1999, Country.USA, MovieGenre.DRAMA,
                frank, 60000000, 286801374, MpaaRating.R, 189, 0);
        Movie movie2 = new Movie("The Shawshank Redemption", 1994, Country.USA, MovieGenre.DRAMA,
                frank, 25000000, 28418687, MpaaRating.R, 142, 0);
        Movie movie3 = new Movie("The Lord of the Rings: The Return of the King", 2003,
                Country.NEW_ZEALAND, MovieGenre.FANTASY, peter, 94000000, 1140682011, MpaaRating.PG_13,
                201, 11);
        Movie movie4 = new Movie("The Lord of the Rings: The Two Towers", 2002,
                Country.NEW_ZEALAND, MovieGenre.FANTASY, peter, 94000000, 936689735, MpaaRating.PG,
                179, 2);
        Movie movie5 = new Movie("The Lord of the Rings: The Fellowship of the Ring", 2001,
                Country.NEW_ZEALAND, MovieGenre.FANTASY, peter, 93000000, 880839846, MpaaRating.PG_13,
                178, 4);

        Movie[] movies = new Movie[]{
                movie1, movie2, movie3, movie4, movie5
        };


        String json = gson.toJson(movies);

        try {
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName))) {
                writer.write(json);
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка записи.");
        }*/

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String collectionJson = reader.lines().collect(Collectors.joining());
                Movie[] movies = gson.fromJson(collectionJson, Movie[].class);
                for (Movie movie : movies) {
                    hashtable.put(movie.getId(), movie);
                }
                System.out.println(hashtable.keySet());
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка чтения из файла.");
        }
//        System.out.println(hashtable.values().stream().mapToLong(Movie::getOscarsCount).average().getAsDouble());

    }
}


