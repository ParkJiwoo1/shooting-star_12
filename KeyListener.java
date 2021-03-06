package hero_flight;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter{
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_W :
				Hero_flight.game.true_Up();
				break;
			case KeyEvent.VK_S :
				Hero_flight.game.true_Down();
				break;
			case KeyEvent.VK_A :
				Hero_flight.game.true_Left();
				break;
			case KeyEvent.VK_D :
				Hero_flight.game.true_Right();
				break;
			case KeyEvent.VK_SPACE :
				Hero_flight.game.true_Shooting();
				break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_W :
				Hero_flight.game.false_Up();
				break;
			case KeyEvent.VK_S :
				Hero_flight.game.false_Down();
				break;
			case KeyEvent.VK_A :
				Hero_flight.game.false_Left();
				break;
			case KeyEvent.VK_D :
				Hero_flight.game.false_Right();
				break;
			case KeyEvent.VK_SPACE :
				Hero_flight.game.false_Shooting();
				break;
		}
	}
}
