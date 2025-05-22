package game.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Menu5SceneController {

    @FXML
    private Button backButton5;

    @FXML
    private Button nextButton5;

    @FXML
    void back5(MouseEvent me) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MenuScene4.fxml"));

            Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.setFullScreenExitHint("");
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.setTitle("Dice Realms");

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void next5(MouseEvent me) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ChoiceOfPlayScene.fxml"));

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
