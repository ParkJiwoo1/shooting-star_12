package asd;


import java.awt.Dimension; 
import java.awt.Point; 
import javax.swing.JFrame;

public class menu extends JFrame{
	private static final long serialVersionUID = 1L;
	final static int FRAME_WIDTH=600;
	final static int FRAME_HEIGHT=500;
	viewController controller;
	
	public menu(String title) {
		super(title);
		this.setLocation(new Point(700,350));
		this.setVisible(true);
		this.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controller=new viewController(this);
	}
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		menu mainFrame = new menu("∞‘¿”");
	}
}
