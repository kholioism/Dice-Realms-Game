
package game.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class hi extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
        //Parent root = new Group();
        Parent root = FXMLLoader.load(getClass().getResource("/SuperMarketScene.fxml"));
        Scene mainScene = new Scene(root);
      
        primaryStage.setScene(mainScene);
        
        primaryStage.setResizable(true);
        primaryStage.setFullScreen(false);
        //primaryStage.setFullScreen(true);
        primaryStage.show();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("idk whats wrong");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}



