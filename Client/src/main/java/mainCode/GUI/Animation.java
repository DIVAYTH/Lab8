package mainCode.GUI;

public class Animation implements Runnable {
    private GroupCircle circle;
    private GUI gui;

    public Animation(GroupCircle circle, GUI gui) {
        this.circle = circle;
        this.gui = gui;
    }

    /**
     * Метод создает анимацию при добавлении элемента
     */
    @Override
    public void run() {
        try {
            while (circle.getHeight() < Double.parseDouble(circle.getStudentsCount()) * 1.25) {
                circle.increase();
                gui.getGraphicsPanel().repaint();
                Thread.sleep(25);
            }
            while (circle.getHeight() > Double.parseDouble(circle.getStudentsCount())) {
                circle.decrease();
                gui.getGraphicsPanel().repaint();
                Thread.sleep(25);
            }
        } catch (InterruptedException e) {
        }
    }
}