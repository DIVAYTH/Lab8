package mainCode.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class GraphicsPanel extends JPanel {
    private HashMap<String, GroupCircle> elements = new HashMap();
    private HashMap<String, Color> colors = new HashMap<>();
    private Random random = new Random();
    private GUI gui;

    public HashMap<String, GroupCircle> getElements() {
        return elements;
    }

    public GraphicsPanel(GUI gui) {
        this.gui = gui;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (Map.Entry<String, GroupCircle> element : elements.entrySet()) {
                    if (element.getValue().contains(e.getX(), e.getY())) {
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
    public void udateElement(String[] arguments) {
        float[] rgb = setColor();
        if (!colors.containsKey(arguments[15])) {
            colors.put(arguments[15], new Color(rgb[0], rgb[1], rgb[2]));
        }
        GroupCircle circle = new GroupCircle(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5],
                arguments[6], arguments[7], arguments[8], arguments[9], arguments[10], arguments[11], arguments[12], arguments[13],
                arguments[14], arguments[15]);
        if (!elements.containsKey(arguments[0])) {
            elements.put(arguments[0], circle);
            new Thread(new Animation(circle, gui)).start();
        }
        repaint();
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
            Iterator<Map.Entry<String, GroupCircle>> iter = elements.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, GroupCircle> element = iter.next();
                g2.setColor(colors.get(element.getValue().getLogin()));
                g2.setBackground(colors.get(element.getValue().getLogin()));
                g2.fill(element.getValue());
                g2.draw(element.getValue());
            }
        }catch (ConcurrentModificationException e){
        }
    }
}