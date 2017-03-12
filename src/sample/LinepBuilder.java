package sample;

import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

import static java.lang.Math.*;


public class LinepBuilder extends Thread {

    @Override
    public void run() {
        super.run();
    }

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent me) {

            ((Circle)(me.getSource())).setCenterX(me.getX());
            ((Circle)(me.getSource())).setCenterY(me.getY());
            //System.out.println(((Circle)(me.getSource())).getParent());

            updateLineps();
            updateNextLvlLineps();
            //newNxtLvlLines();
        }
    };



    int xBound,
        yBound;


    ArrayList<Linep> Lineps = new ArrayList();
    ArrayList<Linep> Lineps1 = new ArrayList();
    ArrayList<Linep> Lineps2 = new ArrayList();
    ArrayList<Linep> Lineps3 = new ArrayList();
    ArrayList<Linep> Lineps4 = new ArrayList();
    ArrayList<Linep> Lineps5 = new ArrayList();

    public LinepBuilder(int new_xBound, int new_yBound){

        xBound = new_xBound;
        yBound = new_yBound;

    }

    public void newRandomLineps(int number){
        Lineps.clear();
        Lineps1.clear();
        Lineps2.clear();
        Lineps3.clear();
        Lineps4.clear();
        Lineps5.clear();
        if(number > 0){

            Line line;
            Linep l1;
            line = new Line(random()*xBound,random()*yBound,random()*xBound,random()*yBound);
            l1 = new Linep(line ,true);
            l1.cirS.setOnMouseDragged(circleOnMouseDraggedEventHandler);
            l1.cirS.setCursor(Cursor.MOVE);
            l1.cirE.setOnMouseDragged(circleOnMouseDraggedEventHandler);
            l1.cirE.setCursor(Cursor.MOVE);
            l1.isUserLine = true;
            l1.setCache(true);
            l1.setCacheHint(CacheHint.SPEED);
            Lineps.add(l1);


            for(int i = 1; i < number; i ++){
                Linep l = new Linep(random()*500,random()*500, Lineps.get(i-1));
                l.isUserLine = true;
                l.cirE.setOnMouseDragged(circleOnMouseDraggedEventHandler);
                l.cirE.setCursor(Cursor.MOVE);
                l.setCache(true);
                l.setCacheHint(CacheHint.SPEED);
                Lineps.add(l);

            }
        }

        System.out.println("needed lines: " + linecount(number) + " - needed levels: " + number);
        newNxtLvlLines();
    }

    private void newNxtLvlLines() {

        Lineps1.clear();
        Lineps2.clear();
        Lineps3.clear();
        Lineps4.clear();
        Lineps5.clear();

        if(Lineps.size() > 1){
            for (int i = 0; i < Lineps.size()  ; i ++){
                try {
                    Linep l1 = Lineps.get(i);
                    Linep l2 = Lineps.get(i + 1);
                    Linep l3 = new Linep(new Line(l1.cirP.getCenterX(), l1.cirP.getCenterY(), l2.cirP.getCenterX(), l2.cirP.getCenterY()),false);
                    l3.setParents(l1.cirP, l2.cirP);

                    Lineps1.add(l3);
                }catch (Exception e){}

            }
        }
        if(Lineps1.size() > 1){
            for (int i = 0; i < Lineps1.size()  ; i ++){
                try {
                    Linep l1 = Lineps1.get(i);
                    Linep l2 = Lineps1.get(i + 1);
                    Linep l3 = new Linep(new Line(l1.cirP.getCenterX(), l1.cirP.getCenterY(), l2.cirP.getCenterX(), l2.cirP.getCenterY()),false);
                    l3.setParents(l1.cirP, l2.cirP);

                    Lineps2.add(l3);
                }catch (Exception e){}

            }
        }
        if(Lineps2.size() > 1){
            for (int i = 0; i < Lineps2.size()  ; i ++){
                try {
                    Linep l1 = Lineps2.get(i);
                    Linep l2 = Lineps2.get(i + 1);
                    Linep l3 = new Linep(new Line(l1.cirP.getCenterX(), l1.cirP.getCenterY(), l2.cirP.getCenterX(), l2.cirP.getCenterY()),false);
                    l3.setParents(l1.cirP, l2.cirP);

                    Lineps3.add(l3);
                }catch (Exception e){}

            }
        }
        if(Lineps3.size() > 1){
            for (int i = 0; i < Lineps3.size()  ; i ++){
                try {
                    Linep l1 = Lineps3.get(i);
                    Linep l2 = Lineps3.get(i + 1);
                    Linep l3 = new Linep(new Line(l1.cirP.getCenterX(), l1.cirP.getCenterY(), l2.cirP.getCenterX(), l2.cirP.getCenterY()),false);
                    l3.setParents(l1.cirP, l2.cirP);

                    Lineps4.add(l3);
                }catch (Exception e){}

            }
        }
    }

    private void updateLineps() {
        ArrayList temp = Lineps;
        temp.addAll(Lineps1);
        temp.addAll(Lineps2);
        temp.addAll(Lineps3);
        temp.addAll(Lineps4);
        temp.addAll(Lineps5);
        if(temp.size() > 0 ){
            Linep l = (Linep) temp.get(0);

            l.sX = l.cirS.getCenterX();
            l.sY = l.cirS.getCenterY();
            l.eX = l.cirE.getCenterX();
            l.eY = l.cirE.getCenterY();
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
            l.updateCirP();
        }
        for(int i = 1; i < temp.size(); i++){
            Linep l = (Linep) temp.get(i);
            try {
                l.eX = l.cirE.getCenterX();
                l.eY = l.cirE.getCenterY();
                l.sX = Lineps.get(i - 1).cirE.getCenterX();
                l.sY = Lineps.get(i - 1).cirE.getCenterY();
            }catch (Exception e){
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
            l.updateCirP();
        }
    }

    public void updateNextLvlLineps(){ //@todo ? statt neu to positionieren tranlate und rotate benutzen!
        ArrayList temp = Lineps1;
        temp.addAll(Lineps2);
        temp.addAll(Lineps3);
        temp.addAll(Lineps4);
        temp.addAll(Lineps5);
        for(int i = 0; i < temp.size(); i++){
            Linep l = (Linep) temp.get(i);


            l.eX = l.eParent.getCenterX();
            l.eY = l.eParent.getCenterY();
            l.sX = l.sParent.getCenterX();
            l.sY = l.sParent.getCenterY();


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
            l.updateCirP();
        }
    }

    public void updateCirPs(){
        ArrayList<Linep> temp = Lineps;
        temp.addAll(Lineps1);
        temp.addAll(Lineps2);
        temp.addAll(Lineps3);
        temp.addAll(Lineps4);
        temp.addAll(Lineps5);
        for(Linep lp:temp){
            lp.updateCirP();
        }
        updateNextLvlLineps();
    }

    public ArrayList getLineps() {
        ArrayList temp = Lineps;
        temp.addAll(Lineps1);
        temp.addAll(Lineps2);
        temp.addAll(Lineps3);
        temp.addAll(Lineps4);
        temp.addAll(Lineps5);
        return temp;
    }


    /**
     *
     * @param i
     * amount of userlines
     * @return
     * amount of extra lines to add
     */
    public int linecount(int i){
        int r = 0;
        while(i > 0){
            i--;
            r += i;
        }
        return r;
    }
}
