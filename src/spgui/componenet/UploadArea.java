package spgui.componenet;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class UploadArea extends JComponent {

	private static final long serialVersionUID = 1L;
	private Image upload = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/spgui/res/img/upload.png"))).getImage();
	private Image file = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/spgui/res/img/file.png"))).getImage();
	private Image files = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/spgui/res/img/files.png"))).getImage();
	private Area area = new Area(new RoundRectangle2D.Double(0, 0, 150, 150, 10, 10));
	private Graphics2D g2;
	private int width = 150;
	private int height = 150;
	private int size = 64;
	private int font_size = 14;
	private int offset = 8;
	String filePath = null;
	int file_num = 0;
	ArrayList<String> filePaths = new ArrayList<String>();
	Transferable transferable;
	
   public UploadArea(int width,int height,int size,int font_size,int offset) {
	   this.width = width;
	   this.height = height;
	   this.size = size;
	   this.font_size = font_size;
	   this.offset = offset;
	   setMouseEvent();
	   setDropEvent();
   }
   
   private void setMouseEvent() {
       addMouseListener(new MouseAdapter()
       {
           public void mouseClicked(MouseEvent e)
           {
        	   if(selectTextFile())
        	   {
        		   filePaths.clear();
        		   filePaths.add(filePath);
        		   file_num = 1;
        		   repaint();
        	   }
           }
       });
   }
   
   public boolean selectTextFile() {
       filePath = null;
       JFileChooser fileChooser = new JFileChooser();
       int returnValue = fileChooser.showOpenDialog(null);
       if (returnValue == JFileChooser.APPROVE_OPTION)
       {
           File selectedFile = fileChooser.getSelectedFile();
           filePath = selectedFile.getAbsolutePath();
       }
       return !(filePath == null || (filePath != null && ("".equals(filePath))));
   }
   
   private void setDropEvent()
   {
	   setDropTarget(new DropTarget() 
	   {   
		   private static final long serialVersionUID = 1L;
		   String transferData;
		   public void drop(DropTargetDropEvent dtde) 
		   {
			   transferable = dtde.getTransferable();
			   try 
			   {
				   dtde.acceptDrop(DnDConstants.ACTION_COPY);
				   transferData = dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor).toString();
			   } 
			   catch (UnsupportedFlavorException | IOException e) 
			   {
				   e.printStackTrace();
			   } 
//			   System.out.println("drop - drop:" + transferData);
			   processPath(transferData);
    		   repaint();
	        }
	    });
   }
   
   private void processPath(String data)
   {
	   int index = 1;
	   filePaths.clear();
	   for(int i = 1;i < data.length();i++)
	   {
		   if(data.indexOf("]") == -1)
		   {
			   filePaths.add(data);
			   break;
		   }
		   if(data.substring(i,i+1).matches("[,\\]]"))
		   {
			   filePaths.add(data.substring(index, i));
			   index = i + 2;
		   }
	   }
	   file_num = filePaths.size();
	   System.out.print(filePaths);
   }
   
   public ArrayList<String> getPath()
   {
	   return filePaths;
   }
   
   public void setSize(int width,int height)
   {
	   this.width = width;
	   this.height = height;
	   area = new Area(new RoundRectangle2D.Double(0, 0, width, height, 10, 10));
   }

   public boolean getStatus()
   {
	   return true;
   }

   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g2 = (Graphics2D) g;
      RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
      qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
      g2.setRenderingHints( qualityHints );  
      g2.setPaint(new Color(188,188,188,255));
	  Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
	  g2.setStroke(dashed);
	  g2.draw(new RoundRectangle2D.Double(0, 0, width - 5, height - 5, 10, 10));
      display();
   }
   
   private String getShortName(String text) {
	   int font_width = g2.getFontMetrics().stringWidth(text);
	   while(font_width > width){
		   font_width = g2.getFontMetrics().stringWidth(text);
		   text = (text.substring(0, text.length() - 1));
	   }
	   text = text.substring(0, text.length() - 3) + "...";
	   return text;
   }
   
   private void display() 
   {
	   if(file_num == 0)
		   upload();
	   else if(file_num == 1)
	   {
		   Font font = new Font("Fira Code Light", Font.PLAIN, font_size);
		   FontMetrics metrics = g2.getFontMetrics(font);
		   int text_y = (height - offset - size - metrics.getHeight()) / 2 + metrics.getAscent() + metrics.getDescent()/2 + size + offset;
		   g2.setFont(font);
		   int font_width = g2.getFontMetrics().stringWidth(getfileName());
		   String text = (font_width > width) ? getShortName(getfileName()) : getfileName();
		   font_width = g2.getFontMetrics().stringWidth(text);
		   g2.drawString(text, (width - 5 - font_width)/2, text_y);
		   float opacity = 0.3f;
		   g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		   g2.drawImage(file, (width - 5 - size)/2, (height - 5 - size)/2, size, size, null);
	   }
	   else
	   {
		   Font font = new Font("Fira Code Light", Font.PLAIN, font_size);
		   FontMetrics metrics = g2.getFontMetrics(font);
		   int text_y = (height - offset - size - metrics.getHeight()) / 2 + metrics.getAscent() + metrics.getDescent()/2 + size + offset;
		   String str = String.valueOf(file_num) + " files";
		   g2.setFont(font);
		   int font_width = g2.getFontMetrics().stringWidth(str);
		   g2.drawString(str, (width - 5 - font_width)/2, text_y);
		   float opacity = 0.3f;
		   g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		   g2.drawImage(files, (width - 5 - size)/2, (height - 5 - size)/2, size, size, null);
	   }
   }

   public void upload()
   {
	   Font font = new Font("Fira Code Light", Font.PLAIN, font_size);
	   FontMetrics metrics = g2.getFontMetrics(font);
	   int text_y = (height - offset - size - metrics.getHeight()) / 2 + metrics.getAscent() + metrics.getDescent()/2 + size + offset;
	   g2.setFont(font);
	   int font_width = g2.getFontMetrics().stringWidth("Upload File");
	   g2.drawString("Upload File", (width - 5 - font_width)/2, text_y);
	   float opacity = 0.3f;
	   g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	   g2.drawImage(upload, (width - 5 - size)/2, (height - 5 - size)/2, size, size, null);
   }
   
   public String getfileName()
   {
	   filePath = filePaths.get(0);
	   return filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length());
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