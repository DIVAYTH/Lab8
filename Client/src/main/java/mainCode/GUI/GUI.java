package mainCode.GUI;

import collectionClasses.*;
import mainCode.Client;

import javax.swing.*;
import java.util.*;

public class GUI {
    private ResourceBundle bundle = ResourceBundle.getBundle("resources");
    private Client client = new Client();
    private ConnectionFrame connection = new ConnectionFrame(this);
    private AuthorizationFrame authorization = new AuthorizationFrame(this);
    private MainFrame main = new MainFrame(this);
    private AddFrame add = new AddFrame(this);
    private RemoveFrame remove = new RemoveFrame(this);
    private GraphicsPanel graphicsPanel = new GraphicsPanel(this);
    private JTextArea result = new JTextArea();

    public ResourceBundle getBundle() {
        return bundle;
    }

    public Client getClient() {
        return client;
    }

    public ConnectionFrame getConnection() {
        return connection;
    }

    public AuthorizationFrame getAuthorization() {
        return authorization;
    }

    public MainFrame getMain() {
        return main;
    }

    public AddFrame getAdd() {
        return add;
    }

    public RemoveFrame getRemove() {
        return remove;
    }

    public GraphicsPanel getGraphicsPanel() {
        return graphicsPanel;
    }

    public JTextArea getResult() {
        return result;
    }

    /**
     * Метод выбирает локализация программы
     */
    public void choseLanguage(JComboBox<String> languages) {
        String language = (String) languages.getSelectedItem();
        switch (language) {
            case "русский":
                bundle = ResourceBundle.getBundle("resources");
                break;
            case "Español (Colombia)":
                bundle = ResourceBundle.getBundle("resources", new Locale("sp"));
                break;
            case "Íslensk":
                bundle = ResourceBundle.getBundle("resources", new Locale("is"));
                break;
            case "Hrvatski":
                bundle = ResourceBundle.getBundle("resources", new Locale("hv"));
                break;
        }
    }
}