package mainCode.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class GraphicsPanel extends JPanel {
    private HashMap<String, AcademicHat> elementsClient = new HashMap();
    private HashMap<String, Color> colors = new HashMap<>();
    private Random random = new Random();
    private GUI gui;

    public HashMap<String, Color> getColors() {
        return colors;
    }

    public HashMap<String, AcademicHat> getElementsClient() {
        return elementsClient;
    }

    public GraphicsPanel(GUI gui) {
        this.gui = gui;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (Map.Entry<String, AcademicHat> element : elementsClient.entrySet()) {
                    if (element.getValue().getHad().contains(e.getX(), e.getY()) ||
                            element.getValue().getRhombus().contains(e.getX(), e.getY()) ||
                            element.getValue().getRope().contains(e.getX(), e.getY()) ||
                            element.getValue().getTriangle().contains(e.getX(), e.getY())) {
                        gui.getResult().setText("id: " + element.getValue().getId() + "\n"
                                + gui.getBundle().getString("groupName") + " " + element.getValue().getName() + "\n"
                                + "x " + element.getValue().getX() + "\n"
                                + "y " + element.getValue().getY() + "\n"
                                + gui.getBundle().getString("creationDate") + " " + element.getValue().getDateTime() + "\n"
                                + gui.getBundle().getString("studentsCount") + " " + element.getValue().getStudentsCount() + "\n"
                                + gui.getBundle().getString("formOfEducation") + " " + element.getValue().getFormOfEducation() + "\n"
                                + gui.getBundle().getString("semester") + " " + element.getValue().getSemester() + "\n"
                                + gui.getBundle().getString("perName") + " " + element.getValue().getPerName() + "\n"
                                + gui.getBundle().getString("height") + " " + element.getValue().getHeight() + "\n"
                                + gui.getBundle().getString("hairColor") + " " + element.getValue().getColor() + "\n"
                                + gui.getBundle().getString("country") + " " + element.getValue().getCountry() + "\n"
                                + "locX " + element.getValue().getLocX() + "\n"
                                + "locY " + element.getValue().getLocY() + "\n"
                                + "locZ " + element.getValue().getLocZ() + "\n"
                                + gui.getBundle().getString("login") + " " + element.getValue().getLogin() + "\n"
                        );
                    }
                }
            }
        });
    }

    /**
     * Метод добавляет элементы в зону визуализации
     *
     * @param arguments7
     */
    public void udateElement(HashMap<String, AcademicHat> elementsServer) {
        try {
            for (Map.Entry<String, AcademicHat> elementServer : elementsServer.entrySet()) {
                if (!elementsClient.containsKey(elementServer.getKey())) {
                    elementsClient.put(elementServer.getKey(), elementServer.getValue());
                    new Thread(new AnimationAdd(elementServer.getValue(), gui)).start();
                }
            }
            for (Map.Entry<String, AcademicHat> elementClient : elementsClient.entrySet()) {
                if (!elementsServer.containsKey(elementClient.getKey())) {
                    new Thread(new AnimationDelete(elementClient.getValue(), gui, elementsClient, elementClient.getKey())).start();
                }
            }
            for (Map.Entry<String, AcademicHat> elementServer : elementsServer.entrySet()) {
                if (!elementsClient.get(elementServer.getKey()).getName().equals(elementServer.getValue().getName()) ||
                        !elementsClient.get(elementServer.getKey()).getX().equals(elementServer.getValue().getX()) ||
                        !elementsClient.get(elementServer.getKey()).getY().equals(elementServer.getValue().getY()) ||
                        !elementsClient.get(elementServer.getKey()).getStudentsCount().equals(elementServer.getValue().getStudentsCount()) ||
                        !elementsClient.get(elementServer.getKey()).getFormOfEducation().equals(elementServer.getValue().getFormOfEducation()) ||
                        !elementsClient.get(elementServer.getKey()).getSemester().equals(elementServer.getValue().getSemester()) ||
                        !elementsClient.get(elementServer.getKey()).getPerName().equals(elementServer.getValue().getPerName()) ||
                        !elementsClient.get(elementServer.getKey()).getHeight().equals(elementServer.getValue().getHeight()) ||
                        !elementsClient.get(elementServer.getKey()).getColor().equals(elementServer.getValue().getColor()) ||
                        !elementsClient.get(elementServer.getKey()).getCountry().equals(elementServer.getValue().getCountry()) ||
                        !elementsClient.get(elementServer.getKey()).getLocX().equals(elementServer.getValue().getLocX()) ||
                        !elementsClient.get(elementServer.getKey()).getLocY().equals(elementServer.getValue().getLocY()) ||
                        !elementsClient.get(elementServer.getKey()).getLocZ().equals(elementServer.getValue().getLocZ())) {
                    new Thread(new AnimationUpdate(elementsClient.get(elementServer.getKey()), elementServer.getValue(), gui, elementsClient, elementServer.getKey())).start();
                }
                repaint();
            }
        } catch (ConcurrentModificationException e) {
            // Исключение не мешает логике исполнения программы
        }
    }

    /**
     * Метод генерирует цвет элементам
     *
     * @return
     */
    public float[] setColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        return new float[]{r, g, b};
    }

    /**
     * Метод рисует фигуры на фрейме
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        try {
            super.paintComponent(g);
            this.setBackground(Color.WHITE);
            Graphics2D g2 = (Graphics2D) g;
            for (Map.Entry<String, AcademicHat> element : elementsClient.entrySet()) {
                g2.setBackground(Color.WHITE);
                element.getValue().drawHat(g2, colors.get(element.getValue().getLogin()));
            }
        } catch (ConcurrentModificationException e) {
            // Исключение не мешает логике исполнения программы
        }
    }
}