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

public class AddIfMax extends AbstractCommand {
    private CollectionManager manager;
    private BDActivity bdActivity;

    public AddIfMax(CollectionManager manager, BDActivity bdActivity) {
        this.manager = manager;
        this.bdActivity = bdActivity;
    }

    /**
     * Метод добавляет элемент в коллекцию, если его height больше максимального
     *
     * @param studyGroup
     * @param login
     * @return
     */
    @Override
    public void executeCommand(ExecutorService poolSend, SelectionKey key, StudyGroup studyGroup, String login) {
        Runnable addElement = () -> {
            if (!(manager.getCol().size() == 0)) {
                if (studyGroup != null) {
                    Stream<StudyGroup> stream = manager.getCol().stream();
                    Integer heightMAX = stream.filter(col -> col.getGroupAdmin().getHeight() != null)
                            .max(Comparator.comparingInt(p -> p.getGroupAdmin().getHeight())).get().getGroupAdmin().getHeight();
                    if (studyGroup.getGroupAdmin().getHeight() != null && studyGroup.getGroupAdmin().getHeight() > heightMAX) {
                        try {
                            long id = bdActivity.getSQLId();
                            bdActivity.addToSQL(studyGroup, login, id);
                            studyGroup.setId(id);
                            studyGroup.setLogin(login);
                            manager.getCol().add(studyGroup);
                            poolSend.submit(new ServerSender(key, "Элемент добавлен"));
                        } catch (SQLException e) {
                            poolSend.submit(new ServerSender(key, "Ошибка при работе с БД"));
                        }
                    } else {
                        poolSend.submit(new ServerSender(key, "Элемент коллекции не сохранен"));
                    }
                } else {
                    poolSend.submit(new ServerSender(key, "Данные введены неверно"));
                }
            } else {
                poolSend.submit(new ServerSender(key, "Коллекция пуста"));
            }
        };
        new Thread(addElement).start();
    }
}