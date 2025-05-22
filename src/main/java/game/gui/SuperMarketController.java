package game.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import game.engine.TurnShift;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static game.gui.GUIGameController.arcaneTime;
import static game.gui.GUIGameController.currentPlayer;
import static game.gui.GUIGameController.getAllPossibleMoves;
import static game.gui.GUIGameController.notBonus;

import java.net.URL;

import java.util.ResourceBundle;

import game.engine.Move;
import game.engine.Player;
import game.exceptions.InvalidDiceSelectionException;
import game.exceptions.PlayerActionException;
import game.exceptions.invalidNameException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.*;

public class SuperMarketController implements Initializable {

    public static Player currentPlayer;

    public static Player PlayerONE;

    public static Player PlayerTWO;

    public static boolean arcaneBoostChosen;

    public static int comboBoxIndex;
    @FXML
    private Label RedValue;

    @FXML
    private Button YesButton;

    @FXML
    private Button NoButton;

    @FXML
    private Button WhiteDice;

    @FXML
    private Label WhiteValue;

    @FXML
    private Button YellowDice;

    @FXML
    private Button scoreSheetButton;

    @FXML
    private Button submitBUTTON;

    @FXML
    private Button BlueDice;

    @FXML
    private Label BlueValue;

    @FXML
    private GridPane DiceValues;

    @FXML
    private ImageView ElementalCrest;

    @FXML
    private Label ElementalCrestCounter;

    @FXML
    private Label ElementalCrestCounter2; // check with farah

    @FXML
    private AnchorPane SuperMarketAnchor;

    @FXML
    private Button ExitButton;

    @FXML
    private Button GreenDice;

    @FXML
    private Label GreenValue;
    public static boolean rolled = false;

    @FXML
    private Button MagentaDice;

    @FXML
    private Label MagentaValue;
    @FXML
    private ImageView elementalCrest;

    @FXML
    private Button RollButton;

    @FXML
    private Button ScoreSheet; // check

    @FXML
    private ImageView timeWarp;

    private static final Exception InvalidDiceSelectionException = null;

    @FXML
    private Button totalScore;
    public static Player currentPlayerCopy;

    @FXML
    public Label PlayerName; // check

    // ya gamoosa el condition is opposite of what u are supposed to check
    // u shouldcheck if they switched msh if they are still the same
    public void switchPlayer() {
        boolean arcaneBoostTime = GUIGameController.arcaneTime;
        TurnShift turn = GUIGameController.getGameBoard().getStatus().getTurn();
        String x = turn + "";
        if (x.equals("P2PT") || x.equals("P1PT")) {
            if (x.equals("P2PT")) { // if the current is 1 he needs to change to two
                if (GUIGameController.arcaneTime && arcaneBoostChosen1 == true) {// triggered by the yes button of the
                                                                                 // arcane boost yes button
                    setArcaneDice();
                    rolled = true;
                    rollButton.setDisable(true);
                    rollButton.setDisable(GUIGameController.arcaneTime);

                    if (arcaneBoostChosen2 == true) {
                        arcaneBoostChosen1 = false;
                        arcaneBoostChosen2 = false;
                    }
                } else {
                    currentPlayer = GUIGameController.getGameBoard().getPlayer2();// change currentplayer to player 2
                    arcaneBoostChosen = false;
                    PlayerName.setText(PlayerTWO.getName());
                    passive = true;
                    timeWarpInitialize(currentPlayer);
                    arcaneBoostInitialize(currentPlayer);
                    // TODO: E3MELY HESAB EL NO POSSIBLE DICE
                    disableAllDiceWithOpacity();
                    rollButton.setDisable(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                    pause.setOnFinished(event -> switchDice()); // Execute the second method after delay
                    pause.play();
                }
            } else if (x.equals("P1PT")) { // if the current is 1 he needs to change to two

                if (GUIGameController.arcaneTime && arcaneBoostChosen1 == true) {// triggered by the yes button of the
                                                                                 // arcane boost yes button
                    setArcaneDice();
                    rolled = true;
                    rollButton.setDisable(true);
                    rollButton.setDisable(GUIGameController.arcaneTime);

                    if (arcaneBoostChosen2 == true) {
                        arcaneBoostChosen1 = false;
                        arcaneBoostChosen2 = false;
                    }
                } else {
                    currentPlayer = GUIGameController.getGameBoard().getPlayer1();// change currentplayer to player 2
                    arcaneBoostChosen = false;
                    PlayerName.setText(PlayerONE.getName());
                    passive = true;
                    timeWarpInitialize(currentPlayer);
                    arcaneBoostInitialize(currentPlayer);
rollButton.setDisable(true);
                    disableAllDiceWithOpacity();
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                    pause.setOnFinished(event -> switchDice()); // Execute the second method after delay
                    pause.play();
                }
            }
        } else if (x.equals("P1AT1") && firstRoundPlayed) {
            currentPlayer = GUIGameController.getGameBoard().getPlayer1();// change currentplayer to player 2
            if (GUIGameController.arcaneTime && arcaneBoostChosen1 == true) {// triggered by the yes button of the
                                                                             // arcane boost yes button
                setArcaneDice();
                rolled = true;
                rollButton.setDisable(true);
                rollButton.setDisable(GUIGameController.arcaneTime);

                if (arcaneBoostChosen2 == true) {
                    arcaneBoostChosen1 = false;
                    arcaneBoostChosen2 = false;
                }

            } else {
                arcaneBoostChosen = false;

                PlayerName.setText(PlayerONE.getName());
                passive = false;
                timeWarpInitialize(currentPlayer);
                arcaneBoostInitialize(currentPlayer);
                resetAllDice();
                rollButton.setDisable(false);
                rolled = false;
            }

        } else if (x.equals("P2AT1")) {
            if (GUIGameController.arcaneTime && arcaneBoostChosen1 == true) {
                setArcaneDice();
                rolled = true;
                rollButton.setDisable(true);
                if (arcaneBoostChosen2 == true) {
                    arcaneBoostChosen1 = false;
                    arcaneBoostChosen2 = false;
                }
            } else {
                currentPlayer = GUIGameController.getGameBoard().getPlayer2();// change currentplayer to player 2
                PlayerName.setText(PlayerTWO.getName());
                passive = false;
                timeWarpInitialize(currentPlayer);
                arcaneBoostInitialize(currentPlayer);
                resetAllDice();
                arcaneBoostChosen = false;

                rolled = false;
                rollButton.setDisable(false);
            }

            // TODO: E3MELY HESAB EL NO POSSIBLE DICE

        }
    }

    @FXML
    private ComboBox<Move> myComboBox;
    @FXML
    private ImageView P1AB1;

    @FXML
    private ImageView P1AB2;

    @FXML
    private ImageView P1AB3;

    @FXML
    private ImageView P1AB4;

    @FXML
    private ImageView P1AB5;

    @FXML
    private ImageView P1AB6;

    @FXML
    private ImageView P1AB7;

    @FXML
    private ImageView P1TW1;

    @FXML
    private ImageView P1TW2;

    @FXML
    private ImageView P1TW3;

    @FXML
    private ImageView P1TW4;

    @FXML
    private ImageView P1TW5;

    @FXML
    private ImageView P1TW6;

    @FXML
    private ImageView P1TW7;

    @FXML
    private ImageView P2AB1;

    @FXML
    private ImageView P2AB2;

    @FXML
    private ImageView P2AB3;

    @FXML
    private ImageView P2AB4;

    @FXML
    private ImageView P2AB5;

    @FXML
    private ImageView P2AB6;

    @FXML
    private ImageView P2AB7;

    @FXML
    private ImageView P2TW1;

    @FXML
    private ImageView P2TW2;

    @FXML
    private ImageView P2TW3;

    @FXML
    private ImageView P2TW4;

    @FXML
    private ImageView P2TW5;

    @FXML
    private ImageView P2TW6;

    @FXML
    private ImageView P2TW7;

    @FXML
    private Label YellowValue;

    @FXML
    private TextArea messageArea;

    // @FXML Label PlayerName;

    @FXML
    private Button rollButton;

    @FXML
    private TextField myTextField;

    @FXML
    Button RedDice;

    @FXML
    private Button totalScoreButton;

    @FXML
    private Label UserComm;

    public static boolean timeWarpDecided = false;

    public boolean passive;
    public boolean arcaneBoostChosen1;
    public boolean arcaneBoostChosen2;

    @FXML
    void yesButtonMethod(MouseEvent me) {
        if (GUIGameController.arcaneTime && !arcaneBoostChosen) {
            GUIGameController.setUserInput("y");
            arcaneBoostChosen = true;
            if (!arcaneBoostChosen1 && !arcaneBoostChosen2)
                arcaneBoostChosen1 = true;
            else
                arcaneBoostChosen2 = true;
            // resetAllDice();
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> arcaneBoostMethod()); // Execute the second method after delay
            pause.play();

            // arcaneLightUp();
            arcaneBoostChosen = true;
            PlayerName.setText(GUIGameController.currentPlayer.getName());
            timeWarpInitialize(GUIGameController.currentPlayer);
            arcaneBoostInitialize(GUIGameController.currentPlayer);
            dicepicked = false;
            switchPlayer();
        } else if (GUIGameController.TimeWarpTime) {
            timeWarpDecided = true;
            rolled = false;
            timeWarpInitialize(GUIGameController.currentPlayer);
            GUIGameController.setUserInput("y");
            rollButton.setDisable(false);
        }

    }

