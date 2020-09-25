package commands;

import mainCode.BDActivity;
import mainCode.CollectionManager;
import mainCode.ServerSender;

import java.nio.channels.SelectionKey;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

public class RemoveGreater extends AbstractCommand {
    private CollectionManager manager;
    private BDActivity bdActivity;

    public RemoveGreater(CollectionManager manager, BDActivity bdActivity) {
        this.manager = manager;
        this.bdActivity = bdActivity;
    }

    /**
     * Метод удаляет все элементы, превышающие заданный height
     *
     * @param str
     * @return
     */
    @Override
    public void executeCommand(ExecutorService poolSend, SelectionKey key, String str, String login) throws NumberFormatException {
        Runnable remove = () -> {
            if (!(manager.getCol().size() == 0)) {
                int oldSize = manager.getCol().size();
                int height = Integer.parseInt(str);
                try {
                    bdActivity.deleteByHeight(height, login);
                } catch (SQLException e) {
                    poolSend.submit(new ServerSender(key, "Ошибка при работе с БД", null));
                }
                if (manager.getCol().removeIf(col -> col.getGroupAdmin().getHeight() != null && col.getGroupAdmin().getHeight() > height && col.getLogin().equals(login))) {
                    int newSize = oldSize - manager.getCol().size();
                    poolSend.submit(new ServerSender(key, "Элементы удалены", null));
                } else {
                    poolSend.submit(new ServerSender(key, "Коллекция не изменина", null));
                }
            } else {
                poolSend.submit(new ServerSender(key, "Коллекция пуста", null));
            }
        };
        new Thread(remove).start();
    }
}