package mainCode.GUI;

public class AnimationAdd implements Runnable {
    private AcademicHat academicHat;
    private GUI gui;

    public AnimationAdd(AcademicHat academicHat, GUI gui) {
        this.academicHat = academicHat;
        this.gui = gui;
    }

    /**
     * Метод создает анимацию при добавлени  элемента
     */
    @Override
    public void run() {
        try {
            while (academicHat.getHad().getHeight() < Integer.parseInt(academicHat.getStudentsCount()) * 1.25) {
                academicHat.increase();
                gui.getGraphicsPanel().repaint();
                Thread.sleep(25);
            }
            while (academicHat.getHad().getHeight() > Integer.parseInt(academicHat.getStudentsCount())) {
                academicHat.decrease();
                gui.getGraphicsPanel().repaint();
                Thread.sleep(25);
            }
        } catch (InterruptedException e) {
            // Исключение не мешает логике исполнения программы
        }
    }
}