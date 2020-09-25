package mainCode.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RemoveFrame {
    private GUI gui;

    public RemoveFrame(GUI gui) {
        this.gui = gui;
    }

    /**
     * Метод создает фрейм для удаления элементов
     *
     * @param command
     */
    public void createRemoveFrame(String command) {
        String arg1 = "";
        JFrame removeFrame = new JFrame("Lab 8");
        removeFrame.setLayout(new GridBagLayout());
        removeFrame.setSize(200, 130);
        removeFrame.setDefaultCloseOperation(removeFrame.DISPOSE_ON_CLOSE);
        removeFrame.setResizable(false);
        removeFrame.setLocationRelativeTo(null);
        if (command.equals("remove_by_id"))
            arg1 = "id: ";
        if (command.equals("remove_any_by_students_count"))
            arg1 = gui.getBundle().getString("studentsCount");
        if (command.equals("remove_greater"))
            arg1 = gui.getBundle().getString("height");

        JTextField arg2 = new JTextField();
        JButton buttonAuth = new JButton(gui.getBundle().getString("submit"));

        removeFrame.add(new JLabel(arg1), new GridBagConstraints(0, 0, 1, 1, 1.0, 0.1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 30, 0, 0), 0, 0));
        removeFrame.add(arg2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(10, 30, 0, 30), 0, 0));
        removeFrame.add(buttonAuth, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 30, 0, 0), 0, 0));

        buttonAuth.addActionListener(e -> {
            try {
                String result = (String) gui.getClient().handler(command, null, arg2.getText());
                if (result.equals("Элемент удален") || result.equals("Элементы удалены")) {
                    gui.getResult().setText(gui.getBundle().getString("removeWell"));
                } else {
                    gui.getResult().setText(gui.getBundle().getString("removeBad"));
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                gui.getResult().setText(gui.getBundle().getString("serverEx"));
            }
            removeFrame.setVisible(false);
        });
        removeFrame.setVisible(true);
    }
}