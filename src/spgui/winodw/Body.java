package spgui.winodw;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

/**
* An extended version of <i>javax.swing.JComponent</i>.<br>
* This class builds the content area of windows.<br>
* 
* @author	Jun Hong Peng
* @version	1.0
* @since	2020-05-10
* @param	width The value to be the width of windows content area.
* @param	height The value to be the height of windows content area.
*/

public class Body extends JComponent {

	private static final long serialVersionUID = 1L;
	public Color color = new Color(254,253,254,255);
	Graphics2D g2;
	int width = 100;
	int height = 32;

   public Body(int width,int height) {
	   this.width = width;
	   this.height = height;
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g2 = (Graphics2D) g;
      RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
      qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
      g2.setRenderingHints( qualityHints );  
  	  Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, 16, 16));
  	  area.add(new Area(new RoundRectangle2D.Double(0, 0, width, 10, 2, 2)));
  	  g2.setPaint(color);
      g2.fill(area);
   } 
}