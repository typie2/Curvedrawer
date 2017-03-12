package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.Robot;
import java.awt.AWTException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Bounds;
import javafx.util.Duration;

public class Controller implements Initializable {

    @FXML
    private Label label; public TextField numberfield; public AnchorPane drawpane; public AnchorPane contrlpane;
    @FXML
    public ToggleButton aniBut; public Slider slider;
    public static double t = 0;

    public static short animate = 0;

    //public double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    //public long frame = 0;
    //public SVGPath path = new SVGPath();
    //public ArrayList<Linep> Lineps = new ArrayList();
    //public ArrayList<Node> cirPlines = new ArrayList();
    //public ArrayList<ArrayList> cirPsLvls = new ArrayList();
    //public ArrayList<ArrayList> LinepLvls = new ArrayList();

    LinepBuilder linepBuilder = (new LinepBuilder(500,500));
    TimerTask tt = new TimerTask() {
        @Override
        public void run() {
            nextFrame();
        }

    };


    public Controller() {
        //t.setValue(0);
        t = 0;
        this.slider = new Slider(0,10,0);
        this.label = new Label(Short.toString((short)slider.getValue()));
        //slider.setValue((double) t.getValue());
        slider.setValue(t);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws InterruptedException {
        if(animate == 0) {
            animate = 1;
            tt.run();
            AnimationLoop.play();
        }else {
            tt.cancel();
            animate = 0;
            AnimationLoop.stop();
        }


    }

    /**
     * When key in numberfield gets released, check if last char in the textfield is a number,
     * otherwise delete it.
     * Go to drawUserLines.
     */
    @FXML
    public void handlekeyreleased() {
        int number = parseInput();
        linepBuilder.newRandomLineps(number);
        ArrayList<Linep> temp = linepBuilder.getLineps();
        drawpane.getChildren().clear();
        //System.out.println(temp.toString());
        for (Linep lp: temp) {
            draw(lp,0);
        }
    }

    int parseInput(){
        String c = "0";
        c += numberfield.getText();
        if (!(c.endsWith("0") || c.endsWith("1") || c.endsWith("2") || c.endsWith("3")
                || c.endsWith("4") || c.endsWith("5") || c.endsWith("6") || c.endsWith("7")
                || c.endsWith("8") || c.endsWith("9")))
            numberfield.deleteText(numberfield.getText().length()-1, numberfield.getText().length());

        try {
            return Integer.parseInt(numberfield.getText());

        } catch(NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Try to get a number from numberfield, on failture set numer to 0.
     *******************************************************************
     * Clear all existing lines, draw the given number of lines at random locations,
     * add them to the lines arraylist.
     */

    //public void newRandomLineps(){
    //    int number;
    //
    //    clearAllDraw();
    //    Lineps.clear();
    //    drawpane.getChildren().clear();
    //    //t = 0;
    //    label.setText(String.valueOf((short)t));
    //    //slider.setValue((short) t.getValue());
    //    slider.setValue((short) t);
    //
    //    try {
    //        number = Integer.parseInt(numberfield.getText());
    //    } catch(NumberFormatException e) {
    //        number = 0;
    //    }
    //    for(int i = Lineps.size(); i < number; i ++){
    //        Line line;
    //        Linep l;
    //        if (i == 0) {
    //            line = new Line(random()*drawpane.getWidth(),random()*drawpane.getHeight(),random()*drawpane.getWidth(),random()*drawpane.getHeight());
    //            l = new Linep(line);
    //        }else {
    //            l = new Linep(random()*500,random()*500, (Linep) Lineps.get(i-1));
    //        }
    //        l.isUserLine = true;
    //        Lineps.add(l);
    //        draw(l.line);
    //        if (i == 0){
    //            draw(l.cirS);
    //            l.cirS.setOnMouseDragged(circleOnMouseDraggedEventHandler);
    //            l.cirS.setCursor(Cursor.MOVE);
    //        }
    //
    //        draw(l.cirE);
    //        l.cirE.setOnMouseDragged(circleOnMouseDraggedEventHandler);
    //        l.cirE.setCursor(Cursor.MOVE);
    //
    //        drawCirPs();
    //    }
    //    System.out.println("needed lines: " + linecount(number));
    //    newNxtLvlLines();
    //}
    //
    //
    //public void updateLineps(){
    //    for(int i = 0; i < Lineps.size(); i++){
    //        Linep l = Lineps.get(i);
    //
    //        l.eX = l.cirE.getCenterX();
    //        l.eY = l.cirE.getCenterY();
    //        if(i != 0){
    //            l.sX = Lineps.get(i - 1).cirE.getCenterX();
    //            l.sY = Lineps.get(i - 1).cirE.getCenterY();
    //        }else {
    //            l.sX = l.cirS.getCenterX();
    //            l.sY = l.cirS.getCenterY();
    //        }
    //
    //        l.line.setStartX(l.sX);
    //        l.line.setStartY(l.sY);
    //        l.line.setEndX(l.eX);
    //        l.line.setEndY(l.eY);
    //
    //        l.direction = l.direction();
    //        l.lenght = l.lenght();
    //        l.nValue = l.sY-l.direction*l.sX;
    //        l.stepsX = abs(l.eX - l.sX)/100;
    //        l.getBb = l.getBb();
    //        l.aa = toRadians(180-toDegrees(l.getBb));
    //    }
    //    drawCirPs();
    //    newNxtLvlLines();
    //}
    //
    //
    //public Linep updateLinep(Linep l){
    //
    //    l.eX = l.cirE.getCenterX();
    //    l.eY = l.cirE.getCenterY();
    //    l.sX = l.cirS.getCenterX();
    //    l.sY = l.cirS.getCenterY();
    //
    //
    //    l.line.setStartX(l.sX);
    //    l.line.setStartY(l.sY);
    //    l.line.setEndX(l.eX);
    //    l.line.setEndY(l.eY);
    //
    //    l.direction = l.direction();
    //    l.lenght = l.lenght();
    //    l.nValue = l.sY-l.direction*l.sX;
    //    l.stepsX = abs(l.eX - l.sX)/100;
    //    l.getBb = l.getBb();
    //    l.aa = toRadians(180-toDegrees(l.getBb));
    //
    //    drawCirPs();
    //    return l;
    //}


    public void nextFrame(){
        t += 0.5;
        if(t > 100) t = 0;
        t += 0.5;
        label.setText(String.valueOf((short)t));
        slider.setValue(t);

        linepBuilder.updateCirPs();
    }

    Timeline AnimationLoop = new Timeline(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            nextFrame();
        }
    }));