    // public void arcaneLightUp() {
    // Dice[] arcaneDice = GUIGameController.getArcaneBoostDice();
    // Button[] diceButtons = { RedDice, GreenDice, BlueDice, MagentaDice,
    // YellowDice, WhiteDice };
    // Label[] diceLabels = { RedValue, GreenValue, BlueValue, MagentaValue,
    // YellowValue, WhiteValue };
    // String hoverStyle = "-fx-background-color: #ffff00; -fx-text-fill: #000000;
    // -fx-border-color: #ffa500; -fx-border-width: 2px; -fx-font-weight: bold;
    // -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);";

    // for (int i = 0; i < diceLabels.length; i++) {
    // diceButtons[i].setStyle(hoverStyle);
    // diceLabels[i].setText(arcaneDice[i].getValue() + "");
    // diceButtons[i].setDisable(arcaneTime);
    // }
    // }

    @FXML
    void noButtonMethod(MouseEvent me) {

        if (GUIGameController.TimeWarpTime) {

            timeWarpDecided = true;
            rolled = true;
            timeWarpInitialize(GUIGameController.currentPlayer);
            GUIGameController.setUserInput("n");

        }
        if (GUIGameController.arcaneTime) {
            GUIGameController.setUserInput("n");
            arcaneBoostChosen = false;
            if (!arcaneBoostChosen1 && !arcaneBoostChosen2)
                arcaneBoostChosen1 = true;
            else
                arcaneBoostChosen2 = true;
            PlayerName.setText(GUIGameController.currentPlayer.getName());
            timeWarpInitialize(GUIGameController.currentPlayer);
            arcaneBoostInitialize(GUIGameController.currentPlayer);
            // if
            // (GUIGameController.getGameStatus().equals(TurnShift.P2PT)||GUIGameController.getGameStatus().equals(TurnShift.P1PT))
            // {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> switchPlayer()); // Execute the second method after delay
            pause.play();
            // }
            dicepicked = false;
        }
        if (!GUIGameController.notBonus && GUIGameController.theKindOfBonus == "Bonus") {
            disableAllDice();
            try {
                switch (GUIGameController.theColourOfBonus) {
                    case ("RED"):
                        handleClick("Dragon");
                        break;
                    case ("GREEN"):
                        handleClick("Guardians");
                        break;
                    case ("BLUE"):
                        handleClick("Hydra");
                        break;
                    case ("MAGENTA"):
                        handleClick("Phoenix");
                        break;
                    case ("YELLOW"):
                        handleClick("Lion");
                        break;
                    case ("WHITE"):
                        handleClick("ArcanePrism");
                        break;
                }

                enableNotUsed();
                forgetDice();

            } catch (InvalidDiceSelectionException e) {
                handleInvalidDiceSelectionException(e);
            }

        // switchPlayer();}
        // if (GUIGameController.getGameStatus().equals(TurnShift.P2PT))
        // switchDice();

    }
    }
    public boolean firstRoundPlayed;

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        PlayerONE = GUIGameController.getGameBoard().getPlayer1();
        PlayerTWO = GUIGameController.getGameBoard().getPlayer2();
        rollButton.setDisable(false);
        timeWarpInitialize(PlayerONE);
        arcaneBoostInitialize(PlayerONE);
        PlayerName.setText(GUIGameController.getGameBoard().getPlayer1().getName());
        rolled = false;
        // TODO: this should happpen smwhere else
        // rollButton.setDisable(arcaneBoostTime);

    }

    public void submitNext(MouseEvent me) {
        try {
            firstRoundPlayed = true;
            if (myComboBox.getValue() == null)

            {
                throw new PlayerActionException();
            } else {
                GUIGameController.setMoveToBeMade(myComboBox.getValue());
                dicepicked = false;
                clearComboBoxOptions();
                // if i put swithplayer here there is nisynchronization with the thread yay

                rolled = false;

                if (passive)
                    passive = false;
                rollButton.setDisable(true);

                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> switchPlayer()); // Execute the second method after delay
                pause.play();
                if (GUIGameController.getGameStatus().equals(TurnShift.P2PT)
                        || GUIGameController.getGameStatus().equals(TurnShift.P1PT)) {
                    pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event -> switchDice()); // Execute the second method after delay
                    pause.play();
                }

                Player p = GUIGameController.currentPlayer;
                currentPlayer = p;

                if (!GUIGameController.notBonus && GUIGameController.theKindOfBonus == "Bonus") {
                    disableAllDice();
                    try {
                        switch (GUIGameController.theColourOfBonus) {
                            case ("RED"):
                                handleClick("Dragon");
                                break;
                            case ("GREEN"):
                                handleClick("Guardians");
                                break;
                            case ("BLUE"):
                                handleClick("Hydra");
                                break;
                            case ("MAGENTA"):
                                handleClick("Phoenix");
                                break;
                            case ("YELLOW"):
                                handleClick("Lion");
                                break;
                            case ("WHITE"):
                                handleClick("ArcanePrism");
                                break;
                        }

                        enableNotUsed();
                        forgetDice();

                    } catch (InvalidDiceSelectionException e) {
                        handleInvalidDiceSelectionException(e);
                    }

                } else {
                    rollButton.setDisable(false);
                    if (GUIGameController.arcaneTime) {
                        arcaneBoostMethod();
                    }
                    forgetDice();
                }

            }

        } catch (

        PlayerActionException p) {
            handleNotChosenMoveException(p);
        }
    }

    public void arcaneBoostMethod() {
        if (GUIGameController.arcaneTime) {// triggered by the yes button of the arcane boost yes button
            setArcaneDice();
            rollButton.setDisable(GUIGameController.arcaneTime);
        }
    }

    public void enableDice(Button b, Label l) {
        b.setDisable(false);
        b.setOpacity(1);
        l.setOpacity(1);
        l.setDisable(false);

    }

    public void customAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid Player Action");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleNotChosenMoveException(PlayerActionException p) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("YOU DIDNT PICK A MOVE");
        alert.setHeaderText(null);
        alert.setContentText("still waiting for u to pick please");
        alert.showAndWait();

    }

    @FXML
    private Button updateButton;

    // Method to update ComboBox options
    public void updateComboBoxOptions(Move[] m) {

        myComboBox.getItems().clear(); // Clear existing items
        if (m.length > 0)
            for (int i = 0; i < m.length; i++) {
                myComboBox.getItems().add(m[i]);
            }
    }

    public void initializeComboBoxOptions() {

        myComboBox.getItems().clear(); // Clear existing items
        // Move m = new Move(new game.dice.RedDice(1, 2),
        // GUIGameController.currentPlayer.getScoreSheet().getDragon());
        // myComboBox.getItems().addAll(m);
    }

    public void clearComboBoxOptions() {
        myComboBox.getItems().clear(); // Clear existing items

    }

    public boolean dicepicked = false;

    @FXML
    void handleButton1(MouseEvent event) {
        try {
            if (!GUIGameController.notBonus) {
                diceDisable(RedDice, RedValue);
                handleAll(RedDice);
            } else if (arcaneBoostChosen) {
                if (!dicepicked) {
                    diceDisable(RedDice, RedValue);
                    handleAll(RedDice);
                }
            }

            else if (passive) {
                disableAllDice();
                diceDisable(RedDice, RedValue);
                handleAll(RedDice);
                timeWarpDecided = false;

            } else {
                timeWarpDecided = !(GUIGameController.TimeWarpTime);

                if (!rolled) {

                    throw new InvalidDiceSelectionException();
                } else if (!GUIGameController.TimeWarpTime) {
                    if (!dicepicked) {
                        diceDisable(RedDice, RedValue);
                        handleAll(RedDice);
                        timeWarpDecided = false;
                    }
                }
            }

        } catch (InvalidDiceSelectionException e) {
            handleInvalidDiceSelectionException(e);
        }
    }

    @FXML
    public void handleButton2(MouseEvent event) {
        try {
            if (!GUIGameController.notBonus) {
                diceDisable(GreenDice, GreenValue);
                handleAll(GreenDice);
            } else if (arcaneBoostChosen) {
                if (!dicepicked) {
                    diceDisable(GreenDice, GreenValue);
                    handleAll(GreenDice);
                }
            }

            else if (passive) {
                disableAllDice();
                diceDisable(GreenDice, GreenValue);
                handleAll(GreenDice);
                timeWarpDecided = false;

            } else {
                timeWarpDecided = !(GUIGameController.TimeWarpTime);

                if (!rolled) {

                    throw new InvalidDiceSelectionException();
                } else if (!GUIGameController.TimeWarpTime) {
                    if (!dicepicked) {
                        diceDisable(GreenDice, GreenValue);
                        handleAll(GreenDice);
                        timeWarpDecided = false;
                    }
                }
            }

        } catch (InvalidDiceSelectionException e) {
            handleInvalidDiceSelectionException(e);
        }
    }

    @FXML
    public void handleButton3(MouseEvent event) {
        try {
            if (!GUIGameController.notBonus) {
                diceDisable(BlueDice, BlueValue);
                handleAll(BlueDice);
            } else if (arcaneBoostChosen) {
                if (!dicepicked) {
                    diceDisable(BlueDice, BlueValue);
                    handleAll(BlueDice);
                }
            }

            else if (passive) {
                disableAllDice();
                diceDisable(BlueDice, BlueValue);
                handleAll(BlueDice);
                timeWarpDecided = false;

            } else {
                timeWarpDecided = !(GUIGameController.TimeWarpTime);

                if (!rolled) {

                    throw new InvalidDiceSelectionException();
                } else if (!GUIGameController.TimeWarpTime) {
                    if (!dicepicked) {
                        diceDisable(BlueDice, BlueValue);
                        handleAll(BlueDice);
                        timeWarpDecided = false;
                    }
                }
            }

        } catch (InvalidDiceSelectionException e) {
            handleInvalidDiceSelectionException(e);
        }
    }
    @FXML
    public void handleButton4(MouseEvent event) {
        try {
            if (!GUIGameController.notBonus) {
                diceDisable(MagentaDice, MagentaValue);
                handleAll(MagentaDice);
            } else if (arcaneBoostChosen) {
                if (!dicepicked) {
                    diceDisable(MagentaDice, MagentaValue);
                    handleAll(MagentaDice);
                }
            }

            else if (passive) {
                disableAllDice();
                diceDisable(MagentaDice, MagentaValue);
                handleAll(MagentaDice);
                timeWarpDecided = false;

            } else {
                timeWarpDecided = !(GUIGameController.TimeWarpTime);

                if (!rolled) {

                    throw new InvalidDiceSelectionException();
                } else if (!GUIGameController.TimeWarpTime) {
                    if (!dicepicked) {
                        diceDisable(MagentaDice, MagentaValue);
                        handleAll(MagentaDice);
                        timeWarpDecided = false;
                    }
                }
            }

        } catch (InvalidDiceSelectionException e) {
            handleInvalidDiceSelectionException(e);
        }
    }

    public void handleButton5(MouseEvent event) {
        try {
            if (!GUIGameController.notBonus) {
                diceDisable(YellowDice, YellowValue);
                handleAll(YellowDice);
            } else if (arcaneBoostChosen) {
                if (!dicepicked) {
                    diceDisable(YellowDice, YellowValue);
                    handleAll(YellowDice);
                }
            }

            else if (passive) {
                disableAllDice();
                diceDisable(YellowDice, YellowValue);
                handleAll(YellowDice);
                timeWarpDecided = false;

            } else {
                timeWarpDecided = !(GUIGameController.TimeWarpTime);

                if (!rolled) {

                    throw new InvalidDiceSelectionException();
                } else if (!GUIGameController.TimeWarpTime) {
                    if (!dicepicked) {
                        diceDisable(YellowDice, YellowValue);
                        handleAll(YellowDice);
                        timeWarpDecided = false;
                    }
                }
            }

        } catch (InvalidDiceSelectionException e) {
            handleInvalidDiceSelectionException(e);
        }
    }
    public void handleButton6(MouseEvent event) {
        try {
            if (!GUIGameController.notBonus) {
                diceDisable(WhiteDice, WhiteValue);
                handleAll(WhiteDice);
            } else if (arcaneBoostChosen) {
                if (!dicepicked) {
                    diceDisable(WhiteDice, RedValue);
                    handleAll(WhiteDice);
                }
            }

            else if (passive) {
                disableAllDice();
                diceDisable(WhiteDice, RedValue);
                handleAll(WhiteDice);
                timeWarpDecided = false;

            } else {
                timeWarpDecided = !(GUIGameController.TimeWarpTime);

                if (!rolled) {

                    throw new InvalidDiceSelectionException();
                } else if (!GUIGameController.TimeWarpTime) {
                    if (!dicepicked) {
                        diceDisable(WhiteDice, WhiteValue);
                        handleAll(WhiteDice);
                        timeWarpDecided = false;
                    }
                }
            }

        } catch (InvalidDiceSelectionException e) {
            handleInvalidDiceSelectionException(e);
        }
    }
    @FXML
    private Label ABorTw;

    // void enableAllDice() {
    // Button[] diceButtons = { RedDice, GreenDice, BlueDice, MagentaDice,
    // YellowDice, WhiteDice };
    // Label[] diceLabels = { RedValue, GreenValue, BlueValue, MagentaValue,
    // YellowValue, WhiteValue };
    // for (int i = 0; i < diceButtons.length; i++) {
    // diceButtons[i].setDisable(false);
    // diceButtons[i].setOpacity(1);
    // diceLabels[i].setOpacity(1);

    // }

    // }

    @FXML
    public void setEnableBonusButton(Button b, Label l) {
        String hoverStyle = "-fx-background-color: #ffff00; -fx-text-fill: #000000; -fx-border-color: #ffa500; -fx-border-width: 2px; -fx-font-weight: bold; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);";
        b.setStyle(hoverStyle);
        l.setOpacity(1);

    }

    // @FXML
    // public void bonusButtons() {
    // if (!GUIGameController.notBonus || GUIGameController.arcaneTime) {
    // rollButton.setDisable(true);
    // if (GUIGameController.theColourOfBonus != null) {
    // switch (GUIGameController.theColourOfBonus) {
    // case ("RED"):
    // disableAllDice();
    // setEnableBonusButton(RedDice, RedValue);
    // break;

    // case ("GREEN"):
    // case ("BLUE"):
    // case ("MAGENTA"):
    // case ("YELLOW"):
    // case ("WHITE"):

    // }

    // }
    // }

    // }

    @FXML
    void rollDice(MouseEvent event) {
        if (passive)
            rollButton.setDisable(true);
        else {
            Player p = GUIGameController.currentPlayer;
            currentPlayer = p;
            timeWarpInitialize(GUIGameController.currentPlayer);
            PlayerName.setText(GUIGameController.p1);
            Dice[] d = GUIGameController.getAllDice();
            RedValue.setText("" + d[0].getValue());
            GreenValue.setText("" + (((GreenDice) d[1]).getGreenValue()));
            BlueValue.setText("" + d[2].getValue());
            MagentaValue.setText("" + d[3].getValue());
            YellowValue.setText("" + d[4].getValue());
            WhiteValue.setText("" + d[5].getValue());
            rolled = true;
            GUIGameController.notifyEventFired();
            rollButton.setDisable(true);
            if (getAllPossibleMoves(currentPlayer).length == 0) {
                switchPlayer();
            }
        } // to check incase no available moves
          // TODO:check why roll button disables only once
    }

    void handleInvalidDiceSelectionException(InvalidDiceSelectionException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("invalid dice selection");
        alert.setHeaderText(null);
        if (!rolled)
            alert.setContentText("you havent rolled yet bruh");
        else if (GUIGameController.notBonus)
            alert.setContentText("You have no possible moves for this dice.");
        else if (!GUIGameController.notBonus) {
            alert.setContentText("this realm has no moves possible for ur bonus");
        }
        alert.showAndWait();
        switchPlayer();
    }

    @FXML
    void handleClick(String s) throws InvalidDiceSelectionException {
        Dice d;
        Move[] m;
        switch (s) {
            case "Dragon":
                if (!GUIGameController.notBonus) {
                    if (GUIGameController.arcaneTime) {
                        m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer,
                                GUIGameController.getAllDice()[0]);
                    } else {
                        m = GUIGameController.getPossibleMovesForARealm(GUIGameController.currentPlayer,
                                GUIGameController.getAllDice()[0]);
                    }
                    GUIGameController.setSelectedButton(0);

                    // m = GUIGameController.getPossibleMovesForADie(currentPlayer, d);
                    if (m.length == 0) {
                        throw new InvalidDiceSelectionException();

                    } else if (m.length == 1) {
                        dicepicked = true;
                        updateComboBoxOptions(m);
                    } else {
                        updateComboBoxOptions(m);
                        dicepicked = true;
                    }
                    break;
                } else {
                    GUIGameController.setSelectedButton(0);
                    d = GUIGameController.getAllDice()[0];
                    m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer, d);
                    if (m.length == 0) {
                        throw new InvalidDiceSelectionException();
                    } else if (m.length == 1) {
                        updateComboBoxOptions(m);
                        dicepicked = true;
                    } else {
                        updateComboBoxOptions(m);
                        dicepicked = true;
                    }
                    break;
                }
            case "Guardians":
            if (!GUIGameController.notBonus) {
                if (GUIGameController.arcaneTime) {
                    m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer,
                            GUIGameController.getAllDice()[1]);
                } else {
                    m = GUIGameController.getPossibleMovesForARealm(GUIGameController.currentPlayer,
                            GUIGameController.getAllDice()[1]);
                }
                GUIGameController.setSelectedButton(1);

                // m = GUIGameController.getPossibleMovesForADie(currentPlayer, d);
                if (m.length == 0) {
                    throw new InvalidDiceSelectionException();

                } else if (m.length == 1) {
                    dicepicked = true;
                    updateComboBoxOptions(m);
                } else {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                }
                break;
            } else {
                GUIGameController.setSelectedButton(1);
                d = GUIGameController.getAllDice()[1];
                m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer, d);
                if (m.length == 0) {
                    throw new InvalidDiceSelectionException();
                } else if (m.length == 1) {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                } else {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                }
                break;
            }

            case "Hydra":
            if (!GUIGameController.notBonus) {
                if (GUIGameController.arcaneTime) {
                    m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer,
                            GUIGameController.getAllDice()[2]);
                } else {
                    m = GUIGameController.getPossibleMovesForARealm(GUIGameController.currentPlayer,
                            GUIGameController.getAllDice()[2]);
                }
                GUIGameController.setSelectedButton(2);

                // m = GUIGameController.getPossibleMovesForADie(currentPlayer, d);
                if (m.length == 0) {
                    throw new InvalidDiceSelectionException();

                } else if (m.length == 1) {
                    dicepicked = true;
                    updateComboBoxOptions(m);
                } else {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                }
                break;
            } else {
                GUIGameController.setSelectedButton(2);
                d = GUIGameController.getAllDice()[2];
                m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer, d);
                if (m.length == 0) {
                    throw new InvalidDiceSelectionException();
                } else if (m.length == 1) {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                } else {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                }
                break;
            }

            case "Lion":
            if (!GUIGameController.notBonus) {
                if (GUIGameController.arcaneTime) {
                    m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer,
                            GUIGameController.getAllDice()[4]);
                } else {
                    m = GUIGameController.getPossibleMovesForARealm(GUIGameController.currentPlayer,
                            GUIGameController.getAllDice()[4]);
                }
                GUIGameController.setSelectedButton(4);

                // m = GUIGameController.getPossibleMovesForADie(currentPlayer, d);
                if (m.length == 0) {
                    throw new InvalidDiceSelectionException();

                } else if (m.length == 1) {
                    dicepicked = true;
                    updateComboBoxOptions(m);
                } else {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                }
                break;
            } else {
                GUIGameController.setSelectedButton(4);
                d = GUIGameController.getAllDice()[4];
                m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer, d);
                if (m.length == 0) {
                    throw new InvalidDiceSelectionException();
                } else if (m.length == 1) {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                } else {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                }
                break;
            }
            case "Phoenix":
            if (!GUIGameController.notBonus) {
                if (GUIGameController.arcaneTime) {
                    m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer,
                            GUIGameController.getAllDice()[3]);
                } else {
                    m = GUIGameController.getPossibleMovesForARealm(GUIGameController.currentPlayer,
                            GUIGameController.getAllDice()[3]);
                }
                GUIGameController.setSelectedButton(3);

                // m = GUIGameController.getPossibleMovesForADie(currentPlayer, d);
                if (m.length == 0) {
                    throw new InvalidDiceSelectionException();

                } else if (m.length == 1) {
                    dicepicked = true;
                    updateComboBoxOptions(m);
                } else {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                }
                break;
            } else {
                GUIGameController.setSelectedButton(3);
                d = GUIGameController.getAllDice()[3];
                m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer, d);
                if (m.length == 0) {
                    throw new InvalidDiceSelectionException();
                } else if (m.length == 1) {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                } else {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                }
                break;
            }
            case "ArcanePrism":
            if (!GUIGameController.notBonus) {
                if (GUIGameController.arcaneTime) {
                    m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer,
                            GUIGameController.getAllDice()[5]);
                } else {
                    m = GUIGameController.getPossibleMovesForARealm(GUIGameController.currentPlayer,
                            GUIGameController.getAllDice()[5]);
                }
                GUIGameController.setSelectedButton(5);

                // m = GUIGameController.getPossibleMovesForADie(currentPlayer, d);
                if (m.length == 0) {
                    throw new InvalidDiceSelectionException();

                } else if (m.length == 1) {
                    dicepicked = true;
                    updateComboBoxOptions(m);
                } else {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                }
                break;
            } else {
                GUIGameController.setSelectedButton(5);
                d = GUIGameController.getAllDice()[5];
                m = GUIGameController.getPossibleMovesForADie(GUIGameController.currentPlayer, d);
                if (m.length == 0) {
                    throw new InvalidDiceSelectionException();
                } else if (m.length == 1) {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                } else {
                    updateComboBoxOptions(m);
                    dicepicked = true;
                }
                break;
            }
        }
    }

    @FXML
    void handleAll(Button dice) throws InvalidDiceSelectionException {
        switch (dice.getId()) {
            case "RedDice":
                handleClick("Dragon");
                break;
            case "GreenDice":
                handleClick("Guardians");
                break;
            case "BlueDice":
                handleClick("Hydra");
                break;
            case "YellowDice":
                handleClick("Lion");
                break;
            case "MagentaDice":
                handleClick("Phoenix");
                break;
            case "WhiteDice":
                handleClick("ArcanePrism");
                break;
            default:

        }

    }

    @FXML
    void chooseGreen(MouseEvent event) {

    }

    @FXML
    void chooseMagenta(MouseEvent event) {

    }

    @FXML
    void chooseRed(MouseEvent event) {

    }

    @FXML
    void chooseWhite(MouseEvent event) {

    }

    @FXML
    void chooseYellow(MouseEvent event) {

    }

    public static Scene mainScene;

    @FXML
    void displayScoreSheet(MouseEvent me) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ScoreSheetScene.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();

            ScoreSheetSceneController ssc = loader.getController();
            stage.setFullScreen(true);

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

    @FXML
    void exitGame(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void showBlueScoreSheet(MouseEvent event) {

    }

    @FXML
    void showGreenScoreSheet(MouseEvent event) {

    }

    @FXML
    void showMagentaScoreSheet(MouseEvent event) {

    }

    @FXML
    void showRedScoreSheet(MouseEvent event) {

    }

    @FXML
    void showYellowScoreSheet(MouseEvent event) {

    }

    @FXML
    void totalScoreDisplay(MouseEvent me) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FinalScene.fxml"));
            Parent root = loader.load();
            TotalSceneController tsc = loader.getController();

            // tsc.setGUIGameController(cli);

            Scene mainScene = new Scene(root);
            Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();

            stage.setScene(mainScene);
            stage.setFullScreenExitHint("");
            stage.show();
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.setTitle("Dice Realms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void resetAllDice() {
        Dice[] array;
        if (GUIGameController.arcaneTime) {
            array = GUIGameController.getArcaneBoostDice();
        } else {
            array = GUIGameController.getAvailableDice();
        }
        Label[] array1 = { RedValue, GreenValue, BlueValue, MagentaValue, YellowValue, WhiteValue };
        if (array.length != 0) {
            for (int i = 0; i < array.length; i++) {
                switch ((array[i].getRealm() + "")) {

                    case "RED": // Decrease the OPACITY of BUTTON RedValue & to be unclickable
                        RedDice.setDisable(false);
                        array1[i].setDisable(false);
                        RedValue.setOpacity(1);
                        RedDice.setOpacity(1);
                        break;
                    case "GREEN":
                        GreenDice.setDisable(false);
                        array1[i].setDisable(false);
                        GreenValue.setOpacity(1);
                        GreenDice.setOpacity(1);
                        break;
                    case "BLUE":
                        BlueDice.setDisable(false);
                        array1[i].setDisable(false);
                        BlueValue.setOpacity(1);
                        BlueDice.setOpacity(1);
                        break;
                    case "MAGENTA":
                        MagentaDice.setDisable(false);
                        array1[i].setDisable(false);
                        MagentaValue.setOpacity(1);
                        MagentaDice.setOpacity(1);
                        break;
                    case "YELLOW":
                        YellowDice.setDisable(false);
                        array1[i].setDisable(false);
                        YellowValue.setOpacity(1);
                        YellowDice.setOpacity(1);
                        break;
                    case "WHITE":
                        WhiteDice.setDisable(false);
                        array1[i].setDisable(false);
                        WhiteValue.setOpacity(1);
                        WhiteDice.setOpacity(1);
                        break;
                    default:
                        break;
                }
            }
        }

    }

    void usedDice() {
        Dice[] usedDice = GUIGameController.currentPlayer.getUsedDice();
        Button[] diceButtons = { RedDice, GreenDice, BlueDice, MagentaDice, YellowDice, WhiteDice };
        Label[] diceLabels = { RedValue, GreenValue, BlueValue, MagentaValue, YellowValue, WhiteValue };
        for (int i = 0; i < usedDice.length; i++) {
            switch ((usedDice[i].getRealm() + "")) {
                case "RED": // Decrease the OPACITY of BUTTON RedValue & to be unclickable
                    RedDice.setDisable(true);
                    diceLabels[i].setDisable(true);
                    RedValue.setOpacity(0.4);
                    RedDice.setOpacity(0.4);
                    break;
                case "GREEN":
                    GreenDice.setDisable(true);
                    diceLabels[i].setDisable(true);
                    GreenValue.setOpacity(0.4);
                    GreenDice.setOpacity(0.4);
                    break;
                case "BLUE":
                    BlueDice.setDisable(true);
                    diceLabels[i].setDisable(true);
                    BlueValue.setOpacity(0.4);
                    BlueDice.setOpacity(0.4);
                    break;
                case "MAGENTA":
                    MagentaDice.setDisable(true);
                    diceLabels[i].setDisable(true);
                    MagentaValue.setOpacity(0.4);
                    MagentaDice.setOpacity(0.4);
                    break;
                case "YELLOW":
                    YellowDice.setDisable(true);
                    diceLabels[i].setDisable(true);
                    YellowValue.setOpacity(0.4);
                    YellowDice.setOpacity(0.4);
                    break;
                case "WHITE":
                    WhiteDice.setDisable(true);
                    diceLabels[i].setDisable(true);
                    WhiteValue.setOpacity(0.4);
                    WhiteDice.setOpacity(0.4);
                    break;
                default:
                    break;
            }
        }
    }

    // void comboDiceREACTIVATION() {

    // // enableNotUsed();
    // forgetDice();
    // }

    void disableAllDiceWithOpacity() {
        Button[] diceButtons = { RedDice, GreenDice, BlueDice, MagentaDice, YellowDice, WhiteDice };
        Label[] diceLabels = { RedValue, GreenValue, BlueValue, MagentaValue, YellowValue, WhiteValue };
        for (int i = 0; i < diceButtons.length; i++) {
            diceButtons[i].setDisable(true);
            diceLabels[i].setDisable(true);
            ;
            diceButtons[i].setOpacity(0.4);
            diceLabels[i].setOpacity(0.4);
            ;

        }

    }

    void disableAllDice() {
        Button[] diceButtons = { RedDice, GreenDice, BlueDice, MagentaDice, YellowDice, WhiteDice };
        Label[] diceLabels = { RedValue, GreenValue, BlueValue, MagentaValue, YellowValue, WhiteValue };
        for (int i = 0; i < diceButtons.length; i++) {
            diceButtons[i].setDisable(true);
            diceLabels[i].setDisable(true);
            ;

        }

    }

    void enableNotUsed() {
        Dice[] array = GUIGameController.getAllDice();
        Button[] diceButtons = { RedDice, GreenDice, BlueDice, MagentaDice, YellowDice, WhiteDice };
        Label[] diceLabels = { RedValue, GreenValue, BlueValue, MagentaValue, YellowValue, WhiteValue };
        for (int i = 0; i < array.length; i++) {
            if (!array[i].isUsed()) {
                diceButtons[i].setDisable(false);
                diceLabels[i].setDisable(false);
                diceButtons[i].setOpacity(1);
                diceLabels[i].setOpacity(1);
            }
        }
    }

    @FXML
    void forgetDice() {
        Dice[] array = GUIGameController.getForgottenRealmDice();

        if (array.length != 0) {

            for (int i = 0; i < array.length; i++) {

                switch ((array[i].getRealm() + "")) {
                    case "RED": // Decrease the OPACITY of BUTTON RedValue & to be unclickable
                        RedDice.setDisable(true);
                        RedValue.setDisable(true);
                        RedValue.setOpacity(0.4);
                        RedDice.setOpacity(0.4);
                        break;
                    case "GREEN":
                        GreenDice.setDisable(true);
                        GreenValue.setDisable(true);
                        GreenValue.setOpacity(0.4);
                        GreenDice.setOpacity(0.4);
                        break;
                    case "BLUE":
                        BlueDice.setDisable(true);
                        BlueValue.setDisable(true);
                        BlueValue.setOpacity(0.4);
                        BlueDice.setOpacity(0.4);
                        break;
                    case "MAGENTA":
                        MagentaDice.setDisable(true);
                        MagentaValue.setDisable(true);
                        MagentaValue.setOpacity(0.4);
                        MagentaDice.setOpacity(0.4);
                        break;
                    case "YELLOW":
                        YellowDice.setDisable(true);
                        YellowValue.setDisable(true);
                        YellowValue.setOpacity(0.4);
                        YellowDice.setOpacity(0.4);
                        break;
                    case "WHITE":
                        WhiteDice.setDisable(true);
                        WhiteValue.setDisable(true);
                        WhiteValue.setOpacity(0.4);
                        WhiteDice.setOpacity(0.4);
                        break;
                    default:
                        break;
                }
            }
        }

    }

    // void arcaneDice ONLY USED DURING ARCANE BOOST (disabling the button >>
    // arcaneboosted)
    // void unarcaneDice ALL >>RESET
    // switch (enable to diceDisable & vice versa)
    // RESET ALL

    void setArcaneDice() {
        Dice[] arcaneDice = GUIGameController.getArcaneBoostDice();
        resetAllDice();
        Label[] diceLabels = { RedValue, GreenValue, BlueValue, MagentaValue, YellowValue, WhiteValue };
        for (int i = 0; i < arcaneDice.length; i++) {
            if (arcaneDice[i].getClass().getSimpleName().equals("GreenDice"))
                diceLabels[i].setText(((GreenDice) arcaneDice[i]).getGreenValue() + "");
            else
                diceLabels[i].setText(arcaneDice[i].getValue() + "");
        }

        // switch ((array[j].getRealm() + "")) {
        // case "RED": // Decrease the OPACITY of BUTTON RedValue & to be unclickable
        // RedDice.setDisable(false);
        // RedValue.setDisable(false);
        // RedValue.setOpacity(1);
        // RedDice.setOpacity(1);
        // break;
        // case "GREEN":
        // GreenDice.setDisable(false);
        // GreenValue.setDisable(false);
        // GreenValue.setOpacity(1);
        // GreenDice.setOpacity(1);
        // break;
        // case "BLUE":
        // BlueDice.setDisable(false);
        // BlueValue.setDisable(false);
        // BlueValue.setOpacity(1);
        // BlueDice.setOpacity(1);
        // break;
        // case "MAGENTA":
        // MagentaDice.setDisable(false);
        // MagentaValue.setDisable(false);
        // MagentaValue.setOpacity(1);
        // MagentaDice.setOpacity(1);
        // break;
        // case "YELLOW":
        // YellowDice.setDisable(false);
        // YellowValue.setDisable(false);
        // YellowValue.setOpacity(1);
        // YellowDice.setOpacity(1);
        // break;
        // case "WHITE":
        // WhiteDice.setDisable(false);
        // WhiteValue.setDisable(false);
        // WhiteValue.setOpacity(1);
        // WhiteDice.setOpacity(1);
        // break;
        // default:
        // break;
        // }
    }

    void resetDice() {
        String normalStyle = "-fx-background-color: #ffcc00; -fx-text-fill: #000000; -fx-border-color: #ff9900; -fx-border-width: 2px; -fx-font-weight: bold;";

        if (GUIGameController.switchPlayer()) {
            RedDice.setDisable(false);
            RedValue.setDisable(false);
            RedValue.setOpacity(1.0);
            RedDice.setOpacity(1.0);
            RedDice.setStyle(normalStyle);

            GreenDice.setDisable(false);
            GreenValue.setDisable(false);
            GreenValue.setOpacity(1.0);
            GreenDice.setOpacity(1.0);
            GreenDice.setStyle(normalStyle);

            BlueDice.setDisable(false);
            BlueValue.setDisable(false);
            BlueValue.setOpacity(1.0);
            BlueDice.setOpacity(1.0);
            BlueDice.setStyle(normalStyle);

            MagentaDice.setDisable(false);
            MagentaValue.setDisable(false);
            MagentaValue.setOpacity(1.0);
            MagentaDice.setOpacity(1.0);
            MagentaDice.setStyle(normalStyle);

            YellowDice.setDisable(false);
            YellowValue.setDisable(false);
            YellowValue.setOpacity(1.0);
            YellowDice.setOpacity(1.0);
            YellowDice.setStyle(normalStyle);

            WhiteDice.setDisable(false);
            WhiteValue.setDisable(false);
            WhiteValue.setOpacity(1.0);
            WhiteDice.setOpacity(1.0);
            WhiteDice.setStyle(normalStyle);
        }
    }
    // to be placed at the end of arcane boost method of using el arcane boost

    void switchDice() {
        Button[] diceButtons = { RedDice, GreenDice, BlueDice, MagentaDice, YellowDice, WhiteDice };
        Label[] diceLabels = { RedValue, GreenValue, BlueValue, MagentaValue, YellowValue, WhiteValue };
        TurnShift turn = GUIGameController.getGameBoard().getStatus().getTurn();
        String x = turn + "";
        // Dice[] array = GUIGameController.getAllDice(); // TODO:if doesnt work try
        // forgotten dice realm
        Dice[] array = GUIGameController.getForgottenRealmDice();
        for (int j = 0; j < array.length; j++) {

            switch ((array[j].getRealm() + "")) {
                case "RED": // Decrease the OPACITY of BUTTON RedValue & to be unclickable
                    RedDice.setDisable(false);
                    RedValue.setDisable(false);
                    RedValue.setOpacity(1);
                    RedDice.setOpacity(1);
                    break;
                case "GREEN":
                    GreenDice.setDisable(false);
                    GreenValue.setDisable(false);
                    GreenValue.setOpacity(1);
                    GreenDice.setOpacity(1);
                    break;
                case "BLUE":
                    BlueDice.setDisable(false);
                    BlueValue.setDisable(false);
                    BlueValue.setOpacity(1);
                    BlueDice.setOpacity(1);
                    break;
                case "MAGENTA":
                    MagentaDice.setDisable(false);
                    MagentaValue.setDisable(false);
                    MagentaValue.setOpacity(1);
                    MagentaDice.setOpacity(1);
                    break;
                case "YELLOW":
                    YellowDice.setDisable(false);
                    YellowValue.setDisable(false);
                    YellowValue.setOpacity(1);
                    YellowDice.setOpacity(1);
                    break;
                case "WHITE":
                    WhiteDice.setDisable(false);
                    WhiteValue.setDisable(false);
                    WhiteValue.setOpacity(1);
                    WhiteDice.setOpacity(1);
                    break;
                default:
                    break;
            }
        }
    }
    // if (x.equals("P2PT") || x.equals("P1PT")) {
    // for (int i = 0; i < diceButtons.length; i++) {
    // if ((!array[i].isUsed()) && (array[i].isForgotten())) { //
    // diceButtons[i].isDisabled() &&
    // diceButtons[i].setDisable(false);
    // diceLabels[i].setDisable(false);
    // diceButtons[i].setOpacity(1);
    // diceLabels[i].setOpacity(1);
    // } else {
    // diceButtons[i].setDisable(true);
    // diceLabels[i].setDisable(true);
    // diceButtons[i].setOpacity(0.4);
    // diceLabels[i].setOpacity(0.4);
    // }
    // }
    // } else if (x.equals("P1AT1") || x.equals("P2AT2")) {
    // resetDice();
    // }

    public void diceEnable(Button die, Label l) {
        die.setDisable(false);
        die.setOpacity(1);
        l.setOpacity(1);
        l.setDisable(false);
    }

    public void diceDisable(Button die, Label l) {
        l.setDisable(true);
        die.setDisable(true);
        die.setOpacity(0.4);
        l.setOpacity(0.4);
    }

    void timeWarpInitialize(Player p) {
        ImageView[] timeWarpP1 = { P1TW1, P1TW2, P1TW3, P1TW4, P1TW5, P1TW6, P1TW7 };
        ImageView[] timeWarpP2 = { P2TW1, P2TW2, P2TW3, P2TW4, P2TW5, P2TW6, P2TW7 };

        // boolean[] twP1obtained = { false, false, false, false, false, false, false };
        // boolean[] twP2obtained = { false, false, false, false, false, false, false };
        // boolean[] twP1used = { false, false, false, false, false, false, false };
        // boolean[] twP2used = { false, false, false, false, false, false, false };

        TimeWarp[] array1 = GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getTimeWarp();
        TimeWarp[] array2 = GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getTimeWarp();
        if (p.equals(GUIGameController.getGameBoard().getPlayer1())) {
            for (int z = 0; z < timeWarpP1.length; z++) {
                timeWarpP1[z].setOpacity(0.4);
                timeWarpP2[z].setOpacity(0);

                if (array1[z].isObtained()) {
                    // twP1obtained[z] = true;
                    timeWarpP1[z].setOpacity(1);
                }
                if (array1[z].isUsed()) {
                    // twP1used[z] = true;
                    timeWarpP1[z].setOpacity(0.4);
                }

            }
        }

        else {
            for (int z = 0; z < timeWarpP2.length; z++) {
                timeWarpP2[z].setOpacity(0.4);
                timeWarpP1[z].setOpacity(0);

                if (array2[z].isObtained()) {
                    // twP2obtained[z] = true;
                    timeWarpP2[z].setOpacity(1);
                }
                if (array2[z].isUsed()) {
                    // twP2used[z] = true;
                    timeWarpP2[z].setOpacity(0.4);
                }
            }
        }
    }

    // for (int j = 0; j < array1.length; j++) {
    // if (twP1obtained[j] == true && twP1used[j] == true) {
    // timeWarpP1[j].setOpacity(j);
    // }
    // if (twP2obtained[j] == true && twP2used[j] == true) {
    // timeWarpP2[j].setDisable(true)
    // }

    // if (twP1obtained[j] == true && twP1used[j] == false) {
    // timeWarpP1[j].setDisable(false);
    // }
    // if (twP2obtained[j] == true && twP2used[j] == false) {
    // timeWarpP2[j].setDisable(false);f
    // }

    // }

    // }

    void arcaneBoostInitialize(Player p) {
        ImageView[] arcaneBoostP1 = { P1AB1, P1AB2, P1AB3, P1AB4, P1AB5, P1AB6, P1AB7 };
        ImageView[] arcaneBoostP2 = { P2AB1, P2AB2, P2AB3, P2AB4, P2AB5, P2AB6, P2AB7 };

        boolean[] abP1obtained = { false, false, false, false, false, false, false };
        boolean[] abP2obtained = { false, false, false, false, false, false, false };
        boolean[] abP1used = { false, false, false, false, false, false, false };
        boolean[] abP2used = { false, false, false, false, false, false, false };

        ArcaneBoost[] array1 = GUIGameController.getGameBoard().getPlayer1().getScoreSheet().getArcaneBoost();
        ArcaneBoost[] array2 = GUIGameController.getGameBoard().getPlayer2().getScoreSheet().getArcaneBoost();
        for (int i = 0; i < array1.length; i++) {
            if (array1[i].isObtained()) {
                abP1obtained[i] = true;
            }
            if (array1[i].isUsed()) {
                abP1used[i] = true;
            }
            if (array2[i].isObtained()) {
                abP2obtained[i] = true;
            }
            if (array2[i].isUsed()) {
                abP2used[i] = true;
            }
        }

        for (int z = 0; z < array1.length; z++) {
            if (p.equals(GUIGameController.getGameBoard().getPlayer1())) {
                arcaneBoostP1[z].setOpacity(0.4);
                arcaneBoostP2[z].setOpacity(0);

                if (abP1obtained[z] == true && abP1used[z] == true) {
                    arcaneBoostP1[z].setOpacity(0.4);
                    ;
                } else if (abP1obtained[z] == true && abP1used[z] == false) {
                    arcaneBoostP1[z].setOpacity(1);
                    ;
                }
            } else {
                for (int j = 0; j < arcaneBoostP1.length; j++) {
                    arcaneBoostP2[z].setOpacity(0.4);
                    arcaneBoostP1[j].setOpacity(0);

                    if (abP2obtained[j] == true && abP2used[j] == false) {
                        arcaneBoostP2[j].setOpacity(1);
                        ;
                    } else if (abP2obtained[j] == true && abP2used[j] == true) {
                        arcaneBoostP2[j].setOpacity(0.4);
                        ;
                    }
                }
            }

        }
    }

}

