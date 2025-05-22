package game.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Parent root = new Group();
            Parent root = FXMLLoader.load(getClass().getResource("/SuperMarketAIScene.fxml"));
            Scene mainScene = new Scene(root);
            primaryStage.setScene(mainScene);
            primaryStage.setFullScreen(true);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Dice Realms");
            primaryStage.show();
            primaryStage.setFullScreenExitHint("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
