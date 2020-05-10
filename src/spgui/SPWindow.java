package spgui;
import java.awt.*;

import javax.swing.*;

import spgui.componenet.BoxShadow;
import spgui.lib.DragListener;
import spgui.winodw.Body;
import spgui.winodw.Header;

/**
* An extended version of <i>javax.swing.JFrame</i>.<br>
* This class builds the windows of Graphical User Interface that is similar as Mac style.<br>
* If you want to add new component in windows, the method should be used as follows:
* <p align="center">frame.addi(child);</p>
* 
* @author	Jun Hong Peng
* @version	1.0
* @since	2020-05-10
* @param	width The value to be the width of windows.
* @param	height The value to be the height of windows.
*/

public class SPWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLayeredPane pane = new JLayeredPane();
	private int width = 300;
	private int height = 80;
	public Header windowBar;
	public Body windowContent;
	private BoxShadow shadow;

	public SPWindow() {
		setFrame();
		setUI();
	}
	
   public SPWindow(int width,int height) {
	   this.width = width;
	   this.height = height;
	   setFrame();
	   setUI();
   }
   
   public void setTitle(String title)
   {
	   super.setTitle(title);
	   windowBar.setTitleText(title, Color.white);
   }
   
   public void setTitleBarColor(Color c) {
	   this.windowBar.bar_bgcolor = c;
   }
   
   public Color getTitleBarColor()
   {
	   return this.windowBar.bar_bgcolor;
   }
   
   public void setIcon(Image image)
   {
	   super.setIconImage(image);
   }
   
   private void setFrame()
   {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setUndecorated(true);
		setBackground(new Color(0,0,0,0));
		setSize(width, height);
		setBounds(0,0,width + 4,height + 4);
		setLocationByPlatform(true);
   }
   
   public JFrame getFrame()
   {
	   return this;
   }
   
   private void setUI()
   {
       pane.setBounds(0, 32, width + 4, height - 32);
       add(pane);
       windowBar = new Header(width,32);
       windowBar.setBounds(0, 0, width,32);
       DragListener drag = new DragListener(this);
       windowBar.addMouseListener(drag);
       windowBar.addMouseMotionListener(drag);
       add(windowBar);
       windowContent = new Body(width,height - 32);
       windowContent.setBounds(0, 0, width, height);
       add(windowContent);
       pane.add(windowContent,Integer.valueOf(100));
       shadow = new BoxShadow(width,height);
       shadow.setBounds(0, 0, width + 4, height + 4);
       add(shadow);
   }
   
   public void addi(JComponent j)
   {
	   pane.add(j,Integer.valueOf(200));
   }
   
   public void addi(JComponent j,int index)
   {
	   pane.add(j,Integer.valueOf(index));
   }
}