package ru.kosstar.client;

import ru.kosstar.connection.ServerMessage;
import ru.kosstar.data.User;


public class Authentication {
    private final NetworkConnection connection;

    public Authentication(NetworkConnection connection) {
        this.connection = connection;
    }

    public User userSignIn(User user) throws InternalClientException, InternalServerException {
        ServerMessage serverMessage = connection.signIn(user);
        if (serverMessage.isSuccess())
            return (User) serverMessage.getMessageBody();
        else
            return null;
    }

    public User userSignUp(User user) throws InternalServerException, InternalClientException {
        ServerMessage serverMessage = connection.signUp(user);
        if (serverMessage.isSuccess())
            return (User) serverMessage.getMessageBody();
        else
            return null;
    }
}
