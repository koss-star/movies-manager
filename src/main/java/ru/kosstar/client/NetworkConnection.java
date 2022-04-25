package ru.kosstar.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kosstar.connection.*;
import ru.kosstar.data.User;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class NetworkConnection {
    private static final Logger logger = LogManager.getLogger("ClientLogger");
    private final DatagramSocket socket;
    private final InetSocketAddress remoteAddr;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
    private final SocketAddress localAddr;

    public NetworkConnection(InetSocketAddress remoteAddr) throws IOException {
        this.remoteAddr = remoteAddr;
        socket = new DatagramSocket();
        localAddr = new InetSocketAddress(InetAddress.getLocalHost(), socket.getLocalPort());
        socket.setSoTimeout(10000);
    }

    private void sendMessage(User user, RequestType type, Object object) throws InternalClientException {
        ClientMessage message = new ClientMessage(user, type, object, localAddr);
        logger.info("Попытка отправить сообщение серверу " + type);
        try {
            try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream(buffer.capacity());
                 ObjectOutputStream objOut = new ObjectOutputStream(byteOut)) {
                objOut.writeObject(message);
                objOut.flush();
                buffer.clear();
                buffer.put(byteOut.toByteArray());
                buffer.flip();
                DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.limit(), remoteAddr);
                socket.send(packet);
            }
        } catch (IOException e) {
            logger.error("Не удалось отправить сообщение", e);
            throw new InternalClientException(e);
        }
    }

    public ServerMessage sendCommand(User user, String commandName, Object commandArg) throws InternalClientException {
        sendMessage(user, RequestType.COMMAND, new CommandWithArgument(commandName, commandArg));
        return receiveMessage();
    }

    public ServerMessage signIn(User user) throws InternalClientException {
        sendMessage(null, RequestType.SIGN_IN, user);
        return receiveMessage();
    }

    public ServerMessage signUp(User user) throws InternalClientException {
        sendMessage(null, RequestType.SIGN_UP, user);
        return receiveMessage();
    }

    public ServerMessage receiveMessage() throws InternalClientException {
        try {
            buffer.clear();
            DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.capacity(), remoteAddr);
            socket.receive(packet);
            buffer.limit(packet.getLength());
            try (ObjectInputStream objIn = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.array(), 0, buffer.limit())
            )) {
                ServerMessage received = (ServerMessage) objIn.readObject();
                logger.info("Получил сообщение от сервера " + received.getType());
                return received;
            }
        } catch (SocketTimeoutException e) {
            return new ServerMessage("Сервер недоступен", ResponseType.NOT_AVAILABLE, null);
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            logger.error("Ошибка при получении сообщения от сервера", e);
            throw new InternalClientException();
        }
    }


}
