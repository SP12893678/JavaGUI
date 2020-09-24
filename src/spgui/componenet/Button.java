package spgui.componenet;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Icon;
import javax.swing.JComponent;

public class Button extends JComponent {

	private static final long serialVersionUID = 1L;
	private Graphics2D g2;
	private Area touchArea = new Area();
	private int offset = 8;
	private int size = 0;
	private int width = 0;
	private int height = 0;
	private int radius = 0;
	private Icon icon = null;
	private String text = "";
	private Color color;
	private boolean mouseOn = false;
	
	public Button(int width,int height,int size,Icon icon,Color color,String text,int radius) {
		this.size = size;
		this.width = width;
		this.height = height;
		this.color = color;
		this.icon = icon;
		this.text = text;
		this.radius = radius;
		setEvent();
		
//		Ripple ripple = new Ripple(300,300);
//		ripple.setBounds(0,0,120,40);
//		add(ripple);
		
		Shape s = new RoundRectangle2D.Double(0, 0,width,height,radius,radius);
		touchArea.add(new Area(s));
	}
	
	private void setEvent() {
		addMouseListener(new MouseAdapter()
	    {
	        public void mouseEntered(MouseEvent e)
	        {
	        	mouseOn = true;
	        	repaint();
	        }
	        public void mouseExited(MouseEvent e)
	        {
	        	mouseOn = false;
	        	repaint();
	        }
	        public void mouseClicked(MouseEvent e)
	        {
	     
	        }
	    });
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
		g2.setRenderingHints( qualityHints );  
		
		Font font = new Font("Monaco", Font.PLAIN, size);
		g2.setPaint(color);
		g2.setFont(font);
		FontMetrics metrics = g2.getFontMetrics(font);
		int font_width = g2.getFontMetrics().stringWidth(text);
		
		
		int icon_x = (width - font_width - icon.getIconWidth())/2;
		icon.paintIcon(this, g2, (width - offset - font_width - icon.getIconWidth())/2,(height - icon.getIconHeight())/2 );
		
		int text_y = (height - metrics.getHeight()) / 2 + metrics.getAscent() + metrics.getDescent()/2;
		g2.drawString(text, icon_x + icon.getIconWidth() + offset, text_y );
		
		g2.setPaint(color);
		g2.drawRoundRect(0, 0, width ,height , radius, radius);
		
		if(mouseOn) {
			g2.setPaint(new Color(color.getRed(),color.getGreen(),color.getBlue(),55));
			g2.fillRoundRect(0, 0, width,height , radius, radius);
		}
		
	}
	
	@Override
	public boolean contains(int x, int y)
	{
		Rectangle bounds = touchArea.getBounds();
		Insets insets = getInsets();
		int translateX = x + bounds.x - insets.left;
		int translateY = y + bounds.y - insets.top;
		return touchArea.contains(translateX, translateY);
	}
}