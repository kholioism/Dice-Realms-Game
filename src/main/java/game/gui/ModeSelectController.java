package game.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModeSelectController {

    @FXML
    private Button AIButton;

    @FXML
    private AnchorPane ModeAnchor;

    @FXML
    private Button PVPButton;

    @FXML
    void AINames(MouseEvent event) {
        GUIGameController.setUserInput("AI");
        GUIGameController.setUserInput(null);
    }

    @FXML
    void PvPNames(MouseEvent event) {
        GUIGameController.setUserInput("PvP");
        GUIGameController.setUserInput(null);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/NameOfPlayersScene.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
