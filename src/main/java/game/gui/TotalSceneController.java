package game.gui;

import java.net.URL;
import java.util.ResourceBundle;

import game.engine.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TotalSceneController implements Initializable {

    @FXML
    private Label B1;

    @FXML
    private Label B2;

    @FXML
    private Label C1;

    @FXML
    private Label C2;

    @FXML
    private Label G1;

    @FXML
    private Label G2;

    @FXML
    private Label M1;

    @FXML
    private Label M2;

    @FXML
    private Label P1;

    @FXML
    private Label P2;

    @FXML
    private Label R1;

    @FXML
    private Label R2;

    @FXML
    private Label Y1;

    @FXML
    private Label Y2;

    @FXML
    private Label score1;

    @FXML
    private Label score2;

    @FXML
    private Button winner;

    @FXML
    private Button backButton;

    private GUIGameController gameController;

    @FXML
    void backToMarket(MouseEvent me) {
        try {

            Parent root = PlayerNameController.SuperMarketScene;
            Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
            Scene mainScene = SuperMarketController.mainScene;
            stage.setScene(mainScene);
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.setTitle("Dice Realms");

            stage.setFullScreenExitHint("");
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PopUpWindow(MouseEvent e) {
        if (GUIGameController.getGameStatus().getRoundNumber() == 6) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("WHO IS THE WINNER?");
            alert.setHeaderText(null);

            String player1 = GUIGameController.getGameBoard().getPlayer1().getName();
            String player2 = GUIGameController.getGameBoard().getPlayer2().getName();
            int totalP1 = totalScoreP1();
            int totalP2 = totalScoreP2();
            score1.setText(totalP1 + "");
            score2.setText(totalP2 + "");
            alert.setContentText((totalP1 > totalP2 ? player1 : player2) + " !");
            alert.showAndWait();

        }
    }

    public int totalScoreP1() {
        Player p1 = GUIGameController.getGameBoard().getPlayer1();
        int[] scoreP1 = GUIGameController.getGameScore(p1).getScore();
        int totalP1 = 0;
        for (int i = 0; i < scoreP1.length; i++) {
            totalP1 += scoreP1[i];
        }
        int smallestSoFar = scoreP1[0];
        for (int j = 1; j < scoreP1.length; j++) {
            if (scoreP1[j] < smallestSoFar) {
                smallestSoFar = scoreP1[j];
            }
        }
        score1.setText(totalP1 + (smallestSoFar * (GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getElementalCrest())) + "");
        return totalP1 + (smallestSoFar * (GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getElementalCrest()));

    }

    public int totalScoreP2() {
        Player p2 = GUIGameController.getGameBoard().getPlayer2();
        int[] scoreP2 = GUIGameController.getGameScore(p2).getScore();
        int totalP2 = 0;
        for (int i = 0; i < scoreP2.length; i++) {
            totalP2 += scoreP2[i];
        }
        int smallestSoFar = scoreP2[0];
        for (int j = 1; j < scoreP2.length; j++) {
            if (scoreP2[j] < smallestSoFar) {
                smallestSoFar = scoreP2[j];
            }
        }
        score2.setText(totalP2 + (smallestSoFar * (GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getElementalCrest())) + "");
        return totalP2 + (smallestSoFar * (GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getElementalCrest()));

    }

    public void initializeTotalScore() {

        Label redScoreP1 = R1;
        Label greenScoreP1 = G1;
        Label blueScoreP1 = B1;
        Label magentaScoreP1 = M1;
        Label yellowScoreP1 = Y1;
        Label crestsP1 = C1;

        Label redScoreP2 = R2;
        Label greenScoreP2 = G2;
        Label blueScoreP2 = B2;
        Label magentaScoreP2 = M2;
        Label yellowScoreP2 = Y2;
        Label crestsP2 = C2;

        if (redScoreP1 != null)
            redScoreP1
                    .setText((GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getDragon().getScore())
                            + "");
        if (redScoreP2 != null)
            redScoreP2
                    .setText((GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getDragon().getScore())
                            + "");

        if (greenScoreP1 != null)
            greenScoreP1.setText(
                    (GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getGuardians().getScore()) + "");
        if (greenScoreP2 != null)
            greenScoreP2.setText(
                    (GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getGuardians().getScore()) + "");

        if (blueScoreP1 != null)
            blueScoreP1
                    .setText(
                            (GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getHydra().getScore()) + "");
        if (blueScoreP2 != null)
            blueScoreP2
                    .setText(
                            (GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getHydra().getScore()) + "");

        if (magentaScoreP1 != null)
            magentaScoreP1
                    .setText((GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getPhoenix().getScore())
                            + "");
        if (magentaScoreP2 != null)
            magentaScoreP2
                    .setText((GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getPhoenix().getScore())
                            + "");

        if (yellowScoreP1 != null)
            yellowScoreP1
                    .setText((GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getLion().getScore()) + "");
        if (yellowScoreP2 != null)
            yellowScoreP2
                    .setText((GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getLion().getScore()) + "");

        if (crestsP1 != null)
            crestsP1.setText((GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getElementalCrest()) + "");
        if (crestsP2 != null)
            crestsP2.setText((GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getElementalCrest()) + "");

        if(score1 != null)
            score1.setText(totalScoreP1() + "");
        if(score2 != null)
            score2.setText(totalScoreP2() + "");

    }

    public void setGUIGameController(GUIGameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Label redScoreP1 = R1;
        Label greenScoreP1 = G1;
        Label blueScoreP1 = B1;
        Label magentaScoreP1 = M1;
        Label yellowScoreP1 = Y1;
        Label crestsP1 = C1;

        Label redScoreP2 = R2;
        Label greenScoreP2 = G2;
        Label blueScoreP2 = B2;
        Label magentaScoreP2 = M2;
        Label yellowScoreP2 = Y2;
        Label crestsP2 = C2;

        if (redScoreP1 != null)
            redScoreP1
                    .setText((GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getDragon().getScore())
                            + "");
        if (redScoreP2 != null)
            redScoreP2
                    .setText((GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getDragon().getScore())
                            + "");

        if (greenScoreP1 != null)
            greenScoreP1.setText(
                    (GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getGuardians().getScore()) + "");
        if (greenScoreP2 != null)
            greenScoreP2.setText(
                    (GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getGuardians().getScore()) + "");

        if (blueScoreP1 != null)
            blueScoreP1
                    .setText(
                            (GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getHydra().getScore()) + "");
        if (blueScoreP2 != null)
            blueScoreP2
                    .setText(
                            (GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getHydra().getScore()) + "");

        if (magentaScoreP1 != null)
            magentaScoreP1
                    .setText((GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getPhoenix().getScore())
                            + "");
        if (magentaScoreP2 != null)
            magentaScoreP2
                    .setText((GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getPhoenix().getScore())
                            + "");

        if (yellowScoreP1 != null)
            yellowScoreP1
                    .setText((GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getLion().getScore()) + "");
        if (yellowScoreP2 != null)
            yellowScoreP2
                    .setText((GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getLion().getScore()) + "");

        if (crestsP1 != null)
            crestsP1.setText((GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getElementalCrest()) + "");
        if (crestsP2 != null)
            crestsP2.setText((GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getElementalCrest()) + "");

        if(score1 != null)
            score1.setText(totalScoreP1() + "");
        if(score2 != null)
            score2.setText(totalScoreP2() + "");
    }

}
