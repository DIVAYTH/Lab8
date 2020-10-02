package mainCode;

import mainCode.GUI.GUI;

public class Localization {
    private GUI gui;

    public Localization(GUI gui) {
        this.gui = gui;
    }

    public String localize(String str) {
        switch (str) {
            case "Авторизация прошла успешно":
                return gui.getBundle().getString("authorizationResult");
            case "Регистрация прошла успешно":
                return gui.getBundle().getString("registrationResult");
            case "Логин или пароль введены неверно":
                return gui.getBundle().getString("authorizationEx");
            case "Пользователь с таким логином уже существует":
                return gui.getBundle().getString("registrationEx");
            case "Коллекция очищена. Удалены все принадлежащие вам элементы":
                return gui.getBundle().getString("clearRes");
            case "В коллекции нет элементов принадлежащих пользователю":
                return gui.getBundle().getString("clearRes2");
            case "Ошибка при работе с БД":
                return gui.getBundle().getString("bdEx");
            default:
                return str;
        }
    }
}
