package spgui.componenet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import spgui.lib.SplineInterpolator;

public class Ripple extends JComponent {

	private static final long serialVersionUID = 1L;
	private Graphics2D g2;
	private Shape area;
	private ArrayList<Shape> ripples = new ArrayList<Shape>();
	private int width = 0;
	private int height = 0;
	Timer timer;
	
	public Ripple(int width,int height) {
		
		this.width = width;
		this.height = height;
		this.area = new Rectangle2D.Double(0, 0, width,height);
		
		
		
		addMouseListener(new MouseAdapter()
	    {
			public void mouseEntered(MouseEvent e)
			{
				e.getComponent().getParent().dispatchEvent(e);
			}
			public void mouseExited(MouseEvent e)
			{       
				e.getComponent().getParent().dispatchEvent(e);
				repaint();
			}
	        public void mouseClicked(MouseEvent e)
	        {
	        	System.out.print("click");
	        	addAndPlayRipple(e);
	        	repaint();
	        }
	    });
	}
	
	protected void addAndPlayRipple(MouseEvent e) {
//		Shape shape = new Arc2D.Double(0, 0, 0, 0, 0, 360, Arc2D.OPEN);
//		ripples.add(shape);
		
		timer = new Timer(1, new ActionListener() {
				SplineInterpolator splineInterpolator = new SplineInterpolator(0.42, 0, 1, 1);
			   private long startTime = -1;
		       private long playTime = 5000;
			
	          @Override
	          public void actionPerformed(ActionEvent e) {
	        	  
                if (startTime < 0) 
                {
                    startTime = System.currentTimeMillis();
                }
                long now = System.currentTimeMillis();
                long duration = now - startTime;
                double t = (double) duration / (double) playTime;
                if (duration >= playTime) 
                {
                    t = 1;
                }
                double progress = splineInterpolator.interpolate(t);
                int result = ((int) Math.round((width) * progress));
                if(ripples.size()>=1)ripples.remove(0);
                Shape shape = new Arc2D.Double(-result/2, -result/2, result, result, 0, 360, Arc2D.OPEN);
            	ripples.add(shape);
            	
              	System.out.println(ripples.size());
                if(result>=width)
                {
                	timer.stop();
                	ripples.removeAll(ripples);
                }
	        	repaint();
	          }
			});
		timer.setInitialDelay(0);
		timer.start();
	}

	public JComponent getRipple() {
		return this;
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
		g2.setRenderingHints( qualityHints );  
		
		for(int index = 0;index < ripples.size() ;index++) {
			g2.setPaint(new Color(0,0,0,20));
			g2.fill(ripples.get(index));
		}
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