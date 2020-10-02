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
     * @param arguments
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
            repaint();
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}