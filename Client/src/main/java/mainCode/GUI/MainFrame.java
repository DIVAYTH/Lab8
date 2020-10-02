package mainCode.GUI;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

public class MainFrame implements Runnable {
    private GUI gui;
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JTable jTable = new JTable(tableModel);
    private RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
    private JFrame mainFrame = new JFrame("Lab 8");
    private JLabel user = new JLabel("");

    public MainFrame(GUI gui) {
        this.gui = gui;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JLabel getUser() {
        return user;
    }

    /**
     * Метод создает основной фрейм
     */
    public void createMainFrame() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());
        JPanel headerBag = new JPanel();
        headerBag.setLayout(new GridBagLayout());
        JPanel upperHeader = new JPanel();
        upperHeader.setLayout(new GridBagLayout());
        JButton exit = new JButton(gui.getBundle().getString("exit"));
        JComboBox<String> languages = new JComboBox<>(new String[]{"русский", "Íslensk", "Hrvatski", "Español (Colombia)"});
        user.setText(gui.getAuthorization().getLogin());

        upperHeader.add(languages, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        upperHeader.add(user, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(4, 0, 0, 0), 0, 0));
        upperHeader.add(exit, new GridBagConstraints(2, 0, 1, 1, 0.09, 1.0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        JPanel header = new JPanel();
        header.setLayout(new GridLayout(2, 0, 5, 5));
        JPanel lowerHeader = new JPanel();
        lowerHeader.setLayout(new GridLayout(2, 0, 5, 5));
        String[] commands = {gui.getBundle().getString("clear"), gui.getBundle().getString("info"), gui.getBundle().getString("help"), gui.getBundle().getString("removeStudentsCount"),
                gui.getBundle().getString("printStudentsCount"), gui.getBundle().getString("printFormOfEducation"), gui.getBundle().getString("removeId"), gui.getBundle().getString("add"),
                gui.getBundle().getString("removeGreater"), gui.getBundle().getString("addIfMin"), gui.getBundle().getString("addIfMax"), gui.getBundle().getString("update")};
        String[] commandsAction = {"clear", "info", "help", "remove_any_by_students_count", "print_field_ascending_students_count",
                "print_field_descending_form_of_education", "remove_by_id", "add", "remove_greater", "add_if_min", "add_if_max", "update"};

        for (int i = 0; i < commands.length; i++) {
            JButton button = new JButton(commands[i]);
            button.setActionCommand(commandsAction[i]);
            lowerHeader.add(button);
            button.addActionListener(e -> {
                try {
                    switch (button.getActionCommand()) {
                        case "clear":
                            gui.getResult().setText((String) gui.getClient().handler(button.getActionCommand(), null, null));
                            break;
                        case "help":
                        case "info":
                        case "print_field_ascending_students_count":
                        case "print_field_descending_form_of_education":
                            gui.getResult().setText((String) gui.getClient().handler(button.getActionCommand(), null, null));
                            break;
                        case "add_if_max":
                        case "add_if_min":
                        case "add":
                        case "update":
                            gui.getAdd().createAddFrame(button.getActionCommand());
                            break;
                        case "remove_greater":
                        case "remove_by_id":
                        case "remove_any_by_students_count":
                            gui.getRemove().createRemoveFrame(button.getActionCommand());
                            break;
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    gui.getResult().setText(gui.getBundle().getString("serverEx"));
                } catch (IndexOutOfBoundsException ex) {
                }
            });
        }

        header.add(upperHeader);
        header.add(lowerHeader);
        headerBag.add(header, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
        mainFrame.add(headerBag, BorderLayout.NORTH);

        String[] tableCol = new String[]{"Id", gui.getBundle().getString("groupName"), "X", "Y", gui.getBundle().getString("creationDate"), gui.getBundle().getString("studentsCount")
                , gui.getBundle().getString("formOfEducation"), gui.getBundle().getString("semester"), gui.getBundle().getString("perName"), gui.getBundle().getString("height"),
                gui.getBundle().getString("hairColor"), gui.getBundle().getString("country"), "locX", "locY", "locz", gui.getBundle().getString("login")};
        for (String s : tableCol) {
            tableModel.addColumn(s);
        }
        JPanel table = new JPanel();
        table.setLayout(new GridBagLayout());
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab(gui.getBundle().getString("table"), new JScrollPane(jTable));
        jTabbedPane.addTab(gui.getBundle().getString("visualisation"), new JScrollPane(gui.getGraphicsPanel()));
        jTable.setRowSorter(sorter);
        JPanel textArea = new JPanel();
        textArea.setLayout(new GridBagLayout());
        gui.getResult().setWrapStyleWord(true);
        gui.getResult().setWrapStyleWord(true);
        gui.getResult().setEditable(false);

        JButton script = new JButton(gui.getBundle().getString("executeScript"));

        table.add(jTabbedPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 10, 5), 0, 0));
        textArea.add(new JScrollPane(gui.getResult()), new GridBagConstraints(0, 0, 1, 2, 1.0, 0.97,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 10, 0, 5), 0, 0));
        script.setActionCommand("execute_script");
        textArea.add(script, new GridBagConstraints(0, 4, 1, 1, 1.0, 0.01,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 5), 0, 0));
        table.add(textArea, new GridBagConstraints(1, 0, 1, 1, 0.5, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 10, 10), 0, 0));

        mainFrame.add(table);
        mainFrame.setLocationRelativeTo(null);

        exit.addActionListener(e -> {
            mainFrame.setVisible(false);
            gui.getAuthorization().getFrame().setVisible(true);
            gui.getResult().setText("");
        });

        script.addActionListener(e -> {
            try {
                JFileChooser file = new JFileChooser();
                JButton open = new JButton();
                file.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if (file.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
                }
                gui.getResult().setText((String) gui.getClient().handler(script.getActionCommand(), null, file.getSelectedFile().getAbsolutePath()));
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                gui.getResult().setText(gui.getBundle().getString("serverEx"));
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        });

        languages.addActionListener(e -> {
            gui.choseLanguage(languages);
            exit.setText(gui.getBundle().getString("exit"));
            String[] commandsLanguage = {gui.getBundle().getString("clear"), gui.getBundle().getString("info"), gui.getBundle().getString("help"), gui.getBundle().getString("removeStudentsCount"),
                    gui.getBundle().getString("printStudentsCount"), gui.getBundle().getString("printFormOfEducation"), gui.getBundle().getString("removeId"), gui.getBundle().getString("add"),
                    gui.getBundle().getString("removeGreater"), gui.getBundle().getString("addIfMin"), gui.getBundle().getString("addIfMax"), gui.getBundle().getString("update")};
            for (int i = 0; i < commandsLanguage.length; i++) {
                ((JButton) lowerHeader.getComponent(i)).setText(commandsLanguage[i]);
            }
            String[] tableLanguage = new String[]{"Id", gui.getBundle().getString("groupName"), "X", "Y", gui.getBundle().getString("creationDate"), gui.getBundle().getString("studentsCount")
                    , gui.getBundle().getString("formOfEducation"), gui.getBundle().getString("semester"), gui.getBundle().getString("perName"), gui.getBundle().getString("height"),
                    gui.getBundle().getString("hairColor"), gui.getBundle().getString("country"), "locX", "locY", "locz", gui.getBundle().getString("login")};
            Enumeration<TableColumn> enumeration = jTable.getColumnModel().getColumns();
            for (int i = 0; i < tableLanguage.length; i++) {
                enumeration.nextElement().setHeaderValue(tableLanguage[i]);
            }
            jTabbedPane.setTitleAt(0, gui.getBundle().getString("table"));
            jTabbedPane.setTitleAt(1, gui.getBundle().getString("visualisation"));
            script.setText(gui.getBundle().getString("executeScript"));
            gui.getResult().setText("");
        });
        mainFrame.setVisible(true);
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Метод загружает элементы
     */
    @Override
    public void run() {
        try {
            String answer;
            HashMap<String, AcademicHat> elementsServer = new HashMap<>();
            do {
                String condition = (String) gui.getClient().handler("show", null, null);
                if (!condition.equals("Коллекция пуста")) {
                    Scanner scanner = new Scanner(condition);
                    elementsServer.clear();
                    do {
                        String elements = scanner.nextLine();
                        String[] arguments = elements.split(", ");
                        tableModel.insertRow(0, arguments);
                        if (!gui.getGraphicsPanel().getColors().containsKey(arguments[15])) {
                            float[] rgb = gui.getGraphicsPanel().setColor();
                            gui.getGraphicsPanel().getColors().put(arguments[15], new Color(rgb[0], rgb[1], rgb[2]));
                        }
                        AcademicHat academicHat = new AcademicHat(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5],
                                arguments[6], arguments[7], arguments[8], arguments[9], arguments[10], arguments[11], arguments[12], arguments[13],
                                arguments[14], arguments[15]);
                        elementsServer.put(arguments[0], academicHat);
                    } while (scanner.hasNextLine());
                    gui.getGraphicsPanel().udateElement(elementsServer);
                    do {
                        answer = (String) gui.getClient().handler("show", null, null);
                        Thread.sleep(1000);
                    } while (answer.equals(condition));
                    for (int i = gui.getMain().getTableModel().getRowCount() - 1; i > -1; i--) {
                        gui.getMain().getTableModel().removeRow(i);
                    }
                } else {
                    elementsServer.clear();
                    gui.getGraphicsPanel().udateElement(elementsServer);
                    Thread.sleep(1000);
                }
            } while (true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            gui.getResult().setText(gui.getBundle().getString("serverEx"));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}