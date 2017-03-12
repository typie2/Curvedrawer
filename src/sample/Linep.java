package sample;


import static java.lang.Math.*;

import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class Linep extends Parent {

    public Line line;
    public Circle cirS, cirE, cirP, sParent, eParent;
    public double sX, sY, eX, eY, direction, lenght, nValue, stepsX, aa, bb;
    public boolean isUserLine = false;
    public int level = 0;


    /**
     * Constructs Linep from given Line.
     * @param l
     * line to construct linep from
     */
    public Linep(Line l, boolean new_isUserLine){

        isUserLine = new_isUserLine;

        sX = (l.getStartX());
        sY = (l.getStartY());
        eX = (l.getEndX());
        eY = (l.getEndY());
        line = l;
        l.setCache(true);
        l.setCacheHint(CacheHint.SPEED);

        direction = direction();
        lenght = lenght();
        nValue = nValue();
        stepsX = stepsX();
        bb = getBb();
        aa = toRadians(180-abs(toDegrees(bb)));

        if(isUserLine) {
            cirS = new Circle(sX, sY, 5);
            cirS.setFill(Color.color(0, 0, 0, 0));
            cirS.setStroke(Color.BLACK);
            cirS.setCache(true);
            cirS.setCacheHint(CacheHint.SPEED);

            cirE = new Circle(eX, eY, 5);
            cirE.setFill(Color.color(0, 0, 0, 0));
            cirE.setStroke(Color.BLACK);
            cirE.setCache(true);
            cirE.setCacheHint(CacheHint.SPEED);
        }
        createCirP();

        this.getChildren().add(0, cirP);
        this.getChildren().add(1,line);
        if(isUserLine) {
            this.getChildren().add(2, cirE);
            try {
                this.getChildren().add(3, cirS);
            } catch (Exception e) {
            }
        }

    }


    /**
     * Constructs Linep from prvios Linep and the cooradiantes for the new point.
     *
     * @param new_eX
     * x value of the new point
     * @param new_eY
     * y value of the new point
     * @param parent
     * previos linep to construct from
     */
    public Linep(double new_eX, double new_eY, Linep parent){


        sX = parent.eX;
        sY = parent.eY;
        eX = new_eX;
        eY = new_eY;
        line = new Line(sX,sY,eX,eY);

        direction = direction();
        lenght = lenght();
        nValue = nValue();
        stepsX = stepsX();
        bb = getBb();
        aa = toRadians(180-toDegrees(bb));

        cirE = new Circle(eX, eY, 5);
        cirE.setFill(Color.color(0, 0, 0, 0));
        cirE.setStroke(Color.BLACK);

        createCirP();

        this.getChildren().add(0, cirP);
        this.getChildren().add(1,line);
        this.getChildren().add(2,cirE);
        try {
            this.getChildren().add(3,cirS);
        }catch (Exception e){}
    }


    /**
     *
     * @return direction as rise of a linear function
     */
    public double direction(){
        return (sY - eY)/(sX - eX);

    }


    /**
     * Calculates the lenght of Linep.
     * @return lenght of linep
     */
    public double lenght(){
        return sqrt(pow((eX - sX),2) + pow((eY - sY),2));
    }


    /**
     * Calculates β of the Linep.
     * @return Calculated β.
     */
    public double getBb(){
        return atan(direction);
    }

    /**
     * Calculates the x value of the stepsize.
     * @return
     */
    private double stepsX(){
        return abs(eX - sX)/100;
    }

    /**
     *
     * @return y value where the linep woult hit x=0.
     */
    public double nValue(){
        return sY-direction*sX;
        //return (l.direction*l.sX)/sY;
    }

    /**
     * @param x C value to find the point of.
     * @return The coordinates of the point at x as [x,y].
     */
    public double[] pointAtX(double x){
        double[] coordinates;
        coordinates = new double[2];

        coordinates[0] = x;
        coordinates[1] = this.direction * coordinates[0] + this.nValue;

        return coordinates;
    }


    public void createCirP() {
        double t = Controller.getT();
        double tX;
        if (this.eX > this.sX) tX = t * this.stepsX + this.sX;
        else tX = -1*(t * this.stepsX) + this.sX;

        Circle new_cirP;
        double[] coordinates = pointAtX(tX);


        new_cirP = new Circle(coordinates[0], coordinates[1], 3);
        new_cirP.setFill(Color.gray(0.4, 0.5));
        new_cirP.setStroke(Color.BLACK);
        cirP = new_cirP;
    }

    public void updateCirP(){
        double t = Controller.getT();
        double tX;
        if (this.eX > this.sX) tX = t * this.stepsX + this.sX;
        else tX = -1*(t * this.stepsX) + this.sX;

        Circle new_cirP;
        double[] coordinates = pointAtX(tX);

        cirP.setCenterX(coordinates[0]);
        cirP.setCenterY(coordinates[1]);
    }

    public void setParents(Circle new_sParent, Circle new_eParent){
        sParent = new_sParent;
        eParent = new_eParent;
    }

}
