package game.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Menu2SceneController {

    @FXML
    private Button backButton2;

    @FXML
    private Button nextButton2;

    @FXML
    private ImageView scene2;

    @FXML
    void next2(MouseEvent me) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MenuScene3.fxml"));

            Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.setFullScreen(true);
            stage.setResizable(false);
            stage.setTitle("Dice Realms");
            stage.show();
            stage.setFullScreenExitHint("");
            // scene2.setImage(new Image("/MenuScene2.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back2(MouseEvent me) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MenuScene1.fxml"));

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
