package ru.kosstar.server.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import ru.kosstar.data.*;
import ru.kosstar.server.InvalidFileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieRepository extends AbstractRepository<Integer, Movie> {
    private static final Map<Integer, Movie> cache = new HashMap<>();
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final MpaaRatingRepository mpaaRatingRepository;


    public MovieRepository(Connection connection) {
        super(connection);
        personRepository = new PersonRepository(connection);
        userRepository = new UserRepository(connection);
        movieGenreRepository = new MovieGenreRepository(connection);
        mpaaRatingRepository = new MpaaRatingRepository(connection);
        countryRepository = new CountryRepository(connection);
    }

    private Movie fromResultSet(ResultSet rs) throws SQLException {
        User user = userRepository.get(rs.getString("owner"));
        Country country = countryRepository.get(rs.getInt("country"));
        MpaaRating mpaaRating = mpaaRatingRepository.get(rs.getInt("mpaaRating"));
        MovieGenre movieGenre = movieGenreRepository.get(rs.getInt("genre"));
        Person director = personRepository.get(rs.getInt("director"));
        return new Movie(
                rs.getInt("id"),
                user,
                rs.getString("name"),
                rs.getInt("productionYear"),
                country,
                movieGenre,
                director,
                rs.getLong("budget"),
                rs.getLong("fees"),
                mpaaRating,
                rs.getInt("durationInMinutes"),
                rs.getInt("oscarsCount"),
                rs.getTimestamp("creationDate").toLocalDateTime()
        );
    }

    private String toUpdateString(Movie movie) throws SQLException {
        return new StringBuilder()
                .append("name = '").append(movie.getName()).append("',")
                .append("productionYear = ").append(movie.getProductionYear()).append(",")
                .append("country = ").append(countryRepository.getIdByName(movie.getCountry())).append(",")
                .append("genre = ").append(movieGenreRepository.getIdByName(movie.getGenre())).append(",")
                .append("director = ").append(movie.getDirector().getId()).append(",")
                .append("budget = ").append(movie.getBudget()).append(",")
                .append("fees = ").append(movie.getFees()).append(",")
                .append("mpaaRating = ").append(mpaaRatingRepository.getIdByName(movie.getMpaaRating())).append(",")
                .append("durationInMinutes = ").append(movie.getDurationInMinutes()).append(",")
                .append("oscarsCount = ").append(movie.getOscarsCount()).toString();
    }

    private String toInsertString(Movie movie) throws SQLException {
        return new StringBuilder()
                .append("default,")
                .append("'").append(movie.getOwner().getLogin()).append("',")
                .append("'").append(movie.getName()).append("',")
                .append(movie.getProductionYear()).append(",")
                .append(countryRepository.getIdByName(movie.getCountry())).append(",")
                .append(movieGenreRepository.getIdByName(movie.getGenre())).append(",")
                .append(movie.getDirector().getId()).append(",")
                .append(movie.getBudget()).append(",")
                .append(movie.getFees()).append(",")
                .append(mpaaRatingRepository.getIdByName(movie.getMpaaRating())).append(",")
                .append(movie.getDurationInMinutes()).append(",")
                .append(movie.getOscarsCount()).toString();
    }

    public List<Movie> getAll() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * " +
                    "from movies;");
            List<Movie> movies = new ArrayList<>();
            while (rs.next()) {
                Movie movie = fromResultSet(rs);
                Movie cached = cache.get(movie.getId());
                if (cached != null) {
                    cached.copy(movie);
                    movies.add(cached);
                } else {
                    cache.put(movie.getId(), movie);
                    movies.add(movie);
                }
            }
            return movies;
        }
    }

    @Override
    public void update(Movie value) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("update movies set " +
                    toUpdateString(value) + " where id = " + value.getId() + ";");
            personRepository.update(value.getDirector());
        }
    }

    @Override
    public Movie insert(Movie value) throws SQLException {
        connection.setAutoCommit(false);
        try (Statement statement = connection.createStatement()) {
            Person director = personRepository.get(value.getDirector().getId());
            if (director == null) {
                value.setDirector(personRepository.insert(value.getDirector()));
            }
            statement.executeUpdate("insert into movies values (" +
                    toInsertString(value) + ");");
            ResultSet rs = statement.executeQuery("select * from movies order by id desc limit 1");
            rs.next();
            Movie movie = fromResultSet(rs);
            cache.put(movie.getId(), movie);
            connection.commit();
            return movie;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from movies where id = " + key + ";");
            cache.remove(key);
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = new DbConnectionManager().getConnection();
        MovieRepository movieRepository = new MovieRepository(connection);
        List<Movie> movies = movieRepository.getAll();

    }
}