// farah implementation
// TODO:i still want u to try it out with the make move
// @FXML
// public Move handleClick(String s) throws InvalidDiceSelectionException {
// Dice d;
// Move[] m;
// switch (s) {
// case "Dragon":
// d = GUIGameController.getAllDice()[0];
// m =
// GUIGameController.getPossibleMovesForADie(currentPlayer,
// d);
// System.out.println("allloooo");

// if (m.length == 0) {
// throw new InvalidDiceSelectionException();
// } else if (m.length == 1) {
// updateComboBoxOptions(m);
// GUIGameController.pickMove(m[0]);
// return m[0]; // Return the single move
// } else if (m.length > 1) {
// updateComboBoxOptions(m);
// System.out.println("kk");

// myComboBox.setOnAction(event -> {
// int selectedIndex = myComboBox.getSelectionModel().getSelectedIndex();
// System.out.println("Selected index: " + selectedIndex);

// if (selectedIndex >= 0 && selectedIndex < m.length) {
// GUIGameController.pickMove(m[selectedIndex]);
// System.out.println("Selected move: " + m[selectedIndex]);
// }
// });
// // Returning null here since the actual return will be triggered within the
// event handler.
// return null;
// }
// break;
// default:
// throw new InvalidDiceSelectionException("Unknown dice type: " + s);
// }
// return null;
// }

