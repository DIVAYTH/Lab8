package commands;

import collectionClasses.StudyGroup;
import mainCode.BDActivity;
import mainCode.CollectionManager;
import mainCode.ServerSender;

import java.nio.channels.SelectionKey;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

public class AddIfMin extends AbstractCommand {
    private CollectionManager manager;
    private BDActivity bdActivity;

    public AddIfMin(CollectionManager manager, BDActivity bdActivity) {
        this.manager = manager;
        this.bdActivity = bdActivity;
    }

    /**
     * Метод добавляет элемент в коллекцию, если его height меньше минимально
     *
     * @param studyGroup
     * @return
     */
    @Override
    public void executeCommand(ExecutorService poolSend, SelectionKey key, StudyGroup studyGroup, String login) {
        Runnable addElement = () -> {
            if (!(manager.getCol().size() == 0)) {
                if (studyGroup != null) {
                    Stream<StudyGroup> stream = manager.getCol().stream();
                    Integer heightMIN = stream.filter(col -> col.getGroupAdmin().getHeight() != null)
                            .min(Comparator.comparingInt(p -> p.getGroupAdmin().getHeight())).get().getGroupAdmin().getHeight();
                    if (studyGroup.getGroupAdmin().getHeight() != null && studyGroup.getGroupAdmin().getHeight() < heightMIN) {
                        try {
                            long id = bdActivity.getSQLId();
                            bdActivity.addToSQL(studyGroup, login, id);
                            studyGroup.setId(id);
                            studyGroup.setLogin(login);
                            manager.getCol().add(studyGroup);
                            poolSend.submit(new ServerSender(key, null, studyGroup));
                        } catch (SQLException e) {
                            poolSend.submit(new ServerSender(key, null, null));
                        }
                    } else {
                        poolSend.submit(new ServerSender(key, null, null));
                    }
                } else {
                    poolSend.submit(new ServerSender(key, null, null));
                }
            } else {
                poolSend.submit(new ServerSender(key, null, null));
            }
        };
        new Thread(addElement).start();
    }
}