package spgui.componenet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import spgui.componenet.Slider.ObservedObject;

/**
* An extended version of <i>javax.swing.JComponent</i>.<br>
* This class builds component of the Slider set.<br>
* 
* @author	Jun Hong Peng
* @version	1.0
* @since	2020-05-10
* @param	titletext The value to be the title text.
* @param	min The value to be slider's minimum value.
* @param	max The value to be slider's maximum value.
* @param	width The value to be slider's width(not include title text and input field).
*/

public class SliderX extends JComponent
   {
	   private static final long serialVersionUID = 1L;
	   private Graphics2D g2;
	   private Area touchArea = new Area();
	   private int width = 380;
	   private int height = 20;
	   public Text title;
	   public Dot dot;
	   public Slider slider;
	   public JTextField valueField;
	   
	   public SliderX(String titletext,int min,int max,int width)
	   { 
		   this.width = width;
		   touchArea.add(new Area(new RoundRectangle2D.Double(0, 0, 50 + width + 60, height, 10, 10)));
		   setTitle(titletext);
		   setSlider(min,max,width);
		   setInputField();

	       Observer watcher = new Observer() {
				@Override
				public void update(Observable o, Object arg) {
					valueField.setText(String.valueOf(slider.getValue()));
					repaint();
				}
	       };
	       
	       slider.watched.addObserver(watcher);
	       

		   addMouseListener(new MouseAdapter()
		   {
				public void mouseClicked(MouseEvent e)
				{
					setValue(e.getX() - 50 - 8);
				}
		   });  
	   }
	   
	   private void setTitle(String titletext) {
		   title = new Text(titletext);
		   title.setBounds(0, 0, title.getTextWidth(), 30);
		   add(title);
	   }
	   
	   private void setSlider(int min,int max,int width) {
		   slider = new Slider(min,max,width);
		   slider.setBounds(50, 0, width + 20, 20);
		   add(slider);
	   }
	   
	   private void setInputField() {
		   Border bottom = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
	       Border empty = new EmptyBorder(0, 0, 5, 0);
	       Border border = new CompoundBorder(bottom, empty);
		   
		   valueField = new JTextField();
		   valueField.setBounds(50 + width + 20 , 0, 40, 20);
		   valueField.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 16));
		   valueField.setOpaque(false);
		   valueField.setBorder(border);
		   valueField.setText(String.valueOf(slider.getValue()));
		   valueField.addCaretListener(new CaretListener() {
			   @Override
			   public void caretUpdate(CaretEvent e) {
					 try {
				         int value = (int) Integer.valueOf(valueField.getText());
				         setValue(value);
				     }
				     catch (Exception error) {
				    	 
				     }
			   }
		   });
		   add(valueField);
	   }
	   
	   public ObservedObject getWatched() {
		   return slider.watched;
	   }
	   
	   public void setSliderColor(Color dotc,Color basebarc,Color fillbarc) {
			slider.setSliderColor(dotc, basebarc, fillbarc);
	   }
		
	   public void setValue(int value) {
		   slider.setValue(value);
	   }
  
	   public int getValue()
	   {
		   return slider.getValue();
	   }

	   public void paintComponent(Graphics g) 
	   {
		      super.paintComponent(g);
		      g2 = (Graphics2D) g;
		      RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
		      qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
		      g2.setRenderingHints( qualityHints );  
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

   