package spgui.winodw;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

import spgui.componenet.Dot;

/**
* An extended version of <i>javax.swing.JComponent</i>.<br>
* This class builds the bar of windows.<br>
* 
* @author	Jun Hong Peng
* @version	1.0
* @since	2020-05-10
* @param	width The value to be the width of windows bar.
* @param	height The value to be the height of windows bar.
*/

public class Header extends JComponent {

	private static final long serialVersionUID = 1L;
	private Area area = new Area();
	private Graphics2D g2;
	private int width = 100;
	private int height = 32;
	public Color bar_bgcolor = new Color(33,33,33,150);
	private String title_text = "Welcome to SP_GUI";
	private Color title_textcolor = new Color(255,255,255,255);
	private Image icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/spgui/res/img/java.png"))).getImage();
	private JFrame frame = null;
	private Dot minDot,reDot,closeDot;
	
   public Header(int width,int height) {
		this.width = width;
		this.height = height;
		area.add(new Area(new RoundRectangle2D.Double(0, 0, width, height, 10, 10)));
		setControlDot();
   }
   
   private void checkframe() {
	   if(this.frame == null)
		   this.frame = (JFrame) SwingUtilities.getWindowAncestor(this.getParent());
   }
   
   private void setControlDot()
   {
		 Color[] red = {new Color(235,90,90,255), new Color(255,111,111,255)}; 
		 Color[] yellow = {new Color(234,158,60,255), new Color(252,173,70,255)}; 
		 Color[] green = {new Color(75,197,82,255), new Color(76,235,98,255)}; 
		 /*-------------------------------------*/
	     minDot = new Dot();
	     reDot = new Dot();
	     closeDot = new Dot();
	     /*-------------------------------------*/
	     minDot.setGradientColor(green);
	     reDot.setGradientColor(yellow);
	     closeDot.setGradientColor(red);
	     /*-------------------------------------*/
	     minDot.setBounds(width - 80, 8, 16, 16);
	     reDot.setBounds(width - 56, 8, 16, 16);
	     closeDot.setBounds(width - 32, 8, 16, 16);
	     /*-------------------------------------*/
	     minDot.addMouseListener(new MouseAdapter()
	     {
	           public void mouseClicked(MouseEvent e)
	           {
	        	   checkframe();
	        	   frame.setState(Frame.ICONIFIED);
	           }
	     });
	     reDot.addMouseListener(new MouseAdapter()
	     {
	           public void mouseClicked(MouseEvent e)
	           {
	        	   checkframe();
	           }
	     });
	     closeDot.addMouseListener(new MouseAdapter()
	     {
	           public void mouseClicked(MouseEvent e)
	           {
	        	   checkframe();
	        	   frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	           }
	     });
	     /*-------------------------------------*/
	     add(minDot);
	     add(reDot);
	     add(closeDot);
   }
   
   public void setTitleText(String text,Color c) {
	   this.title_text = text;
	   this.title_textcolor = c;
   }
   
   public void setIcon(Image img) {
	   this.icon = img;
   }
   
   public void paintTitleBar() {
	  	  Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, 16, 16));
	  	  area.add(new Area(new RoundRectangle2D.Double(0, height - 10, width, 10, 2, 2)));
	      g2.setPaint(bar_bgcolor);
	      g2.fill(area);
   }
   
   private void paintTitleText() {
	   g2.setPaint(title_textcolor);
	   g2.setFont(new Font("Fira Code Light", Font.PLAIN, 16));
	   FontRenderContext frc = g2.getFontRenderContext();
       int font_height = (int) g2.getFont().createGlyphVector(frc, title_text).getVisualBounds().getHeight();
	   g2.drawString(title_text, 32, (32 - font_height)/2 + font_height);
   }
   
   private void paintIcon() {
	   g2.drawImage(icon, 8, 8, 16, 16, null);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g2 = (Graphics2D) g;
      RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
      qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
      g2.setRenderingHints( qualityHints );  
      paintTitleBar();
      paintTitleText();
      paintIcon();
   }
   
   @Override
   public boolean contains(int x, int y)
   {
       Rectangle bounds = area.getBounds();
       Insets insets = getInsets();
       int translateX = x + bounds.x - insets.left;
       int translateY = y + bounds.y - insets.top;
       return area.contains(translateX, translateY);
   }
}