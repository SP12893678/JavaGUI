import javax.swing.SwingUtilities;

public class Main{
	
	public static void main(String[] argv)
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() {
				new SpguiDemo(800,600).setVisible(true);;
			}
		});
	}
}