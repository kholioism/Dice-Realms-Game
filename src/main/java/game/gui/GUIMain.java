package game.gui;

public class GUIMain {

    public static void main(String[] args) {
        // Start the application thread
        MyAppThread appThread = new MyAppThread();
        appThread.start();

        // Start the controller thread
        GUIGameController controllerThread = new GUIGameController();
        controllerThread.start();
    }
}

