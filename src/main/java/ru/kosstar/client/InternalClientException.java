package ru.kosstar.client;

public class InternalClientException extends Exception {

    public InternalClientException() {
        super();
    }

    public InternalClientException(String message) {
        super(message);
    }

    public InternalClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalClientException(Throwable cause) {
        super(cause);
    }

}
