package commands;

import collectionClasses.StudyGroup;
import mainCode.BDActivity;
import mainCode.CollectionManager;
import mainCode.ServerSender;

import java.nio.channels.SelectionKey;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

public class Add extends AbstractCommand {
    private CollectionManager manager;
    private BDActivity bdActivity;

    public Add(CollectionManager manager, BDActivity bdActivity) {
        this.manager = manager;
        this.bdActivity = bdActivity;
    }

    /**
     * Метод добавляет элемент в коллекцию
     *
     * @param studyGroup
     * @return
     */
    @Override
    public void executeCommand(ExecutorService poolSend, SelectionKey key, StudyGroup studyGroup, String login) {
        Runnable addElement = () -> {
            try {
                if (studyGroup != null) {
                    long id = bdActivity.getSQLId();
                    bdActivity.addToSQL(studyGroup, login, id);
                    studyGroup.setId(id);
                    studyGroup.setLogin(login);
                    manager.getCol().add(studyGroup);
                    poolSend.submit(new ServerSender(key, null, studyGroup));
                } else {
                    poolSend.submit(new ServerSender(key, null, null));
                }
            } catch (SQLException e) {
                poolSend.submit(new ServerSender(key, null, null));
            }
        };
        new Thread(addElement).start();
    }
}