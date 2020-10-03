package mainCode.GUI;

import javax.swing.*;
import java.awt.*;

public class ConnectionFrame {
    private GUI gui;

    public ConnectionFrame(GUI gui) {
        this.gui = gui;
    }

    /**
     * Метод создает фрейм для подключения к серверу
     */
    public void createConnectionFrame() {
        JFrame frame = new JFrame("Lab 8");
        frame.setLayout(new GridBagLayout());
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JLabel portLabel = new JLabel((gui.getBundle().getString("port")));
        JTextField portField = new JTextField();
        JLabel hostLabel = new JLabel(gui.getBundle().getString("host"));
        JTextField hostField = new JTextField();
        JLabel result = new JLabel();
        JButton buttonCnt = new JButton(gui.getBundle().getString("submit"));
        JComboBox<String> languages = new JComboBox<>(new String[]{"русский", "Íslensk", "Hrvatski", "Español (Colombia)"});

        frame.add(languages, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 200, 0, 10), 0, 0));
        frame.add(portLabel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 50, 0, 50), 0, 0));
        frame.add(portField, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 50, 0, 50), 0, 0));
        frame.add(hostLabel, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 50, 0, 50), 0, 0));
        frame.add(hostField, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 50, 0, 50), 0, 0));
        frame.add(result, new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 50, 0, 50), 0, 0));
        frame.add(buttonCnt, new GridBagConstraints(0, 6, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 50, 0, 50), 0, 0));

        buttonCnt.addActionListener(e -> {
            if (portField.getText().equals("") || hostField.getText().equals("")) {
                result.setText("<html>" + gui.getBundle().getString("emptyFields") + "</html>");
            } else {
                String answer = String.valueOf(gui.getClient().connection(portField.getText(), hostField.getText()));
                if (answer.equals("!!!")) {
                    frame.dispose();
                    gui.getAuthorization().createAuthorizationFrame();
                } else {
                    result.setText("<html>" + answer + "</html>");
                }
            }
        });

        languages.addActionListener(e -> {
            gui.choseLanguage(languages);
            portLabel.setText(gui.getBundle().getString("port"));
            hostLabel.setText(gui.getBundle().getString("host"));
            buttonCnt.setText(gui.getBundle().getString("submit"));
            result.setText("");
        });
        frame.setVisible(true);
    }
}