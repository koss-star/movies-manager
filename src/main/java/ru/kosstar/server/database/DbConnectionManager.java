package ru.kosstar.server.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kosstar.server.Server;

import javax.sql.DataSource;
import java.io.*;
import java.net.ConnectException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class DbConnectionManager {
    private static final Logger logger = LogManager.getLogger(DbConnectionManager.class);
    private String url = null;
    private String login = null;
    private String pass = null;

    public DbConnectionManager() {
        Properties properties = new Properties();
        try (InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("database.properties")) {
            properties.load(input);
            url = properties.getProperty("jdbc.url");
            login = properties.getProperty("jdbc.login");
            pass = properties.getProperty("jdbc.pass");
        } catch (IOException e) {
            logger.error("Не удалось прочитать database.properties", e);
        }
    }

    public Connection getConnection() throws SQLException {
        logger.info("Открыто новое соединение с бд(" + url + ", " + login + ")");
        return DriverManager.getConnection(url, login, pass);
    }

    public static void main(String[] args) throws SQLException {
        DbConnectionManager manager = new DbConnectionManager();
        try (Connection connection = manager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from countries");
            while (rs.next()) {
                System.out.print(rs.getString("id") + ", ");
                System.out.println(rs.getString("name"));
            }
        }
    }
}
