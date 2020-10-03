package mainCode.GUI;

import collectionClasses.StudyGroup;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AddFrame {
    private GUI gui;
    private JLabel validate = new JLabel();

    public AddFrame(GUI gui) {
        this.gui = gui;
    }

    public JLabel getValidate() {
        return validate;
    }

    /**
     * Метод создает фрейм для ввода данных
     *
     * @param command
     */
    public void createAddFrame(String command) {
        validate.setText("");
        int count = 0;
        JTextField idFiled = new JTextField();
        JFrame addFrame = new JFrame("Lab 8");
        addFrame.setLayout(new GridBagLayout());
        addFrame.setSize(400, 700);
        addFrame.setDefaultCloseOperation(addFrame.DISPOSE_ON_CLOSE);
        addFrame.setResizable(false);
        addFrame.setLocationRelativeTo(null);
        if (command.equals("update")) {
            createComponentForAdd(addFrame, "id: ", idFiled, count++);
        }
        JTextField nameFiled = new JTextField();
        JTextField xFiled = new JTextField();
        JTextField yFiled = new JTextField();
        JTextField studentsCountField = new JTextField();
        ButtonGroup formOfEducationRadio = new ButtonGroup();
        JRadioButton fof1 = new JRadioButton("DISTANCE_EDUCATION");
        JRadioButton fof2 = new JRadioButton("FULL_TIME_EDUCATION");
        JRadioButton fof3 = new JRadioButton("EVENING_CLASSES");
        fof1.setActionCommand("DISTANCE_EDUCATION");
        fof2.setActionCommand("FULL_TIME_EDUCATION");
        fof3.setActionCommand("EVENING_CLASSES");
        formOfEducationRadio.add(fof1);
        formOfEducationRadio.add(fof2);
        formOfEducationRadio.add(fof3);
        JPanel fof = new JPanel();
        fof.setLayout(new GridLayout(3, 1, 5, 5));
        fof.add(fof1);
        fof.add(fof2);
        fof.add(fof3);
        ButtonGroup semesterRadio = new ButtonGroup();
        JRadioButton s1 = new JRadioButton("FIRST");
        JRadioButton s2 = new JRadioButton("THIRD");
        JRadioButton s3 = new JRadioButton("FIFTH");
        JRadioButton s4 = new JRadioButton("EIGHT");
        s1.setActionCommand("FIRST");
        s2.setActionCommand("THIRD");
        s3.setActionCommand("FIFTH");
        s4.setActionCommand("EIGHT");
        semesterRadio.add(s1);
        semesterRadio.add(s2);
        semesterRadio.add(s3);
        semesterRadio.add(s4);
        JPanel s = new JPanel();
        s.setLayout(new GridLayout(2, 1, 5, 5));
        s.add(s1);
        s.add(s2);
        s.add(s3);
        s.add(s4);
        JTextField perNameFiled = new JTextField();
        JTextField heightField = new JTextField();
        ButtonGroup colorField = new ButtonGroup();
        JRadioButton c1 = new JRadioButton("RED");
        JRadioButton c2 = new JRadioButton("BLUE");
        JRadioButton c3 = new JRadioButton("YELLOW");
        JRadioButton c4 = new JRadioButton("ORANGE");
        JRadioButton c5 = new JRadioButton("WHITE");
        c1.setActionCommand("RED");
        c2.setActionCommand("BLUE");
        c3.setActionCommand("YELLOW");
        c4.setActionCommand("ORANGE");
        c5.setActionCommand("WHITE");
        colorField.add(c1);
        colorField.add(c2);
        colorField.add(c3);
        colorField.add(c4);
        colorField.add(c5);
        JPanel c = new JPanel();
        c.setLayout(new GridLayout(3, 1, 5, 5));
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c.add(c4);
        c.add(c5);
        ButtonGroup nationalityField = new ButtonGroup();
        JRadioButton n1 = new JRadioButton("USA");
        JRadioButton n2 = new JRadioButton("CHINA");
        JRadioButton n3 = new JRadioButton("INDIA");
        JRadioButton n4 = new JRadioButton("VATICAN");
        n1.setActionCommand("USA");
        n2.setActionCommand("CHINA");
        n3.setActionCommand("INDIA");
        n4.setActionCommand("VATICAN");
        nationalityField.add(n1);
        nationalityField.add(n2);
        nationalityField.add(n3);
        nationalityField.add(n4);
        JPanel n = new JPanel();
        n.setLayout(new GridLayout(2, 1, 5, 5));
        n.add(n1);
        n.add(n2);
        n.add(n3);
        n.add(n4);
        JTextField locXField = new JTextField();
        JTextField locYField = new JTextField();
        JTextField locZfield = new JTextField();
        JButton buttonAuth = new JButton(gui.getBundle().getString("submit"));
        createComponentForAdd(addFrame, gui.getBundle().getString("groupName"), nameFiled, count++);
        createComponentForAdd(addFrame, "x: ", xFiled, count++);
        createComponentForAdd(addFrame, "y: ", yFiled, count++);
        createComponentForAdd(addFrame, gui.getBundle().getString("studentsCount"), studentsCountField, count++);
        createComponentForAdd(addFrame, gui.getBundle().getString("formOfEducation"), fof, count++);
        createComponentForAdd(addFrame, gui.getBundle().getString("semester"), s, count++);
        createComponentForAdd(addFrame, gui.getBundle().getString("perName"), perNameFiled, count++);
        createComponentForAdd(addFrame, gui.getBundle().getString("height"), heightField, count++);
        createComponentForAdd(addFrame, gui.getBundle().getString("hairColor"), c, count++);
        createComponentForAdd(addFrame, gui.getBundle().getString("country"), n, count++);
        createComponentForAdd(addFrame, "locX: ", locXField, count++);
        createComponentForAdd(addFrame, "locY: ", locYField, count++);
        createComponentForAdd(addFrame, "locZ: ", locZfield, count++);
        createComponentForAdd(addFrame, "", validate, count++);
        addFrame.add(buttonAuth, new GridBagConstraints(0, count++, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 50, 20, 0), 0, 0));

        buttonAuth.addActionListener(e -> {
            String formOfEducation;
            String semester;
            String color;
            String country;
            try {
                formOfEducation = formOfEducationRadio.getSelection().getActionCommand();
            } catch (NullPointerException ex) {
                formOfEducation = null;
            }
            try {
                semester = semesterRadio.getSelection().getActionCommand();
            } catch (NullPointerException ex) {
                semester = null;
            }
            try {
                color = colorField.getSelection().getActionCommand();
            } catch (NullPointerException ex) {
                color = null;
            }
            try {
                country = nationalityField.getSelection().getActionCommand();
            } catch (NullPointerException ex) {
                country = null;
            }
            String[] arrguments = new String[]{nameFiled.getText(), xFiled.getText(), yFiled.getText(), studentsCountField.getText(), formOfEducation,
                    semester, perNameFiled.getText(), heightField.getText(), color, country, locXField.getText(),
                    locYField.getText(), locZfield.getText()};
            try {
                switch (command) {
                    case "add":
                    case "add_if_max":
                    case "add_if_min": {
                        String result = gui.getClient().handler(command, arrguments, null);
                        if (!result.equals("Данные введены неверно")) {
                            gui.getResult().setText(gui.getLocalization().localize(result));
                            addFrame.dispose();
                        }
                    }
                    break;
                    case "update": {
                        String result = gui.getClient().handler(command, arrguments, idFiled.getText());
                        if (!result.equals("Данные введены неверно")) {
                            gui.getResult().setText(gui.getLocalization().localize(result));
                            addFrame.dispose();
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                gui.getResult().setText(gui.getBundle().getString("serverEx"));
                addFrame.dispose();
            } catch (NumberFormatException ex) {
                validate.setText(gui.getBundle().getString("emptyFields"));
            }
        });
        addFrame.setVisible(true);
    }

    /**
     * Метод добавляет элементы на фрейм
     *
     * @param addFrame
     * @param arg1
     * @param arg2
     * @param position
     */
    private void createComponentForAdd(JFrame addFrame, String arg1, JComponent arg2, int position) {
        addFrame.add(new JLabel(arg1), new GridBagConstraints(0, position, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 40, 0, 0), 0, 0));
        addFrame.add(arg2, new GridBagConstraints(1, position, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 70), 0, 0));
    }
}