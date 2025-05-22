package game.gui;

import java.net.URL;
import java.util.ResourceBundle;

import game.creatures.Lion;
import game.dice.Dice;
import game.dice.GreenDice;
import game.dice.MagentaDice;
import game.dice.YellowDice;
import game.engine.Move;
import game.engine.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ScoreSheetSceneController implements Initializable {

    @FXML
    private Label GP1;

    @FXML
    private Label GP2;

    @FXML
    private Label HP1;

    @FXML
    private Label HP1R1;

    @FXML
    private Label HP1R10;

    @FXML
    private Label HP1R11;

    @FXML
    private Label HP1R2;

    @FXML
    private Label HP1R3;

    @FXML
    private Label HP1R4;

    @FXML
    private Label HP1R5;

    @FXML
    private Label HP1R6;

    @FXML
    private Label HP1R7;

    @FXML
    private Label HP1R8;

    @FXML
    private Label HP1R9;

    @FXML
    private Label HP2;

    @FXML
    private Label HP2R1;

    @FXML
    private Label HP2R10;

    @FXML
    private Label HP2R11;

    @FXML
    private Label HP2R2;

    @FXML
    private Label HP2R3;

    @FXML
    private Label HP2R4;

    @FXML
    private Label HP2R5;

    @FXML
    private Label HP2R6;

    @FXML
    private Label HP2R7;

    @FXML
    private Label HP2R8;

    @FXML
    private Label HP2R9;

    @FXML
    private Label LP1;

    @FXML
    private Label LP1R1;

    @FXML
    private Label LP1R10;

    @FXML
    private Label LP1R11;

    @FXML
    private Label LP1R111;

    @FXML
    private Label LP1R1111;

    @FXML
    private Label LP1R11111;

    @FXML
    private Label LP1R111111;

    @FXML
    private Label LP1R1111111;

    @FXML
    private Label LP1R11111111;

    @FXML
    private Label LP1R111111111;

    @FXML
    private Label LP1R1111111111;

    @FXML
    private Label LP1R2;

    @FXML
    private Label LP1R3;

    @FXML
    private Label LP1R4;

    @FXML
    private Label LP1R5;

    @FXML
    private Label LP1R6;

    @FXML
    private Label LP1R7;

    @FXML
    private Label LP1R8;

    @FXML
    private Label LP1R9;

    @FXML
    private Label LP2;

    @FXML
    private Label LP2R1;

    @FXML
    private Label LP2R10;

    @FXML
    private Label LP2R11;

    @FXML
    private Label LP2R2;

    @FXML
    private Label LP2R3;

    @FXML
    private Label LP2R4;

    @FXML
    private Label LP2R5;

    @FXML
    private Label LP2R6;

    @FXML
    private Label LP2R7;

    @FXML
    private Label LP2R8;

    @FXML
    private Label LP2R9;

    @FXML
    private Label P1D10;

    @FXML
    private Label P1D11;

    @FXML
    private Label P1D12;

    @FXML
    private Label P1D13;

    @FXML
    private Label P1D20;

    @FXML
    private Label P1D21;

    @FXML
    private Label P1D23;

    @FXML
    private Label P1D26;

    @FXML
    private Label P1D30;

    @FXML
    private Label P1D32;

    @FXML
    private Label P1D34;

    @FXML
    private Label P1D35;

    @FXML
    private Label P1D40;

    @FXML
    private Label P1D44;

    @FXML
    private Label P1D45;

    @FXML
    private Label P1D46;

    @FXML
    private Label P1DRD;

    @FXML
    private Label P1DRF;

    @FXML
    private Label P1DRH;

    @FXML
    private Label P1DRT;

    @FXML
    private Label P1DRW;

    @FXML
    private Label P1G0;

    @FXML
    private Label P1G10;

    @FXML
    private Label P1G11;

    @FXML
    private Label P1G12;

    @FXML
    private Label P1G2;

    @FXML
    private Label P1G3;

    @FXML
    private Label P1G4;

    @FXML
    private Label P1G5;

    @FXML
    private Label P1G6;

    @FXML
    private Label P1G7;

    @FXML
    private Label P1G8;

    @FXML
    private Label P1G9;

    @FXML
    private Label P1GC1;

    @FXML
    private Label P1GC2;

    @FXML
    private Label P1GC3;

    @FXML
    private Label P1GC4;

    @FXML
    private Label P1GR1;

    @FXML
    private Label P1GR2;

    @FXML
    private Label P1GR3;

    @FXML
    private Label P1H1;

    @FXML
    private Label P1H10;

    @FXML
    private Label P1H11;

    @FXML
    private Label P1H2;

    @FXML
    private Label P1H3;

    @FXML
    private Label P1H4;

    @FXML
    private Label P1H5;

    @FXML
    private Label P1H6;

    @FXML
    private Label P1H7;

    @FXML
    private Label P1H8;

    @FXML
    private Label P1H9;

    @FXML
    private Label P1L1;

    @FXML
    private Label P1L10;

    @FXML
    private Label P1L11;

    @FXML
    private Label P1L2;

    @FXML
    private Label P1L3;

    @FXML
    private Label P1L4;

    @FXML
    private Label P1L5;

    @FXML
    private Label P1L6;

    @FXML
    private Label P1L7;

    @FXML
    private Label P1L8;

    @FXML
    private Label P1L9;

    @FXML
    private Label P1P1;

    @FXML
    private Label P1P10;

    @FXML
    private Label P1P11;

    @FXML
    private Label P1P2;

    @FXML
    private Label P1P3;

    @FXML
    private Label P1P4;

    @FXML
    private Label P1P5;

    @FXML
    private Label P1P6;

    @FXML
    private Label P1P7;

    @FXML
    private Label P1P8;

    @FXML
    private Label P1P9;

    @FXML
    private Label P2D10;

    @FXML
    private Label P2D11;

    @FXML
    private Label P2D12;

    @FXML
    private Label P2D13;

    @FXML
    private Label P2D20;

    @FXML
    private Label P2D21;

    @FXML
    private Label P2D23;

    @FXML
    private Label P2D26;

    @FXML
    private Label P2D30;

    @FXML
    private Label P2D32;

    @FXML
    private Label P2D34;

    @FXML
    private Label P2D35;

    @FXML
    private Label P2D40;

    @FXML
    private Label P2D44;

    @FXML
    private Label P2D45;

    @FXML
    private Label P2D46;

    @FXML
    private Label P2DRD;

    @FXML
    private Label P2DRF;

    @FXML
    private Label P2DRH;

    @FXML
    private Label P2DRT;

    @FXML
    private Label P2DRW;

    @FXML
    private Label P2G0;

    @FXML
    private Label P2G10;

    @FXML
    private Label P2G11;

    @FXML
    private Label P2G12;

    @FXML
    private Label P2G2;

    @FXML
    private Label P2G3;

    @FXML
    private Label P2G4;

    @FXML
    private Label P2G5;

    @FXML
    private Label P2G6;

    @FXML
    private Label P2G7;

    @FXML
    private Label P2G8;

    @FXML
    private Label P2G9;

    @FXML
    private Label P2GC1;

    @FXML
    private Label P2GC2;

    @FXML
    private Label P2GC3;

    @FXML
    private Label P2GC4;

    @FXML
    private Label P2GR1;

    @FXML
    private Label P2GR2;

    @FXML
    private Label P2GR3;

    @FXML
    private Label P2H1;

    @FXML
    private Label P2H10;

    @FXML
    private Label P2H11;

    @FXML
    private Label P2H2;

    @FXML
    private Label P2H3;

    @FXML
    private Label P2H4;

    @FXML
    private Label P2H5;

    @FXML
    private Label P2H6;

    @FXML
    private Label P2H7;

    @FXML
    private Label P2H8;

    @FXML
    private Label P2H9;

    @FXML
    private Label P2L1;

    @FXML
    private Label P2L10;

    @FXML
    private Label P2L11;

    @FXML
    private Label P2L2;

    @FXML
    private Label P2L3;

    @FXML
    private Label P2L4;

    @FXML
    private Label P2L5;

    @FXML
    private Label P2L6;

    @FXML
    private Label P2L7;

    @FXML
    private Label P2L8;

    @FXML
    private Label P2L9;

    @FXML
    private Label P2P1;

    @FXML
    private Label P2P10;

    @FXML
    private Label P2P11;

    @FXML
    private Label P2P2;

    @FXML
    private Label P2P3;

    @FXML
    private Label P2P4;

    @FXML
    private Label P2P5;

    @FXML
    private Label P2P6;

    @FXML
    private Label P2P7;

    @FXML
    private Label P2P8;

    @FXML
    private Label P2P9;

    @FXML
    private Label PP1;

    @FXML
    private Label PP1R1;

    @FXML
    private Label PP1R10;

    @FXML
    private Label PP1R11;

    @FXML
    private Label PP1R2;

    @FXML
    private Label PP1R3;

    @FXML
    private Label PP1R4;

    @FXML
    private Label PP1R5;

    @FXML
    private Label PP1R6;

    @FXML
    private Label PP1R7;

    @FXML
    private Label PP1R8;

    @FXML
    private Label PP1R9;

    @FXML
    private Label PP2;

    @FXML
    private Label PP2R1;

    @FXML
    private Label PP2R10;

    @FXML
    private Label PP2R11;

    @FXML
    private Label PP2R2;

    @FXML
    private Label PP2R3;

    @FXML
    private Label PP2R4;

    @FXML
    private Label PP2R5;

    @FXML
    private Label PP2R6;

    @FXML
    private Label PP2R7;

    @FXML
    private Label PP2R8;

    @FXML
    private Label PP2R9;

    @FXML
    private Button backButton;

    @FXML
    void backSuperMarket(MouseEvent me) {
        try {
            Parent root = PlayerNameController.SuperMarketScene;
            Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
            Scene mainScene = SuperMarketController.mainScene;
            stage.setScene(mainScene);
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.setTitle("Dice Realms");

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hitAny() {
        YellowDice d = new YellowDice();
        d.setValue(6);
        if (!d.isUsed()) {
            Player p = GUIGameController.getActivePlayer();
            Lion l = GUIGameController.getActivePlayer().getScoreSheet().getLion();
            Move m = new Move(d, l);
            GUIGameController.makeMove(p, m);
            System.out.println("hellooooz");

        }

    }

    // For Green
    // Scores
    public void initializeGreenScores() {

        Label[][] greenScoreLabelsP1 = { { P1G0, P1G2, P1G3, P1G4 }, { P1G5, P1G6, P1G7, P1G8 },
                { P1G9, P1G10, P1G11, P1G12 } };
        Label[][] greenScoreLabelsP2 = { { P2G0, P2G2, P2G3, P2G4 }, { P2G5, P2G6, P2G7, P2G8 },
                { P2G9, P2G10, P2G11, P2G12 } };
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 4; b++) {
                if (greenScoreLabelsP1[a][b] != null)
                    greenScoreLabelsP1[a][b].setText(GUIGameController.getGameBoard().getPlayer1().getScoreSheet()
                            .getGuardians().getguardians_hitValues()[a][b]);
                else
                    System.out.println("Label at [" + a + "][" + b + "] is null.");
                if (greenScoreLabelsP2[a][b] != null)
                    greenScoreLabelsP2[a][b].setText(GUIGameController.getGameBoard().getPlayer2().getScoreSheet()
                            .getGuardians().getguardians_hitValues()[a][b]);
                else
                    System.out.println("Label at [" + a + "][" + b + "] is null.");
            }
        }
    }

    // Rewards
    public void initializeGreenRewards() {
        Label[] greenRewardLabelsP1 = { P1GR1, P1GR2, P1GR3, P1GC1, P1GC2, P1GC3, P1GC4 };
        Label[] greenRewardLabelsP2 = { P2GR1, P2GR2, P2GR3, P2GC1, P2GC2, P2GC3, P2GC4 };
        for (int e = 0; e < 7; e++) {
            greenRewardLabelsP1[e]
                    .setText(
                            GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getGuardians().getBonus()[e]);
            greenRewardLabelsP2[e]
                    .setText(
                            GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getGuardians().getBonus()[e]);
        }
    }

    // For Red
    // Scores
    public void initializeRedScores() {
        Label[][] redScoreLabelsP1 = { { P1D13, P1D12, P1D11, P1D10 }, { P1D26, P1D21, P1D20, P1D23 },
                { P1D35, P1D30, P1D32, P1D34 }, { P1D40, P1D45, P1D44, P1D46 } };

        Label[][] redScoreLabelsP2 = { { P2D13, P2D12, P2D11, P2D10 }, { P2D26, P2D21, P2D20, P2D23 },
                { P2D35, P2D30, P2D32, P2D34 }, { P2D40, P2D45, P2D44, P2D46 } };
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int p1Score = GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getDragon()
                        .getDragonHitBoard()[i][j];
                int p2Score = GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getDragon()
                        .getDragonHitBoard()[i][j];
                if (p1Score == 0)
                    redScoreLabelsP1[i][j].setText("X");
                else
                    redScoreLabelsP1[i][j].setText(p1Score + "");

                if (p2Score == 0)
                    redScoreLabelsP2[i][j].setText("X");
                else
                    redScoreLabelsP2[i][j].setText(p2Score + "");

            }
        }
    }

    // Rewards
    public void initializeRedRewards() {
        Label[] redRewardLabelsP1 = { P1DRF, P1DRW, P1DRT, P1DRH, P1DRD };
        Label[] redRewardLabelsP2 = { P2DRF, P2DRW, P2DRT, P2DRH, P2DRD };
        for (int z = 0; z < 5; z++) {
            boolean p1Bonus1 = GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getDragon()
                    .getBonusObtained()[z];
            boolean p2Bonus2 = GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getDragon()
                    .getBonusObtained()[z];

            String p1Bonus = GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getDragon()
                    .getBonusArray()[z];
            String p2Bonus = GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getDragon()
                    .getBonusArray()[z];

            if (p1Bonus1 == true)
                redRewardLabelsP1[z].setText("X");
            else
                redRewardLabelsP1[z].setText(p1Bonus);

            if (p2Bonus2 == true)
                redRewardLabelsP2[z].setText("X");
            else
                redRewardLabelsP2[z].setText(p2Bonus);
        }
    }

    public void initializeYellowBlueMagentaRewards() {
        Label[] yellowRewardLabelsP1 = { LP1R1, LP1R2, LP1R3, LP1R4, LP1R5, LP1R6, LP1R7, LP1R8, LP1R9, LP1R10,
                LP1R11 };
        Label[] yellowRewardLabelsP2 = { LP2R1, LP2R2, LP2R3, LP2R4, LP2R5, LP2R6, LP2R7, LP2R8, LP2R9, LP2R10,
                LP2R11 };

        Label[] blueRewardLabelsP1 = { HP1R1, HP1R2, HP1R3, HP1R4, HP1R5, HP1R6, HP1R7, HP1R8, HP1R9, HP1R10, HP1R11 };
        Label[] blueRewardLabelsP2 = { HP2R1, HP2R2, HP2R3, HP2R4, HP2R5, HP2R6, HP2R7, HP2R8, HP2R9, HP2R10, HP2R11 };

        Label[] magentaRewardLabelsP1 = { PP1R1, PP1R2, PP1R3, PP1R4, PP1R5, PP1R6, PP1R7, PP1R8, PP1R9, PP1R10,
                PP1R11 };
        Label[] magentRewardLabelsP2 = { PP2R1, PP2R2, PP2R3, PP2R4, PP2R5, PP2R6, PP2R7, PP2R8, PP2R9, PP2R10,
                PP2R11 };
        for (int w = 0; w < 11; w++) {
            yellowRewardLabelsP1[w]
                    .setText(GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getLion().getBonus()[w]);
            yellowRewardLabelsP2[w]
                    .setText(GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getLion().getBonus()[w]);
            blueRewardLabelsP1[w]
                    .setText(GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getHydra().getBonus()[w]);
            blueRewardLabelsP2[w]
                    .setText(GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getHydra().getBonus()[w]);

            magentaRewardLabelsP1[w]
                    .setText(GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getPhoenix().getBonus()[w]);
            magentRewardLabelsP2[w]
                    .setText(GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getPhoenix().getBonus()[w]);
        }
    }

    public void initializeYellowBlueMagentaScores() {
        Label[] yellowAttacksP1 = { P1L1, P1L2, P1L3, P1L4, P1L5, P1L6, P1L7, P1L8, P1L9, P1L10, P1L11 };
        Label[] yellowAttacksP2 = { P2L1, P2L2, P2L3, P2L4, P2L5, P2L6, P2L7, P2L8, P2L9, P2L10, P2L11 };
        Label[] magentaAttacksP1 = { P1P1, P1P2, P1P3, P1P4, P1P5, P1P6, P1P7, P1P8, P1P9, P1P10, P1P11 };
        Label[] magentaAttacksP2 = { P2P1, P2P2, P2P3, P2P4, P2P5, P2P6, P2P7, P2P8, P2P9, P2P10, P2P11 };
        Label[] blueAttacksP1 = { P1H1, P1H2, P1H3, P1H4, P1H5, P1H6, P1H7, P1H8, P1H9, P1H10, P1H11 };
        Label[] blueAttacksP2 = { P2H1, P2H2, P2H3, P2H4, P2H5, P2H6, P2H7, P2H8, P2H9, P2H10, P2H11 };

        for (int w = 0; w < 11; w++) {
            if (GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getLion().getLion2()[w] != 0)
                yellowAttacksP1[w].setText(GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getLion().getLion2()[w] + "");

            String s = GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getHydra().getDefeatedHead()[w];
            String s2 = "---";
            if (!s.equals(s2)) {
                blueAttacksP1[w].setText(GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getHydra().getDefeatedHead()[w]);
            }

            if (GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getPhoenix().getChosenDices()[w] != 0)
                magentaAttacksP1[w].setText(GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getPhoenix().getChosenDices()[w]+ "");

            if (GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getLion().getLion2()[w] != 0)
                yellowAttacksP2[w].setText(GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getLion().getLion2()[w] + "");

            String s3 = GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getHydra().getDefeatedHead()[w];
            if (!s3.equals("---"))
                blueAttacksP2[w].setText(GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getHydra().getDefeatedHead()[w]);

            if (GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getPhoenix().getChosenDices()[w] != 0)
                magentaAttacksP2[w].setText(GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getPhoenix().getChosenDices()[w] + "");

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeGreenScores();
        initializeGreenRewards();
        initializeRedScores();
        initializeRedRewards();
        initializeYellowBlueMagentaRewards();
        initializeYellowBlueMagentaScores();
    }
}
