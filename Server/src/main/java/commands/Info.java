package commands;

import mainCode.CollectionManager;
import mainCode.ServerSender;

import java.nio.channels.SelectionKey;
import java.util.Date;
import java.util.concurrent.ExecutorService;

public class Info extends AbstractCommand {
    private Date initDate = new Date();
    private CollectionManager manager;

    public Info(CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * Метод показывает информацию о коллекции
     *
     * @return
     */
    @Override
    public void executeCommand(ExecutorService poolSend, SelectionKey key) {
        Runnable info = () ->
                poolSend.submit(new ServerSender(key, "PriorityQueue\n" +
                        initDate + "\n" +
                        manager.getCol().size(), null));
        new Thread(info).start();
    }
}
