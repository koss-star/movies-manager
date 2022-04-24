package ru.kosstar.server.database;

import ru.kosstar.data.Movie;
import ru.kosstar.data.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends AbstractRepository<String, User> {
    public UserRepository(Connection connection) {
        super(connection);
    }

    private User fromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("login"),
                rs.getString("pass")
        );
    }

    private String toInsertString(User user) {
        return new StringBuilder()
                .append("'").append(user.getLogin()).append("',")
                .append("'").append(user.getPass()).append("'").toString();
    }

    @Override
    public User get(String key) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * " +
                    "from users where login = '" + key + "';");
            if (rs.next())
                return fromResultSet(rs);
            else
                return null;
        }
    }

    @Override
    public User insert(User value) throws SQLException {
        return super.insert(value);
        //int result = statement.executeUpdate("insert into mpaaratings(\"name\") values (" + value.toString());
    }
}
