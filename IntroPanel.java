package asd;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class IntroPanel extends JPanel{
	int select=0;
	viewController controller;
	public IntroPanel(viewController controller) {
		this.controller=controller;
		this.addKeyListener(new IntroKeyEvent(this));
	}
	public void paint(Graphics g) {
		g.setFont(new Font("myFont",Font.BOLD ,30));
		g.setColor(Color.green);
		g.drawString("½´ÆÃ °ÔÀÓ",120,100);
		g.setFont(new Font("secondFont",Font.ITALIC,20));
		g.setColor(Color.black);
		g.drawString("GameStart",130,250);
		g.drawString("Quit", 130, 300);
		if(select==0)
			g.drawString("->", 100, 250);
		else
			g.drawString("->", 100, 300);
	}

}