// private void updateComboBoxOptions(Move[] moves) {
// myComboBox.getItems().clear();
// for (Move move : moves) {
// myComboBox.getItems().add(move.toString()); // Assuming Move has a meaningful
// toString() method
// }
// }

// case "Guardian":
// d = GUIGameController.getAllDice()[1];
// m =
// GUIGameController.getPossibleMovesForADie(currentPlayer,
// d);
// System.out.println("allloooo");
// // System.out.println(m.length);
// if (m.length == 0)
// throw new InvalidDiceSelectionException();
// else if (m.length == 1) {
// updateComboBoxOptions(m);
// return m[0];
// //
// GUIGameController.makeMove(GUIGameController.getGameBoard().getPlayer1(),m[0]);
// } else if (m.length > 1) {

// updateComboBoxOptions(m);
// System.out.println("kk");
// Label l = new Label("");
// myComboBox.setOnAction(event ->{
// int selectedIndex = myComboBox.getSelectionModel().getSelectedIndex();
// String ss = selectedIndex +"";
// l.setText(ss);
// System.out.println(ss);
// boolean b =
// GUIGameController.makeMove(GUIGameController.getGameBoard().getPlayer1(),m[0]);
// // System.out.println(b);
// });
// System.err.println(l.getText());
// int i = Integer.parseInt(l.getText());
// System.out.println("aa");
// System.out.println(m[i]+"true");
// GUIGameController.pickMove(m[myComboBox.getSelectionModel().getSelectedIndex()-1]);}break;

