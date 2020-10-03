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
            case "Элемент добавлен":
                return gui.getBundle().getString("elementAdd");
            case "Данные введены неверно":
                return gui.getBundle().getString("badAdd");
            case "Элемент коллекции не сохранен":
                return gui.getBundle().getString("noSave");
            case "Коллекция пуста":
                return gui.getBundle().getString("colEmpty");
            case "Элементы удалены":
                return gui.getBundle().getString("elementDel");
            case "Коллекция не изменина":
                return gui.getBundle().getString("noChange");
            case "Нет элемента с таким id или пользователь не имеет доступа к этому элементу":
                return gui.getBundle().getString("deleteError");
            case "Нет элемента с таким student_count или пользователь не имеет доступа к этому элементу":
                return gui.getBundle().getString("deleteError2");
            default:
                return str;
        }
    }
}
