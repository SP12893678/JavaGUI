package spgui.lib;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

public class DragListener extends MouseInputAdapter {
       Point location;
       MouseEvent pressed;
       Component movecomponent = null;
       
       public DragListener(Component movecomponent)
       {
    	   this.movecomponent = movecomponent;
       }
       
       public void mousePressed(MouseEvent me) {
           pressed = me;
       }
       
       public void mouseDragged(MouseEvent me) {
           location = movecomponent.getLocation(location);
           int x = location.x - pressed.getX() + me.getX();
           int y = location.y - pressed.getY() + me.getY();
           movecomponent.setLocation(x, y);
       }
   }