// case "Hydra":
// d = GUIGameController.getAllDice()[2];
// m =
// GUIGameController.getPossibleMovesForADie(currentPlayer,
// d);
// System.out.println("allloooo");
// // System.out.println(m.length);
// if (m.length == 0)
// throw new InvalidDiceSelectionException();
// else if (m.length == 1) {
// updateComboBoxOptions(m);
// return m[0];
// //
// GUIGameController.makeMove(GUIGameController.getGameBoard().getPlayer1(),m[0]);
// } else if (m.length > 1) {

// updateComboBoxOptions(m);
// System.out.println("kk");
// Label l = new Label("");
// myComboBox.setOnAction(event ->{
// int selectedIndex = myComboBox.getSelectionModel().getSelectedIndex();
// String ss = selectedIndex +"";
// l.setText(ss);
// System.out.println(ss);
// GUIGameController.pickMove(m[myComboBox.getSelectionModel().getSelectedIndex()-1]);
// // System.out.println(b);
// });
// System.err.println(l.getText());
// int i = Integer.parseInt(l.getText());
// System.out.println("aa");
// System.out.println(m[i]+"true");
// return m[i];}
// break;

// case "Phoenix":
// d = GUIGameController.getAllDice()[3];
// m =
// GUIGameController.getPossibleMovesForADie(currentPlayer,
// d);
// System.out.println("allloooo");
// // System.out.println(m.length);
// if (m.length == 0)
// throw new InvalidDiceSelectionException();
// else if (m.length == 1) {
// updateComboBoxOptions(m);
// return m[0];
// //
// GUIGameController.makeMove(GUIGameController.getGameBoard().getPlayer1(),m[0]);
// } else if (m.length > 1) {

