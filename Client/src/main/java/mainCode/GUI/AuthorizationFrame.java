package mainCode.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AuthorizationFrame {
    private GUI gui;
    private String login;
    private String password;
    private JFrame frame = new JFrame("Lab 8");

    public AuthorizationFrame(GUI gui) {
        this.gui = gui;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * Метод создает фрейм для авторизации
     */
    public void createAuthorizationFrame() {
        frame.setLayout(new GridBagLayout());
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JLabel loginLabel = new JLabel(gui.getBundle().getString("login"));
        JTextField loginFiled = new JTextField();
        JLabel passwordLabel = new JLabel(gui.getBundle().getString("password"));
        JTextField passwordField = new JTextField();
        JLabel result = new JLabel();
        JButton buttonAuth = new JButton(gui.getBundle().getString("enter"));
        JButton buttonReg = new JButton(gui.getBundle().getString("registration"));
        JComboBox<String> languages = new JComboBox<>(new String[]{"русский", "Íslensk", "Hrvatski", "Español (Colombia)"});

        frame.add(languages, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 200, 0, 10), 0, 0));
        frame.add(loginLabel, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 50, 0, 50), 0, 0));
        frame.add(loginFiled, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 50, 0, 50), 0, 0));
        frame.add(passwordLabel, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 50, 0, 50), 0, 0));
        frame.add(passwordField, new GridBagConstraints(0, 4, 2, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 50, 0, 50), 0, 0));
        frame.add(result, new GridBagConstraints(0, 5, 2, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 50, 0, 50), 0, 0));
        frame.add(buttonAuth, new GridBagConstraints(0, 6, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 50, 0, 0), 0, 0));
        frame.add(buttonReg, new GridBagConstraints(1, 6, 1, 1, 0.1, 1.0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 50), 0, 0));

        buttonAuth.addActionListener(e -> {
            try {
                login = loginFiled.getText();
                password = passwordField.getText();
                String answer = (String) gui.getClient().handler("sign", null, null);
                if (answer.equals(gui.getBundle().getString("authorizationResult"))) {
                    frame.setVisible(false);
                    if (gui.getMain().getUser().getText().equals("")) {
                        gui.getMain().createMainFrame();
                    } else {
                        gui.getMain().getUser().setText(getLogin());
                        gui.getMain().getMainFrame().setVisible(true);
                    }
                    result.setText("");
                } else {
                    result.setText("<html>" + answer + "</html>");
                }
                loginFiled.setText("");
                passwordField.setText("");
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                result.setText("<html>" + gui.getBundle().getString("serverEx") + "</html>");
            }
        });

        buttonReg.addActionListener(e -> {
            if (loginFiled.getText().equals("") || passwordField.getText().equals("")) {
                result.setText("<html>" + "Поля не могут быть пустыми" + "</html>");
            } else {
                try {
                    login = loginFiled.getText();
                    password = passwordField.getText();
                    String answer = (String) gui.getClient().handler("reg", null, null);
                    result.setText("<html>" + answer + "</html>");
                    loginFiled.setText("");
                    passwordField.setText("");
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    result.setText("<html>" + gui.getBundle().getString("serverEx") + "</html>");
                }
            }
        });

        languages.addActionListener(e -> {
            gui.choseLanguage(languages);
            loginLabel.setText(gui.getBundle().getString("login"));
            passwordLabel.setText(gui.getBundle().getString("password"));
            buttonAuth.setText(gui.getBundle().getString("enter"));
            buttonReg.setText(gui.getBundle().getString("registration"));
            result.setText("");
        });
        frame.setVisible(true);
    }
}