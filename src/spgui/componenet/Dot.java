package spgui.componenet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;

import javax.swing.JComponent;

/**
* An extended version of <i>javax.swing.JComponent</i>.<br>
* This class builds component of the circle dot.<br>
* 
* @author	Jun Hong Peng
* @version	1.0
* @since	2020-05-10
* @param	size The value to be the 2*radius of circle dot.
*/

public class Dot extends JComponent
{
		private static final long serialVersionUID = 1L;
		private Graphics2D g2;
		private Shape shape;
		private ColorType type = ColorType.Solid;
		private	Color color = Color.BLACK;
		private Color[] colors;
		
		public enum ColorType {
			Solid, LinearGradient, RadialGradient
		}
	
		public Dot()
		{
			setDotSize(16);
		}
		
		public Dot(int size)
		{
			setDotSize(size);
		}
		
		public void setDotSize(int size)
		{
			shape = new Arc2D.Float(0, 0, size, size, 0, 360, Arc2D.CHORD);
		}

		public void setGradientColor(Color[] colors) {
			this.colors = colors;
			type = ColorType.RadialGradient;
		}
		
		public void setColor(Color color) {
			this.color = color;
			type = ColorType.Solid;
		}
		
		public void PaintColor(Graphics2D g2) {
			
			switch(type) 
			{
				case Solid:
					g2.setPaint(color);
					break;
				case RadialGradient:
					g2.setPaint(getGradientColor(colors,0 , 0));
					break;
				case LinearGradient:
//					g2.setPaint();
					break;
				default:
					break;
			}
		}
  
		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			g2 = (Graphics2D) g;
			RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
			qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
			g2.setRenderingHints( qualityHints );  
			PaintColor(g2);
			g2.fill(shape);
		}
		
		private RadialGradientPaint getGradientColor(Color[] colors,int x , int y)
		{
			int radius = Math.min(16, 16);
			return new RadialGradientPaint(new Point(x + 16 / 2, y + 16 / 2),radius,new float[]{0f, 1f},colors);
		}

		@Override
		public boolean contains(int x, int y)
		{
		       Rectangle bounds = shape.getBounds();
		       Insets insets = getInsets();
		       int translateX = x + bounds.x - insets.left;
		       int translateY = y + bounds.y - insets.top;
		       return shape.contains(translateX, translateY);
		}
   }