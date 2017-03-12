package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;


public class test {
    FXMLLoader loader = new FXMLLoader( getClass().getResource("sample.fxml"));
    Controller contr = loader.<Controller>getController();

    public test(){
        System.out.println(contr.slider.toString());
        Ellipse e = new Ellipse(50,50,70,50);
        e.setFill(Color.RED);
        System.out.println(e);
        contr.drawpane.getChildren().add(e);
    }
}
