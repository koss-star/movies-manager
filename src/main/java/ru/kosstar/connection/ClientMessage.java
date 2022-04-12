package ru.kosstar.connection;

import ru.kosstar.data.User;

import java.io.Serializable;
import java.net.SocketAddress;

public class ClientMessage implements Serializable {
    private final User user;
    private final MessageType type;
    private final Object messageBody;
    private final SocketAddress senderAddress;

    public ClientMessage(User user, MessageType type, Object messageBody, SocketAddress senderAddress) {
        this.user = user;
        this.type = type;
        this.messageBody = messageBody;
        this.senderAddress = senderAddress;
    }

    public MessageType getType() {
        return type;
    }

    public Object getMessageBody() {
        return messageBody;
    }

    public SocketAddress getSenderAddress() {
        return senderAddress;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "user=" + user +
                ", type=" + type +
                ", messageBody=" + messageBody +
                ", senderAddress=" + senderAddress +
                '}';
    }
}
