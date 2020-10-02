package commands;

import collectionClasses.StudyGroup;
import mainCode.CollectionManager;
import mainCode.ServerSender;

import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Show extends AbstractCommand {
    private CollectionManager manager;

    public Show(CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * Метод выводит элементы коллекции
     *
     * @return
     */
    public void executeCommand(ExecutorService poolSend, SelectionKey key) {
        Runnable show = () -> {
            if (manager.getCol().size() != 0) {
                Stream<StudyGroup> stream = manager.getCol().stream();
                poolSend.submit(new ServerSender(key, stream.map(StudyGroup::toString).collect(Collectors.joining("\n"))));
            } else {
                poolSend.submit(new ServerSender(key, "Коллекция пуста"));
            }
        };
        new Thread(show).start();
    }
}