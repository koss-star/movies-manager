package ru.kosstar.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kosstar.connection.ClientMessage;
import ru.kosstar.connection.CommandWithArgument;
import ru.kosstar.connection.ServerMessage;
import ru.kosstar.data.User;

import java.net.SocketAddress;

/**
 * Модуль обработки запросов клиента {@link ClientMessage} и подготовки ответов сервера {@link ServerMessage}.
 */
public class ClientManager {
    private static final Logger logger = LogManager.getLogger(ClientManager.class);
    private final MovieManager movieManager;

    /**
     * @param movieManager менеджер для управления коллекцией фильмов
     */
    public ClientManager(MovieManager movieManager) {
        this.movieManager = movieManager;
    }

    /*
        Обработка запросов, связанных с непосредственным выполнением команд над коллекцией.
        throws FailedCommandExecutionException, если выполнение команды завершилось неуспешно.
     */
    private Object executeCommand(User user, CommandWithArgument commandWithArgument)
            throws FailedCommandExecutionException,
            UnsupportedOperationException,
            IllegalArgumentException {
        return movieManager.executeCommand(commandWithArgument);
    }

    /**
     * Обработчик клиентского сообщения.
     *
     * @param receivedMessage сообщение клиента
     * @return ответ сервера на запрос клиента
     */
    public ServerMessage handleClientMessage(ClientMessage receivedMessage) {
        ServerMessage sendingMessage;
        Object messageBody = receivedMessage.getMessageBody();
        SocketAddress destination = receivedMessage.getSenderAddress();
        try {
            switch (receivedMessage.getType()) {
                case SIGN_IN:
                    // TODO аутентификация
                    logger.info("Запрос на аутентификацию от пользователя: " + messageBody);
                    sendingMessage = new ServerMessage(false, destination);
                    break;
                case SIGN_UP:
                    // TODO регистрация
                    logger.info("Запрос на регистрацию от пользователя: " + messageBody);
                    sendingMessage = new ServerMessage(false, destination);
                    break;
                case COMMAND:
                    try {
                        sendingMessage = new ServerMessage(
                                executeCommand(
                                        receivedMessage.getUser(),
                                        (CommandWithArgument) messageBody
                                ),
                                destination
                        );
                    } catch (FailedCommandExecutionException e) {
                        sendingMessage = new ServerMessage(e.getMessage(), true, destination);
                    } catch (IllegalArgumentException | UnsupportedOperationException e) {
                        logger.warn("Получен некорректный запрос на выполнение команды: " + messageBody);
                        sendingMessage = new ServerMessage("Некорректный запрос", true, destination);
                    }
                    break;
                default:
                    logger.warn("Необработанный тип запроса: " + receivedMessage);
                    sendingMessage = new ServerMessage(true, destination);
                    break;
            }
            logger.info("Запрос выполнен, сформированный ответ: " + sendingMessage);
        } catch (ClassCastException e) {
            logger.warn("Получен некорректный запрос на выполнение команды: " + messageBody);
            sendingMessage = new ServerMessage("Некорректный запрос", true, destination);
        }
        return sendingMessage;
    }

}
