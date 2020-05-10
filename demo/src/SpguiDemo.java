import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import spgui.SPWindow;
import spgui.componenet.SliderX;
import spgui.componenet.ToggleBtn;

/**
* This class is the Demo for Spgui.
* @author	Jun Hong Peng
* @version	1.0
* @since	2020-05-10
*/

public class SpguiDemo extends SPWindow{

	private static final long serialVersionUID = 1L;
	SliderX sliderR,sliderG,sliderB,sliderA;
	JTextField hexColorField;
	
	public SpguiDemo(int width,int height) {
		super(width,height);
		setTitle("SpguiDemo");
		CreatColorPalette();
		CreatToogleBtn();
	}

	public void CreatColorPalette() {
	       sliderR = new SliderX("Red", 0, 255, 255);
	       sliderR.setSliderColor(new Color(228,63,63), new Color(252,103,103), new Color(228,47,47));
	       sliderR.setBounds(10, 200, 380, 20);
	       addi(sliderR);
	       
	       sliderG = new SliderX("Green", 0, 255, 255);
	       sliderG.setSliderColor(new Color(62,224,95), new Color(86,232,124), new Color(42,224,89));
	       sliderG.setBounds(10, 250, 365, 20);
	       addi(sliderG);
	       
	       sliderB = new SliderX("Blue", 0, 255, 255);
	       sliderB.setBounds(10, 300, 365, 20);
	       addi(sliderB);
	       
	       sliderA = new SliderX("Alpha", 0, 255, 255);
	       sliderA.setSliderColor(new Color(65,65,65), new Color(155,155,155), new Color(59,59,59));
	       sliderA.setBounds(10, 350, 365, 20);
	       addi(sliderA);
	       
	       sliderR.setValue(getTitleBarColor().getRed());
	       sliderG.setValue(getTitleBarColor().getGreen());
	       sliderB.setValue(getTitleBarColor().getBlue());
	       sliderA.setValue(getTitleBarColor().getAlpha());
     
	       Observer watcher = new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				setTitleBarColor(new Color(sliderR.getValue(),sliderG.getValue(),sliderB.getValue(),sliderA.getValue()));
				String s = Integer.toHexString(sliderR.getValue());
				s += Integer.toHexString(sliderG.getValue());
				s += Integer.toHexString(sliderB.getValue());
				hexColorField.setText(s.toUpperCase());
				repaint();
			}
	       };
	       
	       sliderR.getWatched().addObserver(watcher);
	       sliderG.getWatched().addObserver(watcher);
	       sliderB.getWatched().addObserver(watcher);
	       sliderA.getWatched().addObserver(watcher);
	       
		   Border bottom = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
	       Border empty = new EmptyBorder(0, 2, 5, 2);
	       Border border = new CompoundBorder(bottom, empty);
		   
	       hexColorField = new JTextField();
	       hexColorField.setBounds(317 , 400, 60, 20);
	       hexColorField.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 16));
	       hexColorField.setOpaque(false);
	       hexColorField.setBorder(border);
	       hexColorField.addCaretListener(new CaretListener() {
			   @Override
			   public void caretUpdate(CaretEvent e) {
					 try {
						 sliderR.setValue(Integer.parseInt(hexColorField.getText().substring(0,2), 16));
						 sliderG.setValue(Integer.parseInt(hexColorField.getText().substring(2,4), 16));
						 sliderB.setValue(Integer.parseInt(hexColorField.getText().substring(4,6), 16));
				     }
				     catch (Exception error) {
				    	 
				     }
			   }
		   });
		   addi(hexColorField);

			String s = Integer.toHexString(sliderR.getValue());
			s += Integer.toHexString(sliderG.getValue());
			s += Integer.toHexString(sliderB.getValue());
			hexColorField.setText(s.toUpperCase());
	}
	
	public void CreatToogleBtn() {
		ToggleBtn btn = new ToggleBtn(50,26);
		btn.setBounds(10, 10, 120, 100);
		addi(btn);
		btn = new ToggleBtn(50,26);
		btn.setColor(new Color(100,150,225,255), new Color(60,90,225,255), new Color(60,90,225,100));
		btn.setBounds(10, 50, 120, 100);
		addi(btn);
	}
}