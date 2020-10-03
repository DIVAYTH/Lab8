package mainCode.GUI;

import java.util.HashMap;

public class AnimationUpdate implements Runnable {
    private AcademicHat academicHat;
    private AcademicHat academicHatUpdate;
    private GUI gui;
    private HashMap<String, AcademicHat> elementsClient;
    private String arg;

    public AnimationUpdate(AcademicHat academicHat, AcademicHat academicHatUpdate, GUI gui, HashMap<String, AcademicHat> elementsClient, String arg) {
        this.academicHat = academicHat;
        this.academicHatUpdate = academicHatUpdate;
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
            elementsClient.put(arg, academicHatUpdate);
            while (academicHatUpdate.getHad().getHeight() < Integer.parseInt(academicHatUpdate.getStudentsCount()) * 1.25) {
                academicHatUpdate.increase();
                gui.getGraphicsPanel().repaint();
                Thread.sleep(25);
            }
            while (academicHatUpdate.getHad().getHeight() > Integer.parseInt(academicHatUpdate.getStudentsCount())) {
                academicHatUpdate.decrease();
                gui.getGraphicsPanel().repaint();
                Thread.sleep(25);
            }
        } catch (InterruptedException e) {
            // Исключение не мешает логике исполнения программы
        }
    }
}