package ru.kosstar.exceptions;

/**
 * Выбрасывается, если необходимо прервать нормальное выполнение команды
 */
public class InterruptionOfCommandException extends Exception {

    public InterruptionOfCommandException() {
        super();
    }

    public InterruptionOfCommandException(String message) {
        super(message);
    }

    public InterruptionOfCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterruptionOfCommandException(Throwable cause) {
        super(cause);
    }
}
