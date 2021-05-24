package destiny.panels;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import destiny.assets.Constants;
import destiny.assets.Enemy;
import destiny.assets.Player;
import destiny.assets.RippleCursor;
import destiny.assets.Character;
import destiny.core.FadeImage;
import destiny.core.PButton;
import destiny.core.Screen;
import destiny.core.ScreenManager;
import destiny.core.SoundPlayer;
import processing.core.PApplet;
import processing.core.PImage;
import destiny.net.MongoHandler;
/**
 * BattleScreen is where the user can battle with their revolutionaries
 * 
 * @author Jay Paek
 * @version 5/7/2021
 *
 */
public class BattleScreen implements Screen {

	private FadeImage background;
	private RippleCursor cursor;
	private PButton button, back;
	private int revSelect;
	private int[]  move;
	private int target, enemyTarget;
	private Character[] revs;
	private Enemy[] enemies;
	private PButton[] select;
	private boolean win = false, lose = false;;
	private FadeImage victory, defeat;
	private int level;
	private SoundPlayer sound;
	private PButton setting;
	@Override
	public void setup(PApplet window) {
		background = new FadeImage("res/battlePrepScreen/nathaniel.PNG");
		background.setFadeSpeed(50);
		victory = new FadeImage("res/battleScreen/victory.png");
		defeat = new FadeImage("res/battleScreen/defeat.png");
		cursor = RippleCursor.createLowPerformanceCursor();
		revSelect = 0;
		move = new int[3];
		sound = new SoundPlayer(Constants.getSoundPath("bgm.wav"));
		sound.loop();
		target = 2;
		enemyTarget = 2;
		revs = new Character[3];
		level = Player.getLevel();
		for(int i = 0; i < 3 ; i++) {
			revs[i] = new Character(BattlePrepScreen.revsSelect[i]);
		}
		enemies = new Enemy[] { new Enemy(level, true), new Enemy(level, true), new Enemy(level, true) };
		try {
			button = new PButton(
					new Rectangle(Constants.SCREEN_WIDTH - Constants.scaleIntToWidth(450),
							Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(250), Constants.scaleIntToWidth(400),
							Constants.scaleIntToWidth(200)),
					new PImage(ImageIO.read(new File("res/generalAssets/play.png"))), false);
			back = new PButton(
					new Rectangle(Constants.scaleIntToWidth(50),
							Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(250), Constants.scaleIntToWidth(200),
							Constants.scaleIntToHeight(200)),
					new PImage(ImageIO.read(new File("res/generalAssets/back.png"))), false);
			setting = new PButton(
					new Rectangle(Constants.scaleIntToWidth(50),
							Constants.scaleIntToHeight(50), Constants.scaleIntToWidth(100),
							Constants.scaleIntToHeight(100)),
					new PImage(ImageIO.read(new File("res/generalAssets/setting.png"))), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		background.setCoords(0, 0);
		background.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		victory.setCoords(0, 0);
		victory.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		defeat.setCoords(0, 0);
		defeat.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		defeat.setFadeSpeed(50);
		victory.setFadeSpeed(50);
		select = new PButton[5];
		
		for (int i = 0; i < 5; i++) {
			final PButton b;
			int id = i;
			try {
				b = new PButton(
						new Rectangle(Constants.scaleIntToWidth(100 + (i * 350)),
								Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(300),
								Constants.scaleIntToWidth(300), Constants.scaleIntToWidth(250)),
						new PImage(ImageIO.read(new File("res/generalAssets/obama.png"))), false);
				b.addListener(new Runnable() {
					@Override
					public void run() {
						System.out.println(revs[revSelect].getMp());
						if(id == 3) {
							if(revs[revSelect].getMp() >= 40) {
								move[revSelect] = id;
								revSelect++;
							}
							else {
								JOptionPane.showMessageDialog(null, "not enough mana");

							}
						}else if(id == 4){
							if(revs[revSelect].useUltimate()){
								JOptionPane.showMessageDialog(null, "Gauge isn't filled");
							}
							else {
								move[revSelect] = id;
								revSelect++;
							}
						}
						else {
							move[revSelect] = id;
							revSelect++;
						}

					}
				});
				select[i] = b;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < 3; i++) {
			revs[i].setCoords(Constants.scaleIntToWidth(100 + (i * 300)), Constants.scaleIntToHeight(400));
			enemies[i].setCoords(Constants.SCREEN_WIDTH - Constants.scaleIntToWidth(300 + (i * 300)),
					Constants.scaleIntToHeight(400));
		}
		setting.addListener(new Runnable() {

			@Override
			public void run() {
				int choice = JOptionPane.showConfirmDialog(null, "Do you like the music?");

				if (choice == 0) {

					sound.loop();

				} else if (choice == 1) {

					sound.stop();

				}
				
			}
			
		});
	}



	public void draw(PApplet window) {
		background.draw(window);
		for(Character rev : revs) {
			rev.setHighlight(false);
		}
		for (int i = 0; i < 5; i++) {
			select[i].draw(window);
		}
		if(revSelect <3) {
			revs[revSelect].setHighlight(true);
		}
		for (Character rev : revs) {
			if (!rev.isDead())
				rev.draw(window);
		}
		for (Character enemy : enemies) {
			if (!enemy.isDead())
				enemy.draw(window);
		}
		setting.draw(window);
		if (target == -1) {
			win = true;
		} else if (enemies[target].isDead()) {
			target--; 
		}
		if (enemyTarget == -1) {
			lose = true;
		} else if (revs[enemyTarget].isDead()) {
			enemyTarget--;
		}
		if (win) {
			victory.draw(window);
			back.draw(window);
			back.addListener(new Runnable() {
				@Override
				public void run() {
					background.setFadeSpeed(40);
					background.setTint(255);
					background.setTargetTint(0);
					background.fadeWhite(true);
					Player.addCurrency(7);
					if(Player.getLevelsUnlocked()==level) {
						Player.passLevel();
					}
					background.addListener(new Runnable() {
						@Override
						public void run() {
							ScreenManager.setCurrentScreenByName("level", window);
						}

					});
				}
			});
		}
		if (lose) {
			defeat.draw(window);
			back.addListener(new Runnable() {
				@Override
				public void run() {
					background.setFadeSpeed(40);
					background.setTint(255);
					background.setTargetTint(0);
					background.fadeWhite(true);
					background.addListener(new Runnable() {

						@Override
						public void run() {
							ScreenManager.setCurrentScreenByName("level", window);
						}

					});
				}
			});
			back.draw(window);
		}
		if (revSelect == enemyTarget+1) {
			for(PButton button : select) {
				button.disableListener();
			}
			for (int i = enemyTarget; i >= 0; i--) {
				
				Character rev = revs[i];
				final int next = i - 1;
				rev.setBlock(move[i]==0);
				rev.addTrigger(new Runnable() {

					@Override
					public void run() {
						if(target >=0) {
							act(revs[next+1], enemies[target], move[next+1], 1);
							if (enemies[target].isDead()) {
								target--;
							}
							System.out.println(target);
							if (next >= 0) {
								revs[next].playActionOnce("attack");
							}
							if(next+1==0) {
								for(PButton button : select) {
									button.enableListener();
								}
							}
						}
					}
				});
				
				
			}
			
			for (int i = target; i >= 0; i--) {

				Character enemy = enemies[i];
				final int next = i - 1;

				enemy.addTrigger(new Runnable() {

					@Override
					public void run() {
						if(enemyTarget >=0) {
							if(enemies[next+1].getGauge()==5) {
								act(enemies[next+1],revs[enemyTarget],4, 1);
							}else {
								
								act(enemies[next+1],revs[enemyTarget],2, 1);
							}
							if (revs[enemyTarget].isDead()) {
								enemyTarget--;
							}
	
							if (next >= 0) {
								enemies[next].playActionOnce("attack");
							}
							else {
								revs[enemyTarget].playActionOnce("attack");
	
							}
					}
					}
				});

			}
			
			revSelect = 0;
			enemies[target].playActionOnce("attack");
		}
		if (window.mousePressed) {
			cursor.draw(window);
		} else {
			cursor.clearTrail();
		}
		
	}
	public void act(Character s, Character target, int move, double mult) {
		switch(move) {
		case 0: //block
			break;
		case 1: //charge
			s.setMp(s.getMp()+30);
			break;
		case 2:	//basic attack	
			target.takeDamage(s.getAttack(), mult);
			s.addGauge(1);

			break;
		case 3: //special attack
			target.takeDamage(s.getAttack(), mult*2);
			s.addGauge(2);
			s.useMana(40);
			break;
		case 4: //ultimate attack
			target.takeDamage(s.getAttack(), mult*5);
			break;
		}
	}

	@Override
	public void dispose() {
		background = null;
		cursor = null;
		button.removeListener();
		button = null;
		win = false;
		lose = false;
		defeat = null;
		victory = null;
	}

}
