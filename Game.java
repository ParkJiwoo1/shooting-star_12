package hero_flight;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread{
	private Image player;
	private Image fire;
	private Image shield = new ImageIcon(Main.class.getResource("../images/shield.png")).getImage();
	private Image backgroundImage = new ImageIcon(Main.class.getResource("../images/Game_screen.png")).getImage();
	private Image victory = new ImageIcon(Main.class.getResource("../images/victory.png")).getImage();

	private int playerX, playerY, playerHP;
	private int playerwidth, playerheight;
	private int attack, attackspeed, speed;	// ?îå?†à?ù¥?ñ¥ ?úÑÏπ?, Í≥µÍ≤© ?úÑÏπ?, Í≥µÍ≤©?†•, Í≥µÍ≤©Ï£ºÍ∏∞, ?ù¥?èô?Üç?èÑ
	private int score;
	private int backgroundX;
	private int cnt; //Ï£ºÍ∏∞
	private int spawnX, spawnY;

	private boolean up, down, left, right, shooting;	// ?Ç§ ?ûÖ?†•?ùÑ Î∞õÍ∏∞ ?úÑ?ïú Î≥??àò
	private boolean boss, meteor, hyperbeam, thunder, removeThunder, protect;	// Î≥¥Ïä§ ?ñâ?èô Ï°∞Ï†à
	private boolean win, over;	// ?ì∞?†à?ìú Ï¢ÖÎ£å

	ArrayList<Player_attack> playerAttackList = new ArrayList<Player_attack>();	// ?îå?†à?ù¥?ñ¥ Í≥µÍ≤© Î∞∞Ïó¥
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>();	// ?†Å Î∞∞Ïó¥
	ArrayList<Enemy_attack> enemyAttackList = new ArrayList<Enemy_attack>();	// ?? Í≥µÍ≤© Î∞∞Ïó¥

	Player_attack playerAttack;
	Enemy enemy;
	Enemy_attack enemyAttack;

	public static Boss BOSS = new Boss();

	public void playerDraw(Graphics2D g) {
		g.drawImage(player, playerX, playerY, null);	// ?îå?†à?ù¥?ñ¥ Í∑∏Î¶¨Í∏?

		for(int i=0; i<playerAttackList.size(); i++) {
			playerAttack = (Player_attack)(playerAttackList.get(i));
			g.drawImage(fire, playerAttack.x, playerAttack.y, null);	// ?îå?†à?ù¥?ñ¥ Í≥µÍ≤© Í∑∏Î¶¨Í∏?
		}
	}

	public void enemyDraw(Graphics2D g) {
		for(int i=0; i<enemyList.size(); i++) {
			enemy = (Enemy)(enemyList.get(i));
			switch (enemy.type) {
				case "monster":
					g.setColor(Color.GREEN);
					g.fillRect(enemy.x, enemy.y-20, enemy.HP*7, 10);
					break;
				case "monster2":
					g.setColor(Color.GREEN);
					g.fillRect(enemy.x, enemy.y-20, enemy.HP*5, 10);
					break;
				case "boss":
					g.setColor(Color.BLUE);
					g.fillRect(enemy.x-15, enemy.y-20, 300, 30);
					g.setColor(Color.GREEN);
					g.fillRect(enemy.x-15, enemy.y-20, (int)(enemy.HP/2), 30);
					break;
				case "Megaboss":
					if (protect)
						g.drawImage(shield, enemy.x-43, enemy.y-40, null);
					g.setColor(Color.BLUE);
					g.fillRect(enemy.x-15, enemy.y-20, (int)(enemy.HP/6), 30);
					break;
			}
			g.drawImage(enemy.EnemyImage, enemy.x, enemy.y, null);
			enemy.move();
			if(enemy.x < 0) {
				enemyList.remove(enemyList.get(i));
				i--;
			}
		}
		for(int j=0; j<enemyAttackList.size(); j++) {
			enemyAttack = (Enemy_attack)(enemyAttackList.get(j));
			g.drawImage(enemyAttack.EnemyAttackImage, enemyAttack.x, enemyAttack.y, null);
		}
	}

	public void backgroundDraw(Graphics2D g) {
		g.drawImage(backgroundImage, backgroundX, 0, null);
		backgroundX--;
		if(backgroundX < -760)	// backgroundX = Í∑∏Î¶º Í∞?Î°úÌîΩ?? +1280
			g.drawImage(backgroundImage, Main.SCREEN_WIDTH-((backgroundX*-1)-760), 0, null);
		if(backgroundX == -2040)	// -(Í∑∏Î¶º Í∞?Î°úÍ∏∏?ù¥)
			backgroundX=0;
	}

	public void gameInfoDraw(Graphics g) {
		if(win) {
			g.drawImage(victory, 440, 305, null);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("SCORE : "+score, 40, 90);
		g.drawString("HP : ", 40, 140);
		g.setColor(Color.RED);
		g.fillRect(140, 110, 300, 40);
		g.setColor(Color.GREEN);
		g.fillRect(140, 110, playerHP*3, 40);
	}

	@Override
	public void run() {
		Hero_flight.audio.PlayLoop("src/audio/backgroundmusic.wav");
		BOSS.start();
		Init();	// Ï¥àÍ∏∞?ôî
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			while (!over && !win) {
				KeyProcess();
				EnemySpawnProcess();
				playerAttackprocess();
				EnemyAttackprocess();
				Crashcheck();
				try {
					Thread.sleep(20);	// 0.02Ï¥àÍ∞Ñ?ùò ?ì∞?†à?ìú ?ä¨Î¶?
					cnt++;
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public void Init() {
		player = new ImageIcon(Main.class.getResource("../images/player.png")).getImage();
		fire = new ImageIcon(Main.class.getResource("../images/player_attack.png")).getImage();
		backgroundImage = new ImageIcon(Main.class.getResource("../images/Game_screen.png")).getImage();

		enemyList.clear();
		playerAttackList.clear();
		enemyAttackList.clear();

		backgroundX = 0;
		playerX = 10;
		playerY = 300;
		playerHP = 200;
		playerwidth = 120;
		playerheight = 125;
		attack = 5;
		attackspeed = 12;
		speed = 7;
		score = 0;
		cnt = 0;
		left = false;
		right = false;
		up = false;
		down = false;
		shooting = false;
		boss = false;
		BOSS.start(false);
		BOSS.setMegaboss(false);
		win = false;
		over = false;

		try {
			Thread.sleep(1000);	// 1Ï¥? ??Í∏?
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}	// Í≤åÏûÑ Ï¥àÍ∏∞?ôî Î©îÏÜå?ìú

	private void KeyProcess() {
		if (up && playerY-speed > 30) {
			playerY-=speed;
		}
		if (down && playerY+speed+playerheight < 720) {
			playerY+=speed;
		}
		if (left && playerX-speed > 0) {
			playerX-=speed;
		}
		if (right && playerX+speed+playerwidth < 1280) {
			playerX+=speed;
		}
		if (shooting) {
			if (cnt%attackspeed == 0) {	// ?ïà?óê ?ûà?äî ?à´?ûêÍ∞? ?ûë?ùÑ?àòÎ°? Í≥µÍ≤©Ï£ºÍ∏∞Í∞? ÏßßÏùå
				if (attack == 10)
					playerAttack = new Player_attack(playerX+150, playerY+70, attack);
				else
					playerAttack = new Player_attack(playerX+200, playerY+30, attack);
				playerAttackList.add(playerAttack);
			}
			else
				return;
		}
	}	// ?Ç§ ?ûÖ?†• Î∞õÍ∏∞ Î©îÏÜå?ìú

	private void MegaEvolution() {
		int x = playerX;
		int y = playerY;
		Hero_flight.audio.PlaySound("src/audio/charizard-megax.wav");
		player = new ImageIcon(Main.class.getResource("../images/megastone.png")).getImage();
		playerwidth = 185;
		playerheight = 100;
		playerAttackList.clear();
		try {
			Thread.sleep(1000);	// 1Ï¥? ??Í∏?
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		playerX = x;
		playerY = y;
		player = new ImageIcon(Main.class.getResource("../images/Mega_player.png")).getImage();
		fire = new ImageIcon(Main.class.getResource("../images/Mega_player_attack.png")).getImage();
		attack = 10;
		attackspeed = 6;
		speed = 10;
	}

	private void EnemySpawnProcess() {
		if (score >= 5000 && !boss) {
			enemyList.clear();
			Hero_flight.audio.PlaySound("src/audio/bossRoar.wav");
			enemy = new Enemy(900, 225, 600, "boss");
			enemyList.add(enemy);
			enemyAttackList.clear();
			playerAttackList.clear();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boss = true;
			BOSS.start(boss);
			backgroundImage = new ImageIcon(Main.class.getResource("../images/Game_screen2.png")).getImage();
		}	

		else if (!boss && cnt!=0) {
			if (cnt%300 == 0) {
				for (int i=0; i<3; i++) {
					spawnX = (int)(Math.random()*181)+1000;
					spawnY = (int)(Math.random()*591)+30;
					enemy = new Enemy(spawnX, spawnY, 10, "monster");
					enemyList.add(enemy);
				}
			}
			if (cnt%500 == 0) {
				Hero_flight.audio.PlaySound("src/audio/meteor_falling.wav");
				for (int i=0; i<2; i++) {
					spawnX = (int)(Math.random()*181)+1000;
					spawnY = (int)(Math.random()*591)+30;
					enemy = new Enemy(spawnX, spawnY, 50, "monster2");
					enemyList.add(enemy);
				}
			}
			if (cnt%700 == 0) {
				spawnX = (int)(Math.random()*181)+1000;
				spawnY = (int)(Math.random()*591)+30;
				enemy = new Enemy(spawnX, spawnY, 20, "monster2");
				enemyList.add(enemy);
			}
		}
	}

	private void EnemyAttackprocess() {
		if(boss) {
			meteor = (BOSS.isMeteor());
			if(!hyperbeam && BOSS.isHyperbeam())
				Hero_flight.audio.PlaySound("src/audio/HyperBeam.wav");
			hyperbeam = (BOSS.isHyperbeam());
			if(!thunder && BOSS.isThunder())
				Hero_flight.audio.PlaySound("src/audio/thunder.wav");
			thunder = (BOSS.isThunder());
			removeThunder = (BOSS.isRemoveThunder());
			protect = (BOSS.isProtect());
		}

		for(int i=0; i<enemyList.size(); i++) {
			if (cnt%100 == 0) {
				enemy = (Enemy)(enemyList.get(i));
				switch (enemy.type) {
					case "monster":
						enemyAttack = new Enemy_attack(enemy.x-30, enemy.y+25, "PigeonAttack");
						enemyAttackList.add(enemyAttack);
						break;
					case "monster2":
						enemyAttack = new Enemy_attack(enemy.x-30, enemy.y, "DragoniteAttack1");
						enemyAttackList.add(enemyAttack);
						enemyAttack = new Enemy_attack(enemy.x-30, enemy.y+50, "DragoniteAttack2");
						enemyAttackList.add(enemyAttack);
						enemyAttack = new Enemy_attack(enemy.x-30, enemy.y+100, "DragoniteAttack3");
						enemyAttackList.add(enemyAttack);
						break;
				}
			}

			if (boss) {
				if (enemy.type == "boss" && meteor == true && cnt%60 == 0) {
					Hero_flight.audio.PlaySound("src/audio/meteor_falling.wav");
					spawnX = (int)(Math.random()*181)+1000;
					spawnY = playerY;
					enemyAttack = new Enemy_attack(spawnX, spawnY, "DracoMeteor");
					enemyAttackList.add(enemyAttack);
				}
				else if (enemy.type == "Megaboss" && meteor == true && cnt%40 == 0) {
					Hero_flight.audio.PlaySound("src/audio/meteor_falling.wav");
					spawnX = (int)(Math.random()*181)+1000;
					spawnY = playerY;
					enemyAttack = new Enemy_attack(spawnX, spawnY, "DracoMeteor");
					enemyAttackList.add(enemyAttack);
				}
				else if(enemy.type == "boss" && hyperbeam && cnt%2 == 0) {
					enemyAttack = new Enemy_attack(enemy.x-100, enemy.y+100, "Hyperbeam");
					enemyAttackList.add(enemyAttack);
				}
				else if(enemy.type == "Megaboss" && hyperbeam && cnt%2 == 0) {
					enemyAttack = new Enemy_attack(enemy.x-100, enemy.y+100, "MegaHyperbeam");
					enemyAttackList.add(enemyAttack);
				}
				else if (enemy.type == "Megaboss" && thunder == true && cnt%5 == 0) {
					spawnX = BOSS.getThunderX();
					enemyAttack = new Enemy_attack(spawnX, 30, "Thunder");
					enemyAttackList.add(enemyAttack);
				}
			}	// Î≥¥Ïä§ ?ñâ?èô Ï≤òÎ¶¨
		}

		for(int j=0; j<enemyAttackList.size(); j++) {
			enemyAttack = (Enemy_attack)(enemyAttackList.get(j));
			enemyAttack.fire();

			if(removeThunder && enemyAttack.type == "Thunder") {
				enemyAttackList.remove(enemyAttackList.get(j));
			}

			if(enemyAttack.x < playerX+playerwidth && playerX < enemyAttack.x + enemyAttack.width && enemyAttack.y + enemyAttack.height > (playerY + 50) && enemyAttack.y < (playerY + 50) + (playerheight - 50)) {
				Hero_flight.audio.PlaySound("src/audio/hitten_sound.wav");
				playerHP -= enemyAttack.attack;
				enemyAttackList.remove(enemyAttackList.get(j));
			}
			if(enemyAttack.x < 0) {
				enemyAttackList.remove(enemyAttackList.get(j));
			}
		}

		if (playerHP <= 0) {
			enemyList.clear();
			playerAttackList.clear();
			enemyAttackList.clear();
			over = true;
			Hero_flight.gameOver();
		}
	}

	private void playerAttackprocess() {
		try {
			for(int i=0; i<playerAttackList.size(); i++) {
				playerAttack = (Player_attack)(playerAttackList.get(i));
				playerAttack.move();
				for(int j=0; j<enemyList.size(); j++) {
					enemy = (Enemy)(enemyList.get(j));
					if (playerAttack.x + 50 > enemy.x && playerAttack.x + 50 < enemy.x + enemy.width && playerAttack.y + 50 > enemy.y && playerAttack.y < enemy.y+enemy.height) {
						if (enemy.type != "Megaboss" || !protect)
							enemy.HP -= attack;
						playerAttackList.remove(playerAttackList.get(i));
						if (enemy.HP <= 0) {
							Hero_flight.audio.PlaySound("src/audio/Ember.wav");
							switch (enemy.type) {
								case "monster":
									score += 1000;
									enemyList.remove(enemyList.get(j));
									j--;
									break;
								case "monster2":
									score += 2000;
									enemyList.remove(enemyList.get(j));
									j--;
									break;
								case "boss":
									score += 20000;
									Hero_flight.audio.PlaySound("src/audio/bossRoar.wav");
									enemyAttackList.clear();
									try {
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									enemyList.remove(enemyList.get(j));
									j--;
									enemy = new Enemy(900, 225, 1800, "Megaboss");
									enemyList.add(enemy);
									Hero_flight.audio.PlaySound("src/audio/charizard-megax.wav");
									MegaEvolution();
									BOSS.setMegaboss(true);
									break;
								case "Megaboss":
									score += 30000;
									enemyList.remove(enemyList.get(j));
									j--;
									win = true;
							}
						}
					}
				}
				if(playerAttack.x > 1240) {
					playerAttackList.remove(playerAttackList.get(i)); // ?îå?†à?ù¥?ñ¥ ÎØ∏ÏÇ¨?ùº Ïß??ö∞Í∏?
					i--;
				}
			}
		} catch (Exception e) {}
	}

	private void Crashcheck() {
		for(int i=0; i<enemyList.size(); i++) {
			enemy = (Enemy)(enemyList.get(i));
			if (playerX + playerwidth > enemy.x && enemy.x+enemy.width > playerX && (playerY + 50) + (playerheight - 50) > enemy.y && enemy.y + enemy.height > (playerY + 50)) {
				switch (enemy.type) {
					case "monster":
						playerHP -= 10;
						Hero_flight.audio.PlaySound("src/audio/hitten_sound.wav");
						enemyList.remove(enemyList.get(i));
						i--;
						break;
					case "monster2":
						playerHP -= 30;
						Hero_flight.audio.PlaySound("src/audio/hitten_sound.wav");
						enemyList.remove(enemyList.get(i));
						i--;
						break;
					case "boss":
					case "Megaboss":
						playerHP -= 1;
						Hero_flight.audio.PlaySound("src/audio/hitten_sound.wav");
						break;
				}
			}
		}
	}

	public void true_Up() {
		if(up)
			return;
		up = true;
	}

	public void true_Down() {
		if(down)
			return;
		down = true;
	}

	public void true_Left() {
		if(left)
			return;
		left = true;
	}

	public void true_Right() {
		if(right)
			return;
		right = true;
	}

	public void true_Shooting() {
		if(shooting)
			return;
		shooting = true;
	}

	public void false_Up() {
		if(!up)
			return;
		up = false;
	}

	public void false_Down() {
		if(!down)
			return;
		down = false;
	}

	public void false_Left() {
		if(!left)
			return;
		left = false;
	}

	public void false_Right() {
		if(!right)
			return;
		right = false;
	}

	public void false_Shooting() {
		if(!shooting)
			return;
		shooting = false;
	}

	public boolean isWin() {
		return win;
	}

	public boolean isOver() {
		return over;
	}

}