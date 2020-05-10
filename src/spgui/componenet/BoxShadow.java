package spgui.componenet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

public class BoxShadow extends JComponent
   {
	   private static final long serialVersionUID = 1L;
	   public int pixels;
	   int width = 0;
	   int height = 32;
	   Graphics2D g2;
	   
	   public BoxShadow(int width,int height)
	   {
		   this.width = width;
		   this.height = height;
		   setWindowBorder();
	   }
	   
	   public void setShadow(int width,int height)
	   {
		   this.width = width;
		   this.height = height;
		   setWindowBorder();
		   repaint();
	   }
	   
	   private void setWindowBorder()
	   {
		   this.pixels = 4;
	       Border border = BorderFactory.createEmptyBorder(pixels, pixels, pixels, pixels);
	       this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), border));
	   }
	   
	   public void drawShawdow()
	   {
		   int shade = 0;
		   int topOpacity = 65;
		   for (int i = 0; i < pixels; i++)
		   {
			   g2.setColor(new Color(shade, shade, shade, ((topOpacity / pixels) * i)));
			   g2.drawRoundRect(i - 3, i - 3 , width + 7 - ((i * 2) + 1), height + 7 - ((i * 2) + 1),16, 16);
		   }
	   }
	   
	   public void paintComponent(Graphics g) 
	   {
		      super.paintComponent(g);
		      g2 = (Graphics2D) g;
		      RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
		      qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
		      g2.setRenderingHints( qualityHints );  
		      drawShawdow();
	   }
   }
   