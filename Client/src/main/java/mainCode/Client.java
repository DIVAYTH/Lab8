package mainCode;

import collectionClasses.*;
import mainCode.GUI.GUI;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Client {
    private ArrayList<File> scriptRepeat = new ArrayList<>();
    private static GUI gui;
    private String port;
    private String host;

    public static void main(String[] args) {
        gui = new GUI();
        gui.getConnection().createConnectionFrame();
    }

    /**
     * Метод подключает клиент к серверу
     */
    public String connection(String port, String host) {
        try {
            Socket socket = new Socket(host, Integer.parseInt(port));
            this.port = port;
            this.host = host;
            socket.close();
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
    public String handler(String command, String[] arg1, String arg2) throws IOException, ClassNotFoundException, NumberFormatException {
        String result = null;
        StringBuilder stringBuilder = new StringBuilder();
        switch (command) {
            case "show": {
                Command request = new Command(command, gui.getAuthorization().getLogin(), gui.getAuthorization().getPassword());
                result = send(request);
            }
            break;
            case "add":
            case "add_if_max":
            case "add_if_min": {
                StudyGroup studyGroup = createObject(arg1);
                if (studyGroup != null) {
                    Command request = new Command(command, studyGroup, gui.getAuthorization().getLogin(), gui.getAuthorization().getPassword());
                    result = send(request);
                } else {
                    result = "Данные введены неверно";
                }
            }
            break;
            case "reg":
            case "sign":
            case "help":
            case "clear":
            case "info":
            case "print_field_ascending_students_count":
            case "print_field_descending_form_of_education": {
                Command request = new Command(command, gui.getAuthorization().getLogin(), gui.getAuthorization().getPassword());
                result = gui.getLocalization().localize(send(request));
            }
            break;
            case "remove_greater":
            case "remove_by_id":
            case "remove_any_by_students_count": {
                Integer.parseInt(arg2);
                Command request = new Command(command, arg2, gui.getAuthorization().getLogin(), gui.getAuthorization().getPassword());
                result = send(request);
            }
            break;
            case "update": {
                Integer.parseInt(arg2);
                StudyGroup studyGroup = createObject(arg1);
                if (studyGroup != null) {
                    Command request = new Command(command, arg2, createObject(arg1), gui.getAuthorization().getLogin(), gui.getAuthorization().getPassword());
                    result = send(request);
                } else {
                    result = "Данные введены неверно";
                }
            }
            break;
            case "execute_script": {
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
                                    stringBuilder.append(handler(commandName[0], arrScript, null)).append("\n\n");
                                }
                                break;
                                case "remove_greater":
                                case "remove_by_id":
                                case "remove_any_by_students_count": {
                                    try {
                                        Integer.parseInt(commandName[1]);
                                        stringBuilder.append(handler(commandName[0], null, commandName[1])).append("\n\n");
                                    } catch (NumberFormatException e) {
                                        stringBuilder.append(gui.getBundle().getString("argEx")).append("\n\n");
                                    }
                                }
                                break;
                                case "update":
                                    try {
                                        Integer.parseInt(commandName[1]);
                                        String[] arrScript = new String[13];
                                        for (int i = 0; i < arrScript.length; i++) {
                                            arrScript[i] = commandReader.readLine();
                                        }
                                        stringBuilder.append(handler(commandName[0], arrScript, commandName[1])).append("\n\n");
                                    } catch (NumberFormatException e) {
                                        stringBuilder.append(gui.getBundle().getString("argEx")).append("\n\n");
                                    }
                                    break;
                                case "execute_script":
                                    stringBuilder.append(handler(commandName[0], null, commandName[1])).append("\n\n");
                                    break;
                                default:
                                    stringBuilder.append(gui.getBundle().getString("executeEx")).append("\n\n");
                            }
                            line = commandReader.readLine();
                        }
                        scriptRepeat.remove(scriptRepeat.size() - 1);
                    } catch (NullPointerException e) {
                        return gui.getBundle().getString("fileNull");
                    }
                }
                return String.valueOf(stringBuilder.append(gui.getBundle().getString("scriptRes")).append("\n\n"));
            }
        }
        return result;
    }

    /**
     * Метод отправляет команду на сервер
     *
     * @param answer
     * @throws IOException
     */
    public String send(Command answer) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(host, Integer.parseInt(port));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream toServer = new ObjectOutputStream(baos);
        toServer.writeObject(answer);
        byte[] out = baos.toByteArray();
        socket.getOutputStream().write(out);
        baos.close();
        toServer.close();
        String serverAnswer = receive(socket);
        socket.close();
        return serverAnswer;
    }

    /**
     * Метод получает результат от сервера
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String receive(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
        String serverAnswer = (String) fromServer.readObject();
        fromServer.close();
        return serverAnswer;
    }

    /**
     * Метод создает элемент для колекции
     *
     * @return
     */
    public StudyGroup createObject(String[] arguments) {
        String[] names = new String[]{gui.getBundle().getString("groupName"), "X", "Y", gui.getBundle().getString("studentsCount"), gui.getBundle().getString("formOfEducation"), gui.getBundle().getString("semester"),
                gui.getBundle().getString("perName"), gui.getBundle().getString("height"), gui.getBundle().getString("hairColor"), gui.getBundle().getString("country"), "locX", "locY", "locz"};
        StudyGroup studyGroup;
        Country nationality;
        FormOfEducation formOfEducation;
        Semester semesterEnum;
        Color hairColor;
        try {
            String name = checkString(arguments[0], names[0]);
            Integer x = checkInt(arguments[1], names[1]);
            Double y = checkDouble(arguments[2], names[2]);
            Integer studentsCount = checkIntWithNull(arguments[3], names[3]);
            if (arguments[4] == null) {
                formOfEducation = null;
            } else {
                formOfEducation = FormOfEducation.valueOf(arguments[4]);
            }
            if (arguments[5] == null) {
                semesterEnum = null;
            } else {
                semesterEnum = Semester.valueOf(arguments[5]);
            }
            String perName = checkString(arguments[6], names[6]);
            Integer height = checkIntWithNull(arguments[7], names[7]);
            if (arguments[8] == null) {
                hairColor = null;
            } else {
                hairColor = Color.valueOf(arguments[8]);
            }
            nationality = (Country) checkEnumWithNull(arguments[9], names[9]);
            Double locX = checkDouble(arguments[10], names[10]);
            Integer locY = checkInt(arguments[11], names[11]);
            Integer locZ = checkInt(arguments[12], names[12]);
            studyGroup = new StudyGroup(0, name, new Coordinates(x, y), studentsCount, formOfEducation, semesterEnum,
                    new Person(perName, height, hairColor, nationality, new Location(locX, locY, locZ)), "");
        } catch (Exception e) {
            studyGroup = null;
        }
        return studyGroup;
    }

    /**
     * Метод проверяет занчение int для add
     */
    private Integer checkInt(String value, String name) throws Exception {
        try {
            int result;
            if (value.equals("")) {
                gui.getAdd().getValidate().setText("<html>" + name + " не может быть пустой строкой" + "</html>");
                throw new Exception();
            } else {
                result = Integer.parseInt(value);
            }
            return result;
        } catch (NumberFormatException e) {
            gui.getAdd().getValidate().setText("<html>" + name + " должен быть числом" + "</html>");
            throw new Exception();
        }
    }

    /**
     * Метод проверяет занчение double для add
     */
    private Double checkDouble(String value, String name) throws Exception {
        try {
            double result;
            if (value.equals("")) {
                gui.getAdd().getValidate().setText("<html>" + name + " не может быть пустой строкой" + "</html>");
                throw new Exception();
            } else {
                result = Double.parseDouble(value);
            }
            return result;
        } catch (NumberFormatException e) {
            gui.getAdd().getValidate().setText("<html>" + name + " должен быть числом" + "</html>");
            throw new Exception();
        }
    }

    /**
     * Метод проверяет занчение String для add
     */
    private String checkString(String value, String name) throws Exception {
        if (value.equals("")) {
            gui.getAdd().getValidate().setText("<html>" + name + " не может быть пустой строкой" + "</html>");
            throw new Exception();
        }
        return value;
    }

    /**
     * Метод проверяет занчение int для add с возможным null
     */
    private Integer checkIntWithNull(String value, String name) throws Exception {
        try {
            Integer result;
            if (value.equals("")) {
                result = null;
            } else if (Integer.parseInt(value) < 0) {
                gui.getAdd().getValidate().setText("<html>" + name + " должен быть больше нуля" + "</html>");
                throw new Exception();
            } else {
                result = Integer.parseInt(value);
            }
            return result;
        } catch (NumberFormatException e) {
            gui.getAdd().getValidate().setText("<html>" + name + " должен быть числом" + "</html>");
            throw new Exception();
        }
    }

    /**
     * Метод проверяет значение для Country
     *
     * @param value
     * @param name
     * @return
     * @throws Exception
     */
    private Enum<Country> checkEnumWithNull(String value, String name) throws Exception {
        Country nationality;
        try {
            nationality = Country.valueOf(value);
            return nationality;
        } catch (RuntimeException e) {
            gui.getAdd().getValidate().setText("<html>" + name + " не может быть null" + "</html>");
            throw new Exception();
        }
    }
}