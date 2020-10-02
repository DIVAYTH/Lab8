package commands;

import mainCode.BDActivity;
import mainCode.CollectionManager;
import mainCode.ServerSender;

import java.nio.channels.SelectionKey;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

public class Clear extends AbstractCommand {
    private CollectionManager manager;
    private BDActivity bdActivity;

    public Clear(CollectionManager manager, BDActivity bdActivity) {
        this.manager = manager;
        this.bdActivity = bdActivity;
    }

    /**
     * Метод очищает коллекцию
     *
     * @return
     */
    @Override
    public void executeCommand(ExecutorService poolSend, SelectionKey key, String login) {
        Runnable clear = () -> {
            try {
                bdActivity.clearSQL(login);
                if (manager.getCol().removeIf(col -> col.getLogin().equals(login))) {
                    poolSend.submit(new ServerSender(key, "Коллекция очищена. Удалены все принадлежащие вам элементы"));
                } else {
                    poolSend.submit(new ServerSender(key, "В коллекции нет элементов принадлежащих пользователю"));
                }
            } catch (SQLException e) {
                poolSend.submit(new ServerSender(key, "Ошибка при работе с БД"));
            }
        };
        new Thread(clear).start();
    }
}