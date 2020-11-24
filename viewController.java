package asd;

import java.awt.Container;
public class viewController {
	menu mainFrame;
	Container contentPane;
	IntroPanel introPanel;
	public viewController(menu mainFrame) {
		this.mainFrame=mainFrame;
		init();
	}
	private void init() {
		introPanel=new IntroPanel(this);
		contentPane=mainFrame.getContentPane();
		contentPane.add(introPanel);
		introPanel.requestFocus();
	}
	
}