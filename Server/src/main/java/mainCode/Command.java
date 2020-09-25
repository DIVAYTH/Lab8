package mainCode;

import collectionClasses.StudyGroup;

import java.io.Serializable;

/**
 * Класс для передачи команд в виде объекта
 */
public class Command implements Serializable {
    private static final long serialVersionUID = 17L;
    private String name;
    private String args;
    private StudyGroup studyGroup;
    private String login;
    private String password;

    public Command(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Command(String name, StudyGroup studyGroup, String login, String password) {
        this.name = name;
        this.studyGroup = studyGroup;
        this.login = login;
        this.password = password;
    }

    public Command(String name, String args, StudyGroup studyGroup, String login, String password) {
        this.name = name;
        this.args = args;
        this.studyGroup = studyGroup;
        this.login = login;
        this.password = password;
    }

    public Command(String name, String args, String login, String password) {
        this.name = name;
        this.args = args;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getArgs() {
        return args;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}