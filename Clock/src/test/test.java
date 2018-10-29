package test;

import java.applet.Applet;
import java.awt.*;

public class test extends Applet {

    public void paint(Graphics g){
        int xCenter=getWidth()/2;
        int yYenter=80;
        g.setColor(Color.black);
        g.drawOval(100,100,50,50);
        g.setColor(Color.red);
        g.drawOval(25,25,50,50);
        g.setColor(Color.yellow);
        g.drawOval(0,0,50,50);
        g.setColor(Color.green);
        g.drawOval(50,50,50,50);
        g.setColor(Color.blue);
        g.drawOval(75,75,50,50);
        g.setColor(Color.orange);
        g.drawOval(xCenter-25,yYenter,50,50);

    }
}
