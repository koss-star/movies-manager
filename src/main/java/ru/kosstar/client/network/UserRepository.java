package ru.kosstar.client.network;

import ru.kosstar.client.exceptions.InternalClientException;
import ru.kosstar.client.exceptions.InternalServerException;
import ru.kosstar.connection.ServerMessage;
import ru.kosstar.data.User;


public class UserRepository {
    private static final NetworkConnection connection = NetworkConnection.getInstance();

    public static User userSignIn(User user) throws InternalClientException, InternalServerException {
        ServerMessage serverMessage = connection.signIn(user);
        if (serverMessage.isSuccess())
            return (User) serverMessage.getMessageBody();
        else
            return null;
    }

    public static User userSignUp(User user) throws InternalServerException, InternalClientException {
        ServerMessage serverMessage = connection.signUp(user);
        if (serverMessage.isSuccess())
            return (User) serverMessage.getMessageBody();
        else
            return null;
    }
}
