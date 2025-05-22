package game.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class gui extends Application  {
    @Override
    public void start (Stage stage) {
        try {
            setupStage(stage);
            Label label = new Label("Hello World!");
            label.setTextFill(Color.BLUE);

            Button button = new Button("Ok!");
            //BorderPane borderPane = new BorderPane();
            //borderPane.setTop(label);
            //borderPane.setBottom(button);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

         //   Button[] buttons = new Button[12];
          //  for (int i = 0; i < 12; i++) {
          //      int number = i + 1;
          //      buttons[i] = new Button(Integer.toString(number));
          //      buttons[i].setPrefSize(50, 50);
          //      gridPane.add(buttons[i], i % 4, i / 4);
          //  }

            Button[] buttons = new Button[20];
            for (int i = 0; i < buttons.length; i++) {
                buttons[i] = new Button(Integer.toString(i + 1));
                buttons[i].setPrefSize(80, 40);
            }

            HBox topBox = new HBox(buttons[0], buttons[1], buttons[2]);
            topBox.setAlignment(Pos.CENTER);

            VBox leftBox = new VBox(buttons[3], buttons[4], buttons[5], buttons[6]);
            leftBox.setAlignment(Pos.CENTER);

            GridPane centerGrid = new GridPane();
            centerGrid.setAlignment(Pos.CENTER);
            int count = 0;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    centerGrid.add(buttons[7 + count++], col, row);
             }
            }

            //gridPane.add(label, 0, 0);
            //gridPane.add(button, 0, 1);

            //Group group = new Group(); //ASK
            //group.getChildren().add(label);
            //group.getChildren().add(button);

            TilePane rightPane = new TilePane();
            rightPane.setAlignment(Pos.CENTER);
            rightPane.getChildren().add(buttons[16]);
            rightPane.getChildren().add(buttons[17]);
            rightPane.getChildren().add(buttons[18]);
            rightPane.getChildren().add(buttons[19]);

            BorderPane root = new BorderPane();
            root.setTop(topBox);
            root.setLeft(leftBox);
            root.setCenter(centerGrid);
            root.setRight(rightPane);

            Scene scene = new Scene(root, Color.BLACK);
            stage.setScene(scene);

            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupStage(Stage stage) {
        try {
        stage.setTitle("Simple-App");
        //Image icon = new Image(getClass().getResource("/images/csenb301.png").toExternalForm());
        //stage.getIcons().add(icon);

        stage.setWidth(500);
        stage.setHeight(500);

        stage.setResizable(false); //ASK

        stage.setX(0);
        stage.setY(0);

        stage.setFullScreen(true);
        stage.setFullScreenExitHint("You can press Q to exit full screen mode.");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}