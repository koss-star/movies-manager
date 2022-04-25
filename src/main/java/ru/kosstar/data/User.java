package ru.kosstar.data;

import ru.kosstar.client.IO;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String login;
    private String pass;

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public static User readUserData(IO io) {
        io.printString("Введите логин:");
        String login = io.readNotEmptyString();
        if (login == null)
            return null;
        io.printString("Введите пароль:");
        String pass = io.readNotEmptyString();
        if (pass == null) {
            return null;
        }
        return new User(login, pass);
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
