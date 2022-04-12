package ru.kosstar.connection;

import java.io.Serializable;

/**
 * Типы запроса от клиента -- серверу
 */
public enum MessageType implements Serializable {
    SIGN_UP,
    SIGN_IN,
    COMMAND
}
