package game.gui;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import game.engine.Player;
import game.engine.Player;
import game.exceptions.invalidNameException;
//import javax.naming.invalidNameException;
import game.gui.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static game.gui.GUIGameController.currentPlayer;
import static game.gui.GUIGameController.getActivePlayer;
import static game.gui.GUIGameController.getGameStatus;

public class PlayerNameController {

    public static AnchorPane SuperMarketScene;

    @FXML
    private TextField textfield1;

    @FXML
    private TextField textfield2;

    @FXML
    void handleMouseDragOver1(MouseEvent event) {
        textfield1.setStyle("-fx-background-color: yellow;");
    }

    @FXML
    void handleMouseDragOver2(MouseEvent event) {
        textfield2.setStyle("-fx-background-color: yellow;");
    }


    public static Player PlayerONE;
    public static Player PlayerTWO;

    @FXML
    private Button button1;
    @FXML
    private Button button2;

    @FXML
    private Label name1;
    @FXML
    private Label name2;

    @FXML
    private TextField myTextField;

    @FXML

    public void initialize() {
        // Check if textfield1 and textfield2 are not null
        if (textfield1 != null) {
            textfield1.setPromptText("Enter your name");
        } else {
            System.err.println("textfield1 is not initialized");
        }

        if (textfield2 != null) {
            textfield2.setPromptText("Enter your surname");
        } else {
            System.err.println("textfield2 is not initialized");
        }
    }

    // Method to handle input from the TextField
    public void handleInput() {
        String inputText = textfield1.getText();
        System.out.println("Input Text: " + inputText);

    }

    public void handleInput2() {
        String inputText = textfield2.getText();
        System.out.println("Input Text: " + inputText);
    }

    @FXML
    void submit1(MouseEvent me) {
        name1.setText(textfield1.getText());
        GUIGameController.getGameBoard().getPlayer1().setName(textfield1.getText());
    }

    // TODO: try and catch an exception lw das el button click mengheir maykoon fee
    // ayy text inp;uted
    @FXML
    void submit2(MouseEvent me) {
        name2.setText(textfield2.getText());
        GUIGameController.getGameBoard().getPlayer2().setName(textfield2.getText());
    }

    // TODO:IM TRYING STATIC METHODS HERE HEHE

    @FXML
    void handleMouseDragExited1(MouseEvent event) {
        textfield1.setStyle(""); // Reset to default style
    }

    @FXML
    void handleMouseDragExited2(MouseEvent event) {
        textfield2.setStyle(""); // Reset to default style
    }

    private void validateName(String name) throws invalidNameException {
        if (name == null || name.trim().isEmpty() || !name.matches("[a-zA-Z]+") || name.isEmpty()) {
            throw new invalidNameException("Invalid name: " + name + " ");
        }
    }

    private void validateNotEqual(String name1, String name2) throws invalidNameException {
        if (name1.equals(name2)) {
            throw new invalidNameException("Invalid names: they cant be the same! ");
        }
    }

    private void handleInvalidNameException(invalidNameException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid Name Inputted");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public void gonext(MouseEvent m) throws invalidNameException {
        try {
            try {
                PlayerNameController.SuperMarketScene = FXMLLoader.load(getClass().getResource("/SuperMarketScene.fxml"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            validateName(textfield1.getText());
            validateName(textfield2.getText());
            validateNotEqual(name1.getText(), name2.getText());
            GUIGameController.getGameBoard().getPlayer1().setName( textfield1.getText());
            GUIGameController.getGameBoard().getPlayer2().setName( textfield2.getText());
            GUIGameController.p1 = textfield1.getText();
            GUIGameController.p2 = textfield2.getText();
                       // currentPlayer.setName((GUIGameController.getGameBoard().getPlayer1().getName()));
           // SuperMarketController.PlayerName.setText(GUIGameController.getGameBoard().getPlayer1().getName());
            TextArea logTextArea = new TextArea();
            logTextArea.setEditable(false);
            logTextArea.setWrapText(true);
            logTextArea.setPrefWidth(341);
            logTextArea.setPrefHeight(171);
            logTextArea.setBlendMode(BlendMode.MULTIPLY);
            AnchorPane.setTopAnchor(logTextArea, 144.0);
            AnchorPane.setLeftAnchor(logTextArea, 1542.0);
            logTextArea.setFont(Font.font("Comic Sans MS", 20));
            logTextArea.setBorder(Border.EMPTY);
            // Make the TextArea read-only
            // Redirect system output to the TextArea
            Console console = new Console(logTextArea);
            PrintStream ps = new PrintStream(console, true);
            System.setOut(ps);
            System.setErr(ps);
            AnchorPane root = PlayerNameController.SuperMarketScene;
            root.getChildren().add(logTextArea);
            SuperMarketController.mainScene = new Scene(root);
            Stage stage = (Stage) ((Node) m.getSource()).getScene().getWindow();
            stage.setScene(SuperMarketController.mainScene);
            stage.setTitle("Dice Realms");
            stage.show();
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            System.out.println("ROUND " + getGameStatus().getRoundNumber());
            System.out.println(getActivePlayer().getName() + "'s turn");
            System.out.println("Press enter to roll dice");




        }catch (invalidNameException n) {
            handleInvalidNameException(n);
            // TODO:ur input is false lmaooooooo retry again and check with khalid if
            // exception handled correctly

        }

    }

    public class Console extends OutputStream {
        private TextArea textArea;

        public Console(TextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) {
            // Append character to the TextArea
            Platform.runLater(() -> textArea.appendText(String.valueOf((char) b)));
        }

//        @Override
//        public void write(byte[] b, int off, int len) {
//            // Append portion of byte array to the TextArea
//            Platform.runLater(() -> textArea.appendText(new String(b, off, len)));
//        }
    }
}