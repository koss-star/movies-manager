package ru.kosstar.data;

import javafx.scene.transform.Scale;
import ru.kosstar.client.InvalidInputValueException;

import java.io.Console;
import java.io.IOException;
import java.io.Serializable;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class User implements Serializable {
    private static final Pattern loginPattern = Pattern.compile("[\\d\\w.\\-_]+");
    private static final Pattern passPattern = Pattern.compile("[^\\\\s]{5,64}");
    private String login;
    private String pass;

    public User(String login, String pass) throws InvalidInputValueException {
        List<String> errors = new ArrayList<>();
        if (!Pattern.matches(loginPattern.pattern(), login))
            errors.add("login");
        else if (!Pattern.matches(passPattern.pattern(), pass))
            errors.add("pass");
        if (errors.size() > 0)
            throw new InvalidInputValueException(errors.toArray(new String[0]));
        this.login = login;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '}';
    }

    public void copy(User user) {
        this.login = user.getLogin();
        this.pass = user.getPass();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(pass, user.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, pass);
    }
}
