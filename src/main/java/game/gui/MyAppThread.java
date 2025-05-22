package game.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class MyAppThread extends Thread {

    @Override
    public void run() {
        // Start JavaFX application
        Application.launch(DiceRealms.class);
    }
}
