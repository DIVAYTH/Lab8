package commands;

import collectionClasses.StudyGroup;
import mainCode.BDActivity;
import mainCode.CollectionManager;
import mainCode.ServerSender;

import java.nio.channels.SelectionKey;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

public class Update extends AbstractCommand {
    private CollectionManager manager;
    private BDActivity bdActivity;

    public Update(CollectionManager manager, BDActivity bdActivity) {
        this.manager = manager;
        this.bdActivity = bdActivity;
    }

    /**
     * Метод обновляет элемент по его id
     *
     * @param str
     * @param studyGroup
     * @return
     */
    @Override
    public void executeCommand(ExecutorService poolSend, SelectionKey key, String str, StudyGroup studyGroup, String login) throws NumberFormatException {
        Runnable update = () -> {
            try {
                if (studyGroup != null) {
                    if (!(manager.getCol().size() == 0)) {
                        int id = Integer.parseInt(str);
                        bdActivity.update(id, login);
                        if (manager.getCol().removeIf(col -> col.getId() == id && col.getLogin().equals(login))) {
                            studyGroup.setId(id);
                            studyGroup.setLogin(login);
                            manager.getCol().add(studyGroup);
                            poolSend.submit(new ServerSender(key, null, studyGroup));
                        } else {
                            poolSend.submit(new ServerSender(key, null, null));
                        }
                    } else {
                        poolSend.submit(new ServerSender(key, null, null));
                    }
                } else {
                    poolSend.submit(new ServerSender(key, null, null));
                }
            } catch (SQLException e) {
                poolSend.submit(new ServerSender(key, null, null));
            }
        };
        new Thread(update).start();
    }
}