package mainCode.GUI;

import java.util.HashMap;

public class AnimationDelete implements Runnable {
    private AcademicHat academicHat;
    private GUI gui;
    private HashMap<String, AcademicHat> elementsClient;
    private String arg;

    public AnimationDelete(AcademicHat academicHat, GUI gui, HashMap<String, AcademicHat> elementsClient, String arg) {
        this.academicHat = academicHat;
        this.gui = gui;
        this.elementsClient = elementsClient;
        this.arg = arg;
    }

    /**
     * Метод создает анимацию при удалении элемента
     */
    @Override
    public void run() {
        try {
            int firstValue = academicHat.getRhombus().ypoints[0] - Integer.parseInt(academicHat.getStudentsCount()) / 4;
            int secondValue = academicHat.getRhombus().ypoints[0];
            while (academicHat.getRhombus().ypoints[0] != firstValue) {
                academicHat.hatUp();
                gui.getGraphicsPanel().repaint();
                Thread.sleep(25);
            }
            while (academicHat.getRhombus().ypoints[0] != secondValue) {
                academicHat.hatDown();
                gui.getGraphicsPanel().repaint();
                Thread.sleep(25);
            }
            elementsClient.remove(arg);
            gui.getGraphicsPanel().repaint();
        } catch (InterruptedException e) {
            // Исключение не мешает логике исполнения программы
        }
    }
}