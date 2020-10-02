package mainCode.GUI;

import java.awt.geom.Arc2D;

public class AnimationAdd implements Runnable {
    private Arc2D path;
    private GUI gui;

    public AnimationAdd(Arc2D path, GUI gui) {
        this.path = path;
        this.gui = gui;
    }

    /**
     * Метод создает анимацию при добавлении элемента
     */
    @Override
    public void run() {
        try {
            while (path.getHeight() < path.getHeight() * 1.25) {
                path.getBounds().x++;
                path.getBounds().y++;
                gui.getGraphicsPanel().repaint();
                Thread.sleep(25);
            }
            while (path.getHeight() > path.getHeight()) {
                path.getBounds().x--;
                path.getBounds().y--;
                gui.getGraphicsPanel().repaint();
                Thread.sleep(25);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

