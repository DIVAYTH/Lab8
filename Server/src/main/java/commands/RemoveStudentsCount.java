package commands;

import mainCode.BDActivity;
import mainCode.CollectionManager;
import mainCode.ServerSender;

import java.nio.channels.SelectionKey;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

public class RemoveStudentsCount extends AbstractCommand {
    private CollectionManager manager;
    private BDActivity bdActivity;

    public RemoveStudentsCount(CollectionManager manager, BDActivity bdActivity) {
        this.manager = manager;
        this.bdActivity = bdActivity;
    }

    /**
     * Метод удаляет эемент по его student count
     *
     * @param str
     * @return
     */
    @Override
    public void executeCommand(ExecutorService poolSend, SelectionKey key, String str, String login) throws NumberFormatException {
        Runnable delete = () -> {
            if (!(manager.getCol().size() == 0)) {
                int studentsCount = Integer.parseInt(str);
                try {
                    bdActivity.deleteByStudentsCount(studentsCount, login);
                } catch (SQLException e) {
                    poolSend.submit(new ServerSender(key, "Ошибка при работе с БД"));
                }
                if (manager.getCol().removeIf(col -> col.getStudentsCount() != null && col.getStudentsCount() == studentsCount && col.getLogin().equals(login))) {
                    poolSend.submit(new ServerSender(key, "Элемент удален"));
                } else
                    poolSend.submit(new ServerSender(key, "Нет элемента с таким student_count или пользователь не имеет доступа к этому элементу"));
            } else {
                poolSend.submit(new ServerSender(key, "Коллекция пуста"));
            }
        };
        new Thread(delete).start();
    }
}