// updateComboBoxOptions(m);
// System.out.println("kk");
// Label l = new Label("");
// myComboBox.setOnAction(event ->{
// int selectedIndex = myComboBox.getSelectionModel().getSelectedIndex();
// String ss = selectedIndex +"";
// l.setText(ss);
// System.out.println(ss);
// GUIGameController.pickMove(m[myComboBox.getSelectionModel().getSelectedIndex()-1]);
// // System.out.println(b);
// });
// System.err.println(l.getText());
// int i = Integer.parseInt(l.getText());
// System.out.println("aa");
// System.out.println(m[i]+"true");
// return m[i];}break;

// case "Lion":
// d = GUIGameController.getAllDice()[4];
// m =
// GUIGameController.getPossibleMovesForADie(currentPlayer,
// d);
// System.out.println("allloooo");
// // System.out.println(m.length);
// if (m.length == 0)
// throw new InvalidDiceSelectionException();
// else if (m.length == 1) {
// updateComboBoxOptions(m);
// return m[0];
// //
// GUIGameController.makeMove(GUIGameController.getGameBoard().getPlayer1(),m[0]);
// } else if (m.length > 1) {

// updateComboBoxOptions(m);
// System.out.println("kk");
// Label l = new Label("");
// myComboBox.setOnAction(event ->{
// int selectedIndex = myComboBox.getSelectionModel().getSelectedIndex();
// String ss = selectedIndex +"";
// l.setText(ss);
// System.out.println(ss);
// GUIGameController.pickMove(m[myComboBox.getSelectionModel().getSelectedIndex()-1]);
// // System.out.println(b);
// });
// System.err.println(l.getText());
// int i = Integer.parseInt(l.getText());
// System.out.println("aa");
// System.out.println(m[i]+"true");
// return m[i];}
// break;

// case "ArcanePrsim":

// {updateComboBoxOptions({"1.Red", "2.Green", "3.Blue", "4.Magenta",
// "5.Lion"});
// }break;
// }
// // return new Move(new
// RedDice(),GUIGameController.getScoreSheet(null).getDragon());

// public Move helperMethod(Move m){
// return m;
// }