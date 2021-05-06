package destiny.panels;

import java.awt.Rectangle;

import destiny.assets.Constants;
import destiny.assets.RippleCursor;
import destiny.core.FadeImage;
import destiny.core.PButton;
import destiny.core.Screen;
import destiny.core.ScreenManager;
import processing.core.PApplet;
import processing.core.PImage;

public class LevelSelectScreen implements Screen {
	
	private FadeImage background;
	private RippleCursor cursor;
	private PButton  back;
	private FadeImage prev;
	private PButton[] levelButtons;
	private FadeImage[] levels;
	private int page;
	@Override
	public void setup(PApplet window) {
		background = new FadeImage("res/levelSelectScreen/210322.jpg");
		prev = new FadeImage("res/generalAssets/back.png");
		cursor = RippleCursor.createLowPerformanceCursor();
		page = 1;
		back = new PButton(new Rectangle(0, Constants.SCREEN_HEIGHT-200, 200, Constants.SCREEN_HEIGHT), false);
		background.setCoords(0, 0);
		prev.resize(150, 150);
		prev.setCoords(50, Constants.SCREEN_HEIGHT-200);
		background.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		levelButtons = new PButton[Constants.TOTAL_LEVELS];
		levels = new FadeImage[Constants.TOTAL_LEVELS];
		

		for(int i = 0; i < Constants.TOTAL_LEVELS; i++) {
			PButton b = new PButton(new Rectangle(250+((i%20%5)*300), 100+((i%20/5)*200), 250+((i%20%5)*300)+100, 100+((i%20/5)*200)+ 100), false);
			int level = i+1;
			b.addListener(new Runnable() {
				@Override
				public void run() {
					for(FadeImage pic : levels) {
						pic.setFadeSpeed(40);
						pic.setTint(255);
						pic.setTargetTint(0);
						pic.fadeWhite(true);
					}
					background.setFadeSpeed(40);
					background.setTint(255);
					background.setTargetTint(0);
					background.fadeWhite(true);

					prev.setFadeSpeed(40);
					prev.setTint(255);
					prev.setTargetTint(0);
					prev.fadeWhite(true);
					System.out.println(level);
					background.addListener(new Runnable() {
						@Override
						public void run() {
							ScreenManager.setCurrentScreenByName("prep", window);
						}
						
					});
				}
			});
			levelButtons[i] = b;
			FadeImage img = new FadeImage("res/generalAssets/obama.png");
			img.setCoords(250+((i%20%5)*300), 100+((i%20/5)*200));
			img.resize(200, 200);
			levels[i] = img;
		}
	}

	public void draw(PApplet window) {
		background.draw(window);
		
		for(int i = 20*(page-1); i < 20*page; i++) {
			levels[i].draw(window);
		}
		if (window.mousePressed) {
			cursor.draw(window);
		} else {
			cursor.clearTrail();
		}
		back.addListener(new Runnable() {
			@Override
			public void run() {
				for(FadeImage pic : levels) {
					pic.setFadeSpeed(40);
					pic.setTint(255);
					pic.setTargetTint(0);
					pic.fadeWhite(true);
				}
				background.setFadeSpeed(40);
				background.setTint(255);
				background.setTargetTint(0);
				background.fadeWhite(true);
				prev.setFadeSpeed(40);
				prev.setTint(255);
				prev.setTargetTint(0);
				prev.fadeWhite(true);
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
