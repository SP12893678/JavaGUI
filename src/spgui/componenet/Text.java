package spgui.componenet;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
* An extended version of <i>javax.swing.JComponent</i>.<br>
* This class builds component of the text.<br>
* 
* @author	Jun Hong Peng
* @version	1.0
* @since	2020-05-10
* @param	text The value to be rendering.
*/

public class Text extends JComponent
{
		private static final long serialVersionUID = 1L;
		private Graphics2D g2;
		private String text;
		private Font fontstyle = new Font(null, Font.PLAIN, 16);
		private Color color = Color.black;

		public Text(String text) {
			this.text = text;
		}
		
		public void setFontStyle(Font fontstyle)
		{
			this.fontstyle = fontstyle;
		}
		
		public void setColor(Color c) {
			this.color = c;
		}
		
		public int getTextWidth() {
			AffineTransform affinetransform = new AffineTransform();     
			FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
			return (int)(fontstyle.getStringBounds(text, frc).getWidth());
		}

		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			g2 = (Graphics2D) g;
			RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
			qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
			g2.setRenderingHints( qualityHints );  
			g2.setPaint(color);
			g2.setFont(fontstyle);
			g2.drawString(text,  0, 14);
		}
   }