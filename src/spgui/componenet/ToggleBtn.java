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
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.Timer;

import spgui.lib.SplineInterpolator;

public class ToggleBtn extends JComponent
   {
	   private static final long serialVersionUID = 1L;
	   private Graphics2D g2;
	   private Area touchArea = new Area();
	   private Shape s;
       private Color fillbarc,dotc,flowdotc;
	   private int width = 50;
	   private int height = 26;
	   private int fill = 0;
	   private boolean mouseOn = false,status = false;
	   private Timer timer;
	   private SplineInterpolator splineInterpolator;
       private long startTime = -1;
       private long playTime = 500;
       private int result = 0;
  
	   public ToggleBtn(int width,int height)
	   { 
		   this.width = width;
		   this.height = height;
		   setColor(new Color(225,100,100,255),new Color(225,60,60,255),new Color(225,60,60,100));
		   
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
		        	timer.start();
		        }
		    });
		   setArea();
		   setAnimate();
	   }
	   
	   public void setColor(Color fillbarc,Color dotc,Color flowdotc) {
		   this.fillbarc = fillbarc;
		   this.dotc = dotc;
		   this.flowdotc = flowdotc;
	   }
	   
	   private void setAnimate() {
		   splineInterpolator = new SplineInterpolator(0.42, 0, 1, 1);
		   timer = new Timer(1, new ActionListener() {
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
                  result = ((int) Math.round((width) * progress));
                  fill = (status)?width - result: result;
                  if(result>=width)
                  {
					timer.stop();
					status = !status;
					startTime = -1;
                  }
	        	  repaint();
	          }
			});
			timer.setInitialDelay(0);
	   }
	   
	   private void setArea() {
		    s = new RoundRectangle2D.Double(height - height/2.88, height - height/2.88, width, height/1.44, height/1.44, height/1.44);
		    touchArea.add(new Area(s));
		    s = new RoundRectangle2D.Double(height - height/2.88, height - height/2.88, fill, height/1.44, height/1.44, height/1.44);
			touchArea.add(new Area(s));
			s = new Arc2D.Double(0, 0, height*2, height*2, 0, 360, Arc2D.OPEN);
			touchArea.add(new Area(s));
	   }
	   
	   private void paintBaseBar() 
	   {
		      g2.setPaint(new Color(194,194,194,255));
		      s = new RoundRectangle2D.Double(height, height - height/2.88, width, height/1.44, height/1.44, height/1.44);
		      g2.fill(s);
		      touchArea.add(new Area(s));
	   }
	   private void paintFillBar() 
	   {
			g2.setPaint(fillbarc);
			s = new RoundRectangle2D.Double(height, height - height/2.88, fill, height/1.44, height/1.44, height/1.44);
			g2.fill(s);
			touchArea.add(new Area(s));
	   }
	   private void paintDot() 
	   {
		   g2.setPaint(dotc);
		   s = new Arc2D.Double(height/2 + fill,height/2, height, height, 0, 360, Arc2D.OPEN);
		   g2.fill(s);
		   touchArea.add(new Area(s));
	   }
	   private void paintFlowDot() 
	   {
			g2.setPaint(flowdotc);
			s = new Arc2D.Double(0 + fill, 0, height*2, height*2, 0, 360, Arc2D.OPEN);
			g2.fill(s);
			touchArea.add(new Area(s));
	   }

	   public void paintComponent(Graphics g) 
	   {
		      super.paintComponent(g);
		      g2 = (Graphics2D) g;
		      RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
		      qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
		      g2.setRenderingHints( qualityHints );  
		      
		      paintBaseBar();
		      paintFillBar();
		      if(mouseOn)
		      {
		    	  paintFlowDot();
		      }
		      paintDot();

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

   