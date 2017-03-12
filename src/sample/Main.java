package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Kurvenzeichner");
        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(500.0);
        primaryStage.setMinWidth(500);
        primaryStage.setHeight(530.0);
        primaryStage.setMinHeight(170);
        //primaryStage.setResizable(false);
        primaryStage.show();

        //test test = new test();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
