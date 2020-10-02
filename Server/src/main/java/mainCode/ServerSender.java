package mainCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ServerSender implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServerSender.class);
    private SelectionKey key;
    private String serverAnswer;

    public ServerSender(SelectionKey key, String serverAnswer) {
        this.key = key;
        this.serverAnswer = serverAnswer;
    }

    /**
     * Метод отправляет результат выполнения команды, регистрации или авторизации клиенту
     */
    public void run() {
        SocketChannel channel = (SocketChannel) key.channel();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream toClient = new ObjectOutputStream(baos)) {
            toClient.writeObject(serverAnswer);
            ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            int available = channel.write(buffer);
            while (available > 0) {
                available = channel.write(buffer);
            }
            buffer.clear();
            logger.debug("Результат отправлен клиенту");
            key.cancel();
        } catch (IOException e) {
            // Исключение не мешает логике исполнения программы
        }
    }
}