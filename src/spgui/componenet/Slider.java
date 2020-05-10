package spgui.componenet;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

/**
* An extended version of <i>javax.swing.JComponent</i>.<br>
* This class builds component of the Slider.<br>
* 
* @author	Jun Hong Peng
* @version	1.0
* @since	2020-05-10
* @param	min The value to be slider's minimum value.
* @param	max The value to be slider's maximum value.
* @param	width The value to be slider's width.
*/

public class Slider extends JComponent
{
		private static final long serialVersionUID = 1L;
		private Graphics2D g2;
		private int min,max,total,width,value = 0;
		private Dot dot;
		private Color basebarc = new Color(128,174,247),fillbarc = new Color(23,100,186);
		public ObservedObject watched;

		public Slider(int min,int max,int width) {
			   this.min = min;
			   this.max = max;
			   this.total = max - min;
			   this.width = width;
			   setDot();
			   watched = new ObservedObject(0);
			   setValue(0);
		}
		
		private void setDot() {
			 dot = new Dot();
			 dot.setColor(new Color(23,100,187,255));
			 dot.setBounds(0, 0, 20, 20);
			 DragListener drag = new DragListener();
			 dot.addMouseListener(drag);
			 dot.addMouseMotionListener(drag);
			 add(dot);
		}

		public void setValue(int value)
		{
			if(value <= max && value >= min)
			{
				this.value = value;
			}
			else if(value > max)
			{
				this.value = max;
			}
			else if(value < min){
				this.value = min;
			}
			dot.setLocation(width*this.value/total, dot.getY());    
			watched.setValue(this.value);
		}
		
		public int getValue() {
			return value;
		}
		
		public void setSliderColor(Color dotc,Color basebarc,Color fillbarc) {
			dot.setColor(dotc);
			this.basebarc = basebarc;
			this.fillbarc = fillbarc;
		}
			
		private void paintBaseBar() {
			g2.setPaint(basebarc);
			g2.fillRoundRect(6 , 6 , width, 4,4, 4);
		}
		
		private void paintFillBar() {
			g2.setPaint(fillbarc);
			g2.fillRoundRect(6 , 6 , width*value/total, 4,4, 4);
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
		}
		
		public class DragListener extends MouseInputAdapter 
		{
		       Point location;
		       MouseEvent pressed;
		       
		       public void mousePressed(MouseEvent me) {
		           pressed = me;
		       }

		       public void mouseDragged(MouseEvent me) {
		    	   Component c = me.getComponent();
		           location = c.getLocation(location);
		           int x = location.x - pressed.getX() + me.getX();
		           
		           x = (x >= width)?width:x;
		           x = (x <= 0)?0:x;
		           setValue(total * x/width);
		       }
		 }

		 public class ObservedObject extends Observable {
			   private int watchedValue;
			   
			   public ObservedObject(int watchedValue) {
				   this.watchedValue = watchedValue;
			   }
			   
			   public void setValue(int value) {
			      if(watchedValue != value) {
			         watchedValue = value;
			         setChanged();
			         notifyObservers();
			      }
			   }
		 }
   }