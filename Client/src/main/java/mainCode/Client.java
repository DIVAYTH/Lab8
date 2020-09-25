package mainCode;

import collectionClasses.*;
import mainCode.GUI.GUI;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Client {
    private Socket socket;
    private ArrayList<File> scriptRepeat = new ArrayList<>();
    private static GUI gui;

    public static void main(String[] args) {
        gui = new GUI();
        gui.getConnection().createConnectionFrame();
    }

    /**
     * Метод подключает клиент к серверу
     */
    public String connection(String port, String host) {
        try {
            SocketAddress socketAddress = new InetSocketAddress(host, Integer.parseInt(port));
            socket = new Socket();
            socket.connect(socketAddress);
            return "!!!";
        } catch (SocketTimeoutException e) {
            return gui.getBundle().getString("connectionEx1");
        } catch (IOException | IllegalArgumentException e) {
            return gui.getBundle().getString("connectionEx2");
        }
    }

    /**
     * Метод выбирает и отправляет команду на сервер
     *
     * @param command
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object handler(String command, String[] arg1, String arg2) throws IOException, ClassNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        if (command.equals("reg") || command.equals("sign")) {
            Command request = new Command(command, gui.getAuthorization().getLogin(), gui.getAuthorization().getPassword());
            send(request);
            String result = (String) receive();
            switch (result) {
                case "Авторизация прошла успешно":
                    return gui.getBundle().getString("authorizationResult");
                case "Регистрация прошла успешно":
                    return gui.getBundle().getString("registrationResult");
                case "Логин или пароль введены неверно":
                    return gui.getBundle().getString("authorizationEx");
                case "Пользователь с таким логином уже существует":
                    return gui.getBundle().getString("registrationEx");
            }
        } else {
            switch (command) {
                case "help": {
                    Command request = new Command(command, gui.getAuthorization().getLogin(), gui.getAuthorization().getPassword());
                    send(request);
                    String result = (String) receive();
                    if (result != null)
                        return gui.getBundle().getString("spravka");
                }
                case "clear":
                case "info":
                case "show":
                case "print_field_ascending_students_count":
                case "print_field_descending_form_of_education": {
                    Command request = new Command(command, gui.getAuthorization().getLogin(), gui.getAuthorization().getPassword());
                    send(request);
                    String result = (String) receive();
                    switch (result) {
                        case "Коллекция очищена. Удалены все принадлежащие вам элементы":
                            return gui.getBundle().getString("clearRes");
                        case "В коллекции нет элементов принадлежащих пользователю":
                            return gui.getBundle().getString("clearRes2");
                        case "Ошибка при работе с БД":
                            return gui.getBundle().getString("bdEx");
                        default:
                            return result;
                    }
                }
                case "add":
                case "add_if_max":
                case "add_if_min": {
                    Command request = new Command(command, createObject(arg1), gui.getAuthorization().getLogin(), gui.getAuthorization().getPassword());
                    send(request);
                    return receive();
                }
                case "remove_greater":
                case "remove_by_id":
                case "remove_any_by_students_count":
                    try {
                        Integer.parseInt(arg2);
                        Command request = new Command(command, arg2, gui.getAuthorization().getLogin(), gui.getAuthorization().getPassword());
                        send(request);
                        return receive();
                    } catch (NumberFormatException e) {
                        return gui.getBundle().getString("argEx");
                    }
                case "update":
                    try {
                        Integer.parseInt(arg2);
                        Command request = new Command(command, arg2, createObject(arg1), gui.getAuthorization().getLogin(), gui.getAuthorization().getPassword());
                        send(request);
                        return receive();
                    } catch (NumberFormatException e) {
                        return null;
                    }
                case "execute_script":
                    File file = new File(arg2);
                    if (!file.exists())
                        return gui.getBundle().getString("fileEx");
                    if (!file.canRead())
                        return gui.getBundle().getString("fileEx2");
                    if (scriptRepeat.contains(file)) {
                        return gui.getBundle().getString("rec") + arg2 + gui.getBundle().getString("rec2");
                    } else {
                        scriptRepeat.add(file);
                        try {
                            BufferedReader commandReader = new BufferedReader(new FileReader(file));
                            String line = commandReader.readLine();
                            while (line != null) {
                                String[] commandName = line.split(" ");
                                switch (commandName[0]) {
                                    case "clear":
                                    case "help":
                                    case "info":
                                    case "print_field_ascending_students_count":
                                    case "print_field_descending_form_of_education":
                                        stringBuilder.append(handler(commandName[0], null, null)).append("\n\n");
                                        break;
                                    case "add_if_max":
                                    case "add_if_min":
                                    case "add": {
                                        String[] arrScript = new String[13];
                                        for (int i = 0; i < arrScript.length; i++) {
                                            arrScript[i] = commandReader.readLine();
                                        }
                                        StudyGroup studyGroup = (StudyGroup) handler(commandName[0], arrScript, null);
                                        if (studyGroup != null) {
                                            stringBuilder.append(gui.getBundle().getString("addWell")).append("\n\n");
                                        } else {
                                            stringBuilder.append(gui.getBundle().getString("addBad")).append("\n\n");
                                        }
                                    }
                                    break;
                                    case "remove_greater":
                                    case "remove_by_id":
                                    case "remove_any_by_students_count":
                                        try {
                                            Integer.parseInt(commandName[1]);
                                            String result = ((String) handler(commandName[0], null, commandName[1]));
                                            if (result.equals("Элемент удален") || result.equals("Элементы удалены"))
                                            stringBuilder.append(result).append("\n\n");
                                        } catch (NumberFormatException e) {
                                            stringBuilder.append(gui.getBundle().getString("argEx")).append("\n\n");
                                        }
                                        break;
                                    case "update":
                                        try {
                                            Integer.parseInt(commandName[1]);
                                            String[] arrScript = new String[13];
                                            for (int i = 0; i < arrScript.length; i++) {
                                                arrScript[i] = commandReader.readLine();
                                            }
                                            StudyGroup studyGroup = (StudyGroup) handler(commandName[0], arrScript, commandName[1]);
                                            if (studyGroup != null) {
                                                stringBuilder.append(gui.getBundle().getString("updateWell")).append("\n\n");
                                            } else {
                                                stringBuilder.append(gui.getBundle().getString("updateBad")).append("\n\n");
                                            }
                                        } catch (NumberFormatException e) {
                                            stringBuilder.append(gui.getBundle().getString("argEx")).append("\n\n");
                                        }
                                        break;
                                    case "execute_script":
                                        stringBuilder.append(handler(commandName[0], null, commandName[1])).append("\n\n");
                                        break;
                                    default:
                                        stringBuilder.append(gui.getBundle().getString("executeEx")).append("\n");
                                }
                                line = commandReader.readLine();
                            }
                            scriptRepeat.remove(scriptRepeat.size() - 1);
                        } catch (NullPointerException e) {
                            return gui.getBundle().getString("fileNull");
                        }
                    }
            }
        }
        return String.valueOf(stringBuilder.append(gui.getBundle().getString("scriptRes")).append("\n\n"));
    }

    /**
     * Метод отправляет команду на сервер
     *
     * @param answer
     * @throws IOException
     */
    public void send(Command answer) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream toServer = new ObjectOutputStream(baos);
        toServer.writeObject(answer);
        byte[] out = baos.toByteArray();
        socket.getOutputStream().write(out);
    }

    /**
     * Метод получает результат от сервера
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object receive() throws IOException, ClassNotFoundException {
        try {
            Object answer;
            ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
            answer = fromServer.readObject();
            if (answer.equals("exit")) {
                System.exit(0);
            }
            return answer;
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Метод создает элемент для колекции
     *
     * @return
     */
    public StudyGroup createObject(String[] arguments) {
        StudyGroup studyGroup;
        FormOfEducation formOfEducation;
        Semester semesterEnum;
        Color hairColor;
        Country nationality;
        try {
            String name = checkString(arguments[0]);
            Integer x = checkInt(arguments[1]);
            Double y = checkDouble(arguments[2]);
            Integer studentsCount = checkIntWithNull(arguments[3]);
            try {
                formOfEducation = FormOfEducation.valueOf(arguments[4]);
            } catch (IllegalArgumentException e) {
                formOfEducation = null;
            }
            try {
                semesterEnum = Semester.valueOf(arguments[5]);
            } catch (IllegalArgumentException e) {
                semesterEnum = null;
            }
            String perName = checkString(arguments[6]);
            Integer height = checkIntWithNull(arguments[7]);
            try {
                hairColor = Color.valueOf(arguments[8]);
            } catch (IllegalArgumentException e) {
                hairColor = null;
            }
            nationality = Country.valueOf(arguments[9]);
            Double locX = checkDouble(arguments[10]);
            Integer locY = checkInt(arguments[11]);
            Integer locZ = checkInt(arguments[12]);
            long id = 0;
            studyGroup = new StudyGroup(id, name, new Coordinates(x, y), studentsCount, formOfEducation, semesterEnum,
                    new Person(perName, height, hairColor, nationality, new Location(locX, locY, locZ)), "");
        } catch (Exception e) {
            studyGroup = null;
        }
        return studyGroup;
    }

    /**
     * Метод проверяет занчение int для add
     */
    private Integer checkInt(String value) throws Exception {
        int result;
        if (value.equals("")) {
            throw new Exception();
        } else {
            result = Integer.parseInt(value);
        }
        return result;
    }

    /**
     * Метод проверяет занчение double для add
     */
    private Double checkDouble(String value) throws Exception {
        double result;
        if (value.equals("")) {
            throw new Exception();
        } else {
            result = Double.parseDouble(value);
        }
        return result;
    }

    /**
     * Метод проверяет занчение String для add
     */
    private String checkString(String value) throws Exception {
        if (value.equals("")) {
            throw new Exception();
        }
        return value;
    }

    /**
     * Метод проверяет занчение int для add с возможным null
     */
    private Integer checkIntWithNull(String value) throws Exception {
        Integer result;
        if (value.equals("")) {
            result = null;
        } else if (Integer.parseInt(value) < 0) {
            throw new Exception();
        } else {
            result = Integer.parseInt(value);
        }
        return result;
    }
}