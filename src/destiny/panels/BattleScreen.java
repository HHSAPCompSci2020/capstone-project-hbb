package destiny.panels;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import destiny.assets.Constants;
import destiny.assets.RippleCursor;
import destiny.assets.Character;
import destiny.core.FadeImage;
import destiny.core.PButton;
import destiny.core.Screen;
import destiny.core.ScreenManager;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * BattleScreen is where the user can battle with their revolutionaries
 * @author Jay Paek
 * @version 5/7/2021
 *
 */
public class BattleScreen implements Screen {
	
	private FadeImage background;
	private RippleCursor cursor;
	private PButton button, back;
	private int revSelect;
	private int[] move;
	private int target, enemyTarget, battle;
	private Character[] revs, enemies;
	private PButton[] select;
	private boolean win = false, lose = false;;
	private FadeImage victory, defeat;
	
	@Override
	public void setup(PApplet window) {
		background = new FadeImage("res/battlePrepScreen/nathaniel.PNG");
		victory = new FadeImage("res/battleScreen/victory.png");
		defeat = new FadeImage("res/battleScreen/defeat.png");
		cursor = RippleCursor.createLowPerformanceCursor();
		revSelect = 0;
		move = new int[3];
		target = 2;
		enemyTarget = 2;
		revs = new Character[] {new Character(1),new Character(1),new Character(1)};
		enemies = new Character[] {new Character(1),new Character(1),new Character(1)};
		try {
			button = new PButton(new Rectangle( Constants.SCREEN_WIDTH - Constants.scaleIntToWidth(450), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(250), Constants.scaleIntToWidth(400), Constants.scaleIntToWidth(200)),
					new PImage(ImageIO.read(new File("res/generalAssets/play.png"))), false);
			back = new PButton(new Rectangle(Constants.scaleIntToWidth(50), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(250), Constants.scaleIntToWidth(200), Constants.scaleIntToHeight(200)),
					new PImage(ImageIO.read(new File("res/generalAssets/back.png"))), false);
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
		
		select = new PButton[5];

		for(int i = 0; i < 5; i++) {
			final PButton b;
			int id = i;
			try {
				b = new PButton(new Rectangle(Constants.scaleIntToWidth(100+(i*350)), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(300), Constants.scaleIntToWidth(300), Constants.scaleIntToWidth(250)), new PImage(ImageIO.read(new File("res/generalAssets/obama.png"))), false);
				b.addListener(new Runnable() {
					@Override
					public void run() {
						move[revSelect] = id;
						revSelect++;
					}
				});
				select[i] = b;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		button.addListener(new Runnable() {
			@Override
			public void run() {
				background.setFadeSpeed(40);
				background.setTint(255);
				background.setTargetTint(0);
				background.fadeWhite(true);
				background.addListener(new Runnable() {

					@Override
					public void run() {
						ScreenManager.setCurrentScreenByName("battle", window);
					}
					
				});
			}
		});
		
	}

	public void draw(PApplet window) {
		background.draw(window);
		if(target == -1) {
			win = true;
		}
		if(enemyTarget == -1) {
			lose = true;
		}
		for(int i = 0; i < 5; i++) {
			select[i].draw(window);
		}
		for(Character rev : revs) {
			rev.draw(window);
		}
		for(Character enemy : enemies) {
			enemy.draw(window);
		}
		switch(battle) {
		case 1:
			if(!revs[2].isDead()) {
				revs[2].changeAction("attack");
			}
			battle++;
			break;
		case 2:
			if(!enemies[2].isDead()) {
				enemies[2].changeAction("attack");
			}
			battle++;
			break;
		case 3:
			if(!revs[1].isDead()) {
				revs[1].changeAction("attack");
			}
			battle++;
			break;
		case 4:
			if(!enemies[1].isDead()) {
				enemies[1].changeAction("attack");
			}
			battle++;
			break;
		case 5:
			if(!revs[0].isDead()) {
				revs[0].changeAction("attack");
			}
			battle++;
			break;
		case 6:
			if(!enemies[0].isDead()) {
				enemies[0].changeAction("attack");
			}
			battle++;
			break;
		}
		if(enemies[target].isDead()) {
			target--;
		}
		if(revs[enemyTarget].isDead()) {
			enemyTarget--;
		}
		if(win) {
			victory.draw(window);
			back.draw(window);
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
		}
		if(lose) {
			defeat.draw(window);
			back.draw(window);
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
		}
		if(revSelect==3) {
			battle = 1;
			revSelect = 0;
		}
		if (window.mousePressed) {
			cursor.draw(window);
		} else {
			cursor.clearTrail();
		}
		
}
	

	@Override
	public void dispose() {
		background = null;
		cursor = null;
		button.removeListener();
		button = null;
	
	}

}
