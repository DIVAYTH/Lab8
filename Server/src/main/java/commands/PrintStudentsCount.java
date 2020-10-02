package commands;

import collectionClasses.StudyGroup;
import mainCode.CollectionManager;
import mainCode.ServerSender;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintStudentsCount extends AbstractCommand {
    private CollectionManager manager;

    public PrintStudentsCount(CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * Метод выводит students count в порядке возрастания
     *
     * @return
     */
    @Override
    public void executeCommand(ExecutorService poolSend, SelectionKey key) {
        Runnable print = () -> {
            if (!(manager.getCol().size() == 0)) {
                Stream<StudyGroup> stream = manager.getCol().stream();
                poolSend.submit(new ServerSender(key, stream.filter(col -> col.getStudentsCount() != null).sorted(new ComparatorByStudentCount())
                        .map(col -> "students count" + " - " + col.getStudentsCount()).collect(Collectors.joining("\n"))));
            } else {
                poolSend.submit(new ServerSender(key, "Коллекция пустая"));
            }
        };
        new Thread(print).start();
    }
}