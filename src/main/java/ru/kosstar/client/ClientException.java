package ru.kosstar.client;

import java.net.SocketException;

public class ClientException extends SocketException {

    public ClientException() {
        super();
    }

    public ClientException(String message) {
        super(message);
    }
}
