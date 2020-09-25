package mainCode.GUI;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Класс отражающий объекты в зоне визуализации
 */
public class GroupCircle extends Ellipse2D {
    private String id;
    private String name;
    private String dateTime;
    private String studentsCount;
    private String formOfEducation;
    private String semester;
    private String perName;
    private String height;
    private String color;
    private String country;
    private String locX;
    private String locY;
    private String locZ;
    private String login;
    private Point point;
    private double h;
    private double w;

    public GroupCircle(String id, String name, String x, String y, String dateTime, String studentsCount,
                       String formOfEducation, String semester, String perName, String height, String color, String country,
                       String locX, String locY, String locZ, String login) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semester = semester;
        this.perName = perName;
        this.height = height;
        this.color = color;
        this.country = country;
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.login = login;
        point = new Point(Integer.parseInt(x) + 250, (int) java.lang.Double.parseDouble(y) + 170);
        try {
            this.h = java.lang.Double.parseDouble(studentsCount);
            this.w = java.lang.Double.parseDouble(studentsCount);
        } catch (NumberFormatException e) {
            this.h = 50;
            this.w = 50;
        }
    }

    @Override
    public double getX() {
        return point.x;
    }

    @Override
    public double getY() {
        return point.y;
    }

    @Override
    public double getWidth() {
        return w;
    }

    @Override
    public double getHeight() {
        return h;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setFrame(double x, double y, double w, double h) {
        point.setLocation(x, y);
        this.w = w;
        this.h = h;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getStudentsCount() {
        return studentsCount;
    }

    public String getFormOfEducation() {
        return formOfEducation;
    }

    public String getSemester() {
        return semester;
    }

    public String getPerName() {
        return perName;
    }

    public String getHeightPer() {
        return height;
    }

    public String getColor() {
        return color;
    }

    public String getCountry() {
        return country;
    }

    public String getLocX() {
        return locX;
    }

    public String getLocY() {
        return locY;
    }

    public String getLocZ() {
        return locZ;
    }

    public String getLogin() {
        return login;
    }

    public Point getPoint() {
        return point;
    }

    public void increase() {
        w++;
        h++;
    }

    public void decrease() {
        w--;
        h--;
    }
}