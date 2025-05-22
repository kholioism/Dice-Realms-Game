package game.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class IntroSceneController {

    @FXML
    private Button MenuButton;

    @FXML
    private Button StartButton;

    @FXML
    private AnchorPane anchorPaneID;

    @FXML
    void menu(MouseEvent me) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MenuScene1.fxml"));

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
    void start(MouseEvent me) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ChoiceOfPlayScene.fxml"));

            Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.setTitle("Dice Realms");
            stage.show();
            stage.setFullScreenExitHint("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
