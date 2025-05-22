package game.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Menu1SceneController {

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private ImageView image1;

    @FXML
    public void next1(MouseEvent me) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MenuScene2.fxml"));

            Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.setTitle("Dice Realms");
            stage.setFullScreenExitHint("");

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void back1(MouseEvent me) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/IntroductoryScene.fxml"));

            Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.setTitle("Dice Realms");
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}