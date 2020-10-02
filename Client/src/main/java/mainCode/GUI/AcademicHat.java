package mainCode.GUI;

import java.awt.*;
import java.awt.geom.*;

/**
 * Класс отражающий объекты в зоне визуализации
 */
public class AcademicHat {
    private String id;
    private String x;
    private String y;
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
    private GeneralPath path;
    private Arc2D arc2D;
    int hatX;
    int hatY;
    int size;

    public AcademicHat(String id, String x, String y, String name, String dateTime, String studentsCount, String formOfEducation,
                       String semester, String perName, String height, String color, String country, String locX, String locY,
                       String locZ, String login) {
        this.id = id;
        this.x = x;
        this.y = y;
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
        this.path = new GeneralPath();
        hatX = Integer.parseInt(x) + 250;
        hatY = (int) (Double.parseDouble(y)) + 170;
        size = Integer.parseInt(studentsCount);
        arc2D = new Arc2D.Double(hatX, hatY, size, size, 170, 200, Arc2D.PIE);
    }

    public void drawHat(Graphics2D g2, Color color) {
        GeneralPath path = new GeneralPath();
        path.moveTo(hatX + size / 2, hatY + size / 2);
        path.lineTo(hatX + size * 2, hatY + size / 4);
        path.lineTo(hatX + size / 2, hatY - size / 4);
        path.lineTo(hatX - size, hatY + size / 4);
        path.closePath();
        g2.fill(path);
        Line2D.Double line = new Line2D.Double(new Point2D.Double(hatX - size, hatY + size / 4),
                new Point2D.Double(hatX - size, hatY + size));
        path.append(line, false);
        path.closePath();
        Line2D.Double line1 = new Line2D.Double(new Point2D.Double(hatX - size, hatY + size),
                new Point2D.Double(hatX - size + size / 6, hatY + size + size / 6));
        Line2D.Double line2 = new Line2D.Double(new Point2D.Double(hatX - size + size / 6, hatY + size + size / 6),
                new Point2D.Double(hatX - size - size / 6, hatY + size + size / 6));
        Line2D.Double line3 = new Line2D.Double(new Point2D.Double(hatX - size - size / 6, hatY + size + size / 6),
                new Point2D.Double(hatX - size, hatY + size));
        path.append(line1, false);
        path.append(line2, false);
        path.append(line3, false);
        g2.setColor(color);
        g2.fill(arc2D);
        path.append(arc2D, false);
        g2.setColor(Color.BLACK);
        g2.draw(path);
    }

    public String getId() {
        return id;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
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

    public String getHeight() {
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

    public GeneralPath getPath() {
        return path;
    }

    public Arc2D getArc2D() {
        return arc2D;
    }
}