package mainCode;

import collectionClasses.StudyGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashSet;

public class ServerSender implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServerSender.class);
    private SelectionKey key;
    private String answer;
    private StudyGroup studyGroup;

    public ServerSender(SelectionKey key, String answer, StudyGroup studyGroup) {
        this.key = key;
        this.answer = answer;
        this.studyGroup = studyGroup;
    }

    /**
     * Метод отправляет результат выполнения команды, регистрации или авторизации клиенту
     */
    public void run() {
        SocketChannel channel = (SocketChannel) key.channel();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream toClient = new ObjectOutputStream(baos)) {
            if (answer != null) {
                toClient.writeObject(answer);
            } else {
                toClient.writeObject(studyGroup);
            }
            ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            int available = channel.write(buffer);
            while (available > 0) {
                available = channel.write(buffer);
            }
            buffer.clear();
            buffer.flip();
            logger.debug("Результат отправлен клиенту");
        } catch (IOException e) {
        }
    }
}