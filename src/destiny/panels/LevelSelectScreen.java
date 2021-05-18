package destiny.panels;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import destiny.assets.Constants;
import destiny.assets.RippleCursor;
import destiny.core.FadeImage;
import destiny.core.PButton;
import destiny.core.Screen;
import destiny.core.ScreenManager;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * LevelSelect screen is where the user select which level to play in.
 * @author Jay Paek
 * @version 5/7/2021
 *
 */
public class LevelSelectScreen implements Screen {
	
	private FadeImage background;
	private RippleCursor cursor;
	private PButton  back;
	private FadeImage prev;
	private PButton[] levelButtons;
	private int page;

	
	@Override
	public void setup(PApplet window) {
		background = new FadeImage("res/levelSelectScreen/room.jpg");
		prev = new FadeImage("res/generalAssets/back.png");
		cursor = RippleCursor.createLowPerformanceCursor();
		page = 1;
		try {
			back = new PButton(new Rectangle(Constants.scaleIntToWidth(50), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(250), Constants.scaleIntToWidth(200), Constants.scaleIntToWidth(200)),
					new PImage(ImageIO.read(new File("res/generalAssets/back.png"))), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		background.setCoords(0, 0);
		background.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		prev.resize(Constants.scaleIntToWidth(150), Constants.scaleIntToWidth(150));
		prev.setCoords(Constants.scaleIntToWidth(50), Constants.SCREEN_HEIGHT-Constants.scaleIntToHeight(200));
		levelButtons = new PButton[Constants.TOTAL_LEVELS];		
		for(int i = 0; i < Constants.TOTAL_LEVELS; i++) {
			PButton b;
			int id = i;
			int temp = i + 1;
			System.out.println("String by the way: res/leveSelectScreen/numbers/"+temp+".png");
			try {
				b = new PButton(new Rectangle(Constants.scaleIntToWidth(250+((i%20%5)*300)), Constants.scaleIntToHeight(100+((i%20/5)*200)), Constants.scaleIntToWidth(200), Constants.scaleIntToWidth(170)),new PImage(ImageIO.read(new File("res/levelSelectScreen/numbers/"+temp+".png"))), false);
				b.addListener(new Runnable() {
					@Override
					public void run() {
						background.setFadeSpeed(40);
						background.setTint(255);
						background.setTargetTint(0);
						background.fadeWhite(true);
						System.out.println(id);
						background.addListener(new Runnable() {
							@Override
							public void run() {
								ScreenManager.setCurrentScreenByName("prep", window);
							}
							
						});
					}
				});
				levelButtons[i] = b;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
						ScreenManager.setCurrentScreenByName("main", window);
					}
					
				});
			}
		});
	}

	public void draw(PApplet window) {
		background.draw(window);
		back.draw(window);
		for(int i = 20*(page-1); i < 20*page; i++) {
			levelButtons[i].draw(window);
		}
		if (window.mousePressed) {
			cursor.draw(window);
		} else {
			cursor.clearTrail();
		}
		
	}
	

	@Override
	public void dispose() {
		for(int i = 0; i < levelButtons.length; i++) {
			levelButtons[i].removeListener();
			levelButtons[i] = null;
		}
		background = null;
		cursor = null;
		back.removeListener();
		back = null;
		prev = null;
	}

}
