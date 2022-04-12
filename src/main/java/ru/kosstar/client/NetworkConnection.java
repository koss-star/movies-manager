package ru.kosstar.client;

import ru.kosstar.connection.ClientMessage;
import ru.kosstar.connection.CommandWithArgument;
import ru.kosstar.connection.MessageType;
import ru.kosstar.connection.ServerMessage;
import ru.kosstar.data.User;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class NetworkConnection {
    private final DatagramSocket socket;
    private final InetSocketAddress remoteAddr;
    private final ByteBuffer buffer = ByteBuffer.allocate(4 * 1024 * 1024);
    private final SocketAddress localAddr;

    public NetworkConnection(InetSocketAddress remoteAddr) throws IOException {
        this.remoteAddr = remoteAddr;
        socket = new DatagramSocket();
        localAddr = new InetSocketAddress(InetAddress.getLocalHost(), socket.getLocalPort());
        socket.setSoTimeout(10000);
    }

    private boolean sendMessage(User user, MessageType type, Object object) {
        ClientMessage message = new ClientMessage(user, type, object, localAddr);
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
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }

    public ServerMessage sendCommand(User user, String commandName, Object commandArg) {
        if (!sendMessage(user, MessageType.COMMAND, new CommandWithArgument(commandName, commandArg)))
            return new ServerMessage("Не удалось отправить сообщение.", true, null);
        else
            return receiveMessage();
    }

    public boolean signIn(User user) {
        sendMessage(null, MessageType.SIGN_IN, user);
        return receiveMessage().isSuccess();
    }

    public boolean signUp(User user) {
        sendMessage(null, MessageType.SIGN_UP, user);
        return receiveMessage().isSuccess();
    }

    public ServerMessage receiveMessage() {
        try {
            buffer.clear();
            DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.capacity(), remoteAddr);
            socket.receive(packet);
            buffer.limit(packet.getLength());
            try (ObjectInputStream objIn = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.array(), 0, buffer.limit())
            )) {
                return (ServerMessage) objIn.readObject();
            }
        } catch (SocketTimeoutException e) {
            return new ServerMessage("Сервер недоступен.", true, null);
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            e.printStackTrace();
            return new ServerMessage("Произошла внутренняя ошибка приложения.", true, null);
        }
    }


}
