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
    private Head had;
    private Polygon rhombus;
    private Rope rope;
    private Polygon triangle;
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
        hatX = Integer.parseInt(x) + 250;
        hatY = (int) (Double.parseDouble(y)) + 170;
        try {
            size = Integer.parseInt(studentsCount);
        }catch (NumberFormatException e){
            size = 50;
            // значения по умолчанию
        }
        had = new Head(new Point(hatX, hatY), size, size, 170, 200);
        rhombus = new Polygon(new int[]{hatX + size / 2, hatX + size * 2, hatX + size / 2, hatX - size},
                new int[]{hatY + size / 2, hatY + size / 4, hatY - size / 4, hatY + size / 4}, 4);
        rope = new Rope(new Point(hatX - size, hatY + size / 4),
                new Point(hatX - size, hatY + size));
        triangle = new Polygon(new int[]{hatX - size, hatX - size + size / 6, hatX - size - size / 6},
                new int[]{hatY + size, hatY + size + size / 6, hatY + size + size / 6}, 3);
    }

    /**
     * Метод рисует элементы
     *
     * @param g2
     * @param color
     */
    public void drawHat(Graphics2D g2, Color color) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.fill(rhombus);
        g2.draw(rhombus);

        g2.setColor(color);
        g2.fill(had);
        g2.draw(had);

        g2.setColor(Color.YELLOW);
        g2.fill(rope);
        g2.draw(rope);
        g2.fill(triangle);
        g2.draw(triangle);
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

    public Polygon getRhombus() {
        return rhombus;
    }

    public Head getHad() {
        return had;
    }

    public Polygon getTriangle() {
        return triangle;
    }

    public Rope getRope() {
        return rope;
    }

    /**
     * Метод увеличивает размер объекта
     */
    public void increase() {
        rhombus.xpoints[0]++;
        rhombus.xpoints[1]++;
        rhombus.xpoints[2]++;
        rhombus.ypoints[0]++;
        rhombus.ypoints[1]++;
        rhombus.ypoints[2]++;
        had.increase();
    }

    /**
     * Метод уменьшает размер объекта
     */
    public void decrease() {
        rhombus.xpoints[0]--;
        rhombus.xpoints[1]--;
        rhombus.xpoints[2]--;
        rhombus.ypoints[0]--;
        rhombus.ypoints[1]--;
        rhombus.ypoints[2]--;
        had.decrease();
    }

    /**
     * Метод поднимает шляпу
     */
    public void hatUp() {
        rhombus.ypoints[0]--;
        rhombus.ypoints[1]--;
        rhombus.ypoints[2]--;
        rhombus.ypoints[3]--;
        triangle.ypoints[0]--;
        triangle.ypoints[1]--;
        triangle.ypoints[2]--;
        rope.lineUp();
    }

    /**
     * Метод опускает шляпу
     */
    public void hatDown() {
        rhombus.ypoints[0]++;
        rhombus.ypoints[1]++;
        rhombus.ypoints[2]++;
        rhombus.ypoints[3]++;
        triangle.ypoints[0]++;
        triangle.ypoints[1]++;
        triangle.ypoints[2]++;
        rope.lineDown();
    }

    class Head extends Arc2D {
        private Point point;
        private double w;
        private double h;
        private double start;
        private double extent;

        public Head(Point point, double w, double h, double start, double extent) {
            super(Arc2D.PIE);
            this.point = point;
            this.w = w;
            this.h = h;
            this.start = start;
            this.extent = extent;
        }

        @Override
        public double getAngleStart() {
            return start;
        }

        @Override
        public double getAngleExtent() {
            return extent;
        }

        @Override
        public void setArc(double x, double y, double w, double h, double angSt, double angExt, int closure) {
            point.setLocation(x, y);
            this.w = w;
            this.h = h;
            this.start = angSt;
            this.extent = angExt;
        }

        @Override
        public void setAngleStart(double angSt) {
            this.start = angSt;
        }

        @Override
        public void setAngleExtent(double angExt) {
            this.extent = angExt;
        }

        @Override
        protected Rectangle2D makeBounds(double x, double y, double w, double h) {
            return null;
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

        public void increase() {
            w++;
            h++;
        }

        public void decrease() {
            w--;
            h--;
        }
    }

    class Rope extends Line2D {
        private Point point1;
        private Point point2;

        public Rope(Point point1, Point point2) {
            this.point1 = point1;
            this.point2 = point2;
        }

        @Override
        public double getX1() {
            return point1.x;
        }

        @Override
        public double getY1() {
            return point1.y;
        }

        @Override
        public Point2D getP1() {
            return point1;
        }

        @Override
        public double getX2() {
            return point2.x;
        }

        @Override
        public double getY2() {
            return point2.y;
        }

        @Override
        public Point2D getP2() {
            return point2;
        }

        @Override
        public void setLine(double x1, double y1, double x2, double y2) {
            point1.setLocation(x1, y1);
            point2.setLocation(x2, y2);
        }

        @Override
        public Rectangle2D getBounds2D() {
            return null;
        }

        public void lineUp() {
            point1.y--;
            point2.y--;
        }

        public void lineDown() {
            point1.y++;
            point2.y++;
        }
    }
}