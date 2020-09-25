package commands;

import mainCode.BDActivity;
import mainCode.CollectionManager;
import mainCode.ServerSender;

import java.nio.channels.SelectionKey;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

public class RemoveId extends AbstractCommand {
    private CollectionManager manager;
    private BDActivity bdActivity;

    public RemoveId(CollectionManager manager, BDActivity bdActivity) {
        this.manager = manager;
        this.bdActivity = bdActivity;
    }

    /**
     * Метод удаляет элемент по его id
     *
     * @param str
     * @return
     */
    @Override
    public void executeCommand(ExecutorService poolSend, SelectionKey key, String str, String login) throws NumberFormatException {
        Runnable delete = () -> {
            if (!(manager.getCol().size() == 0)) {
                int id = Integer.parseInt(str);
                try {
                    bdActivity.deleteById(id, login);
                } catch (SQLException e) {
                    poolSend.submit(new ServerSender(key, "Ошибка при работе с БД)", null));
                }
                if (manager.getCol().removeIf(col -> col.getId() == id && col.getLogin().equals(login))) {
                    poolSend.submit(new ServerSender(key, "Элемент удален", null));
                } else
                    poolSend.submit(new ServerSender(key, "Нет элемента с таким id или пользователь не имеет доступа к этому элементу", null));
            } else {
                poolSend.submit(new ServerSender(key, "Коллекция пуста", null));
            }
        };
        new Thread(delete).start();
    }
}