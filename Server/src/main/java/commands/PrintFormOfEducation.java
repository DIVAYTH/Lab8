package commands;

import collectionClasses.StudyGroup;
import mainCode.CollectionManager;
import mainCode.ServerSender;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintFormOfEducation extends AbstractCommand {
    private CollectionManager manager;

    public PrintFormOfEducation(CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * Метод выводит FormOfEducation в порядке убывания
     *
     * @return
     */
    @Override
    public void executeCommand(ExecutorService poolSend, SelectionKey key) {
        Runnable print = () -> {
            if (manager.getCol().size() != 0) {
                Stream<StudyGroup> stream = manager.getCol().stream();
                poolSend.submit(new ServerSender(key, stream.filter(col -> col.getFormOfEducation() != null).sorted(new ComparatorByFormOfEducation())
                        .map(col -> "formOfEducation" + " - " + col.getFormOfEducation()).collect(Collectors.joining("\n")), null));
            } else {
                poolSend.submit(new ServerSender(key, "Коллекция пустая", null));
            }
        };
        new Thread(print).start();
    }
}