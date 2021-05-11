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
import destiny.core.ScreenFader;
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
	private ScreenFader fader;

	
	@Override
	public void setup(PApplet window) {
		background = new FadeImage("res/levelSelectScreen/room.jpg");
		prev = new FadeImage("res/generalAssets/back.png");
		cursor = RippleCursor.createLowPerformanceCursor();
		page = 1;
		try {
			back = new PButton(new Rectangle(50, Constants.SCREEN_HEIGHT - 250, 200, 200),
					new PImage(ImageIO.read(new File("res/generalAssets/back.png"))), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		background.setCoords(0, 0);
		prev.resize(150, 150);
		prev.setCoords(50, Constants.SCREEN_HEIGHT-200);
		background.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		levelButtons = new PButton[Constants.TOTAL_LEVELS];		
		fader = new ScreenFader(0,255,10);
		for(int i = 0; i < Constants.TOTAL_LEVELS; i++) {
			PButton b;
			try {
				b = new PButton(new Rectangle(250+((i%20%5)*300), 100+((i%20/5)*200), 200, 200),new PImage(ImageIO.read(new File("res/generalAssets/obama.png"))), false);
				b.addListener(new Runnable() {
					@Override
					public void run() {
						fader.fadeToWhite();

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
		fader.fadeIn();		
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
		back.addListener(new Runnable() {
			@Override
			public void run() {
				fader.fadeToWhite();
				background.addListener(new Runnable() {

					@Override
					public void run() {
						ScreenManager.setCurrentScreenByName("main", window);
					}
					
				});
			}
		});
	}
	

	@Override
	public void dispose() {
		background = null;
		cursor = null;
		back.removeListener();
		back = null;
		prev = null;
	}

}