    public void prevoisFrame(){
        t--;
        if(t == -1) t = 100;
        label.setText(String.valueOf((short)t));
        slider.setValue(t);

        linepBuilder.updateCirPs();
    }

    /**
     * Draws given node to drawpane
     * @param n
     * node to draw
     */



    public void draw(Node n, int i){
        drawpane.getChildren().add(i, n);
    }


    public static double getT() {
        return t;
    }

    //EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
    //    @Override
    //    public void handle(MouseEvent me) {
//
    //        ((Circle)(me.getSource())).setCenterX(me.getSceneX());
    //        ((Circle)(me.getSource())).setCenterY(me.getSceneY()-contrlpane.getHeight());
//
    //        updateLineps();
    //        newNxtLvlLines();
    //    }
    //};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnimationLoop.setCycleCount(Timeline.INDEFINITE);
        t = 0;
        linepBuilder.start();

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                label.setText((short)Math.round(new_val.intValue()) + "");
                t = new_val.doubleValue();
                linepBuilder.updateCirPs();
                if (t == 101) {
                    t = 0;
                    try {
                        Bounds bounds = contrlpane.getBoundsInLocal();
                        Bounds screenBounds = contrlpane.localToScreen(bounds);
                        int x = (int) screenBounds.getMinX();
                        int y = (int) screenBounds.getMinY();
                        Robot robot = new Robot();
                        robot.mouseMove(x + 130, y + 15);
                    }
                    catch (AWTException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
}

/*

    public void drawCirPs(){
        ArrayList<Circle> cirPs = new ArrayList();
        if (cirPsLvls.size() > 0) {
            cirPs = cirPsLvls.get(0);
        }
        for(Circle cir:cirPs){
            clearDraw(cir);
        }
        cirPsLvls.clear();
        cirPs.clear();
        for(Linep l:Lineps){
            Circle cirP = new Circle();
            //cirP = Linep.createCirP(l,(short) t.getValue());
            l.createCirP();
            l.cirP = cirP;
            cirPs.add(cirP);
            draw(l.cirP, 0);
        }
        cirPsLvls.add(cirPs);
        label.setText(String.valueOf((short)t));
        //slider.setValue((short) t.getValue());
        slider.setValue(t);
        //nextLvlLines();
    }


    public void clearAllDraw(){
        drawpane.getChildren().clear();
    }


        public void AnimateLoop() throws InterruptedException{
        //t.setValue(0);

        t = 0;
        Timeline animation = new Timeline();
        animation.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(t, 0))
        );

       long start_time = 0, end_time;
      double difference;

      while(animate == 1){
         System.out.println("~*animation*~ frame: " + frame);
         end_time = System.nanoTime();
         difference = (end_time - start_time)/1e6;
         if(difference < 100) Thread.sleep((long) (100-difference));
         start_time = System.nanoTime();
         nextFrame();
         frame++;
      }
    }




public void draw(Node n){
        drawpane.getChildren().add(n);
    }

    public void newNxtLvlLines(){
    //    for(ArrayList<Linep> ar: LinepLvls){
    //        for(Linep l: ar){
    //            clearDraw(l.line);
    //            //clearDraw(Linep.createCirP(l,(short) t.getValue()));
    //            clearDraw(l.createCirP());
    //        }
    //    }
    //    for(ArrayList<Circle> ar:cirPsLvls){
    //        for(Circle cir:ar){
    //            clearDraw(ar);
    //        }
    //    }
    //    LinepLvls.clear();
    //    for (int i = 0; i < cirPsLvls.size(); i++){
    //        ArrayList<Circle> cirPs = cirPsLvls.get(i);
    //        if (cirPs.size() > 1) {
    //            ArrayList<Linep> Linps = new ArrayList();
    //            for (int j = 0; j < cirPs.size()-1; j++){
//
    //                Line line = new Line(cirPs.get(j).getCenterX(), cirPs.get(j).getCenterY(),
    //                        cirPs.get(j+1).getCenterX(), cirPs.get(j+1).getCenterY());
    //                Linep l = new Linep(line);
//
    //                //cirPlines.add(l.cirP);
    //                Linps.add(l);
    //                //updateLinep(l);
    //            }
    //            LinepLvls.add(Linps);
//
    //        }
    //    }
    //    for(ArrayList<Linep> ar: LinepLvls){
    //        for(Linep l: ar){
    //            draw(l.line);
    //            //draw(Linep.createCirP(l, (short) t.getValue()));
    //            draw(l.createCirP());
    //        }
    //    }
    }


    public void updateNxtLvlLine(){
        for (int i = 0; i < LinepLvls.size(); i++){
            ArrayList<Linep> Linps = LinepLvls.get(i);
            if (Linps.size() > 1) {
                for (int j = 0; j < Linps.size()-1; j++){
                    Linep l = Linps.get(i);
                    l.eX = l.cirE.getCenterX();
                    l.eY = l.cirE.getCenterY();
                    if(i != 0){
                        l.sX = Lineps.get(i - 1).cirE.getCenterX();
                        l.sY = Lineps.get(i - 1).cirE.getCenterY();
                    }else {
                        l.sX = l.cirS.getCenterX();
                        l.sY = l.cirS.getCenterY();
                    }

                    l.line.setStartX(l.sX);
                    l.line.setStartY(l.sY);
                    l.line.setEndX(l.eX);
                    l.line.setEndY(l.eY);

                    l.direction = l.direction();
                    l.lenght = l.lenght();
                    l.nValue = l.sY-l.direction*l.sX;
                    l.stepsX = abs(l.eX - l.sX)/100;
                    l.bb = l.getBb();
                    l.aa = toRadians(180-toDegrees(l.bb));
                }
            }
        }
    }



 */