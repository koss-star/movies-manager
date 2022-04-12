package ru.kosstar.connection;

import java.io.Serializable;
import java.net.SocketAddress;

public class ServerMessage implements Serializable {
    private final Object messageBody;
    // TODO ошибки бывают разные: ошибка сервера, неправильный запрос от клиента,
    //  ошибка при выполнении команды (ErrorType)
    private final SocketAddress destination;
    private final boolean isError;

    public ServerMessage(Object messageBody, boolean isError, SocketAddress destination) {
        this.messageBody = messageBody;
        this.isError = isError;
        this.destination = destination;
    }

    public ServerMessage(Object messageBody, SocketAddress destination) {
        this(messageBody, false, destination);
    }

    public ServerMessage(boolean isError, SocketAddress destination) {
        this(null, isError, destination);
    }

    public boolean isError() {
        return isError;
    }

    public boolean isSuccess() {
        return !isError;
    }

    public Object getMessageBody() {
        return messageBody;
    }

    public SocketAddress getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "ServerMessage{" +
                "messageBody=" + messageBody +
                ", destination=" + destination +
                ", isError=" + isError +
                '}';
    }
}
