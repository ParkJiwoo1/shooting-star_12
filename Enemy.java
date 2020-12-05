package hero_flight;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy {
	int x, y, width, height, HP;
	String type;
	Image EnemyImage;
	
	Enemy(int x, int y, int HP, String type) {
		switch(type){
			case "monster" :
				this.EnemyImage = new ImageIcon(Main.class.getResource("../images/monster.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 80;
				this.height = 86;
				this.HP = HP;
				this.type = "monster";
				break;
			case "Meteorite" :
				this.EnemyImage = new ImageIcon(Main.class.getResource("../images/Meteor.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 300;
				this.height = 100;
				this.HP = HP;
				this.type = "Meteorite";
				break;
			case "monster2" :
				this.EnemyImage = new ImageIcon(Main.class.getResource("../images/monster2.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 113;
				this.height = 150;
				this.HP = HP;
				this.type = "monster2";
				break;
			case "boss" :
				this.EnemyImage = new ImageIcon(Main.class.getResource("../images/boss.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 262;
				this.height = 300;
				this.HP = HP;
				this.type = type;
				break;
			case "Megaboss" :
				this.EnemyImage = new ImageIcon(Main.class.getResource("../images/Megaboss.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 293;
				this.height = 300;
				this.HP = HP;
				this.type = type;
				break;
		}
	}
	
	public void move() {
		switch (type) {
			case "monster":
			case "monster2":
				this.x-=2;
				break;
			case "Meteorite":
				this.x-=10;
				break;
			case "boss":
				break;
			case "Megaboss":
				break;
			default:
				break;
		}
	}
}
