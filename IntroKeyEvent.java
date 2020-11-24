package asd;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class IntroKeyEvent implements KeyListener{
	IntroPanel introPanel;
	public IntroKeyEvent(IntroPanel introPanel) {
		this.introPanel=introPanel;
	}
	public void keyPressed1(KeyEvent e) {
		if(e.getKeyCode()==30) {
			introPanel.select=0;
		}
		else if(e.getKeyCode()==40) {
			introPanel.select=1;
		}
		else if(e.getKeyCode()==10) {
			if(introPanel.select==0) {}
			else if(introPanel.select==1)
				System.exit(0);
		}
		introPanel.repaint();
	}
	public void keyTyped(KeyEvent e) {		
	}

	public void keyPressed(KeyEvent e) {
	}
	
	public void keyReleased(KeyEvent e) {
	}
}