package game.gui;

import java.nio.file.Paths;

import game.dice.Dice;
import game.dice.GreenDice;
import game.dice.RedDice;
import game.engine.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DiceRealms extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/IntroductoryScene.fxml"));
            Scene mainScene = new Scene(root);
            primaryStage.setScene(mainScene);
            primaryStage.initStyle(StageStyle.DECORATED);
            primaryStage.setResizable(true); // Allow resizing
            primaryStage.setMaximized(true); // Start maximized
            primaryStage.show();
            backgroundtrack();

            // Key event to toggle fullscreen mode
            mainScene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case F:
                        primaryStage.setFullScreen(!primaryStage.isFullScreen());
                        break;
                    case ESCAPE:
                        primaryStage.setFullScreen(false);
                        break;
                    default:
                        break;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private MediaPlayer mediaPlayer;

    public void backgroundtrack() {
        try {
            String musicFile = "src\\main\\resources\\audio\\BackgroundMusic.mp3";
            Media media = new Media(Paths.get(musicFile).toUri().toString());
            mediaPlayer = new MediaPlayer(media);// Use class-level mediaPlayer
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.5); // Set volume to 50%
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error loading or playing media file: " + e.getMessage());
        }
    }
}
