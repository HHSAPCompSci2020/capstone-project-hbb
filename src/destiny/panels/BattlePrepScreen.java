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
 * BattlePrepScreen is where the user can select which revolutionaries they want to use for battle
 * @author Jay Paek
 * @version 5/7/2021
 *
 */
public class BattlePrepScreen implements Screen {
	
	private FadeImage background;
	private RippleCursor cursor;
	private PButton button, back;
	private boolean first, second, third, selectFirst, selectSecond, selectThird;
	private int[] revs;
	private PButton[] select;
	private PButton[] selection;
	private int page;

	
	@Override
	public void setup(PApplet window) {
		background = new FadeImage("res/battlePrepScreen/nathaniel.PNG");
		cursor = RippleCursor.createLowPerformanceCursor();
		first = false;
		second = false;
		third = false;
		selectFirst = true;
		selectSecond = false;
		selectThird = false;
		page = 1;
		revs = new int[] {0,0,0};
		try {
			button = new PButton(new Rectangle(Constants.SCREEN_WIDTH - Constants.scaleIntToWidth(450), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(250), Constants.scaleIntToWidth(400), Constants.scaleIntToWidth(200)),
					new PImage(ImageIO.read(new File("res/generalAssets/play.png"))), false);
			back = new PButton(new Rectangle(Constants.scaleIntToWidth(50), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(250), Constants.scaleIntToWidth(200), Constants.scaleIntToWidth(200)),
					new PImage(ImageIO.read(new File("res/generalAssets/back.png"))), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		background.setCoords(0, 0);
		background.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		
		select = new PButton[Constants.TOTAL_CHARACTERS];
		selection = new PButton[3]; 


		for(int i = 0; i < Constants.TOTAL_LEVELS; i++) {
			PButton b;
			int id = i+1;
			try {
				b = new PButton(new Rectangle(Constants.scaleIntToWidth(800+(i%20%5*200)), Constants.scaleIntToHeight(100+(i%20/5*200)), Constants.scaleIntToWidth(200), Constants.scaleIntToWidth(200)), new PImage(ImageIO.read(new File("res/generalAssets/obama.png"))), false);
				b.addListener(new Runnable() {
					@Override
					public void run() {
						if(selectFirst) {
							revs[0] = id;
							selectFirst = false;
							selectSecond = true;
							first = true;
						} else if(selectSecond) {
							revs[1] = id;
							selectSecond = false;
							selectThird = true;
							second = true;
						}
						else if(selectThird) {
							revs[2] = id;
							selectThird = false;
							third = true;
						}
						System.out.println(""+revs[0]+revs[1]+revs[2]);
					}
				});
				select[i] = b;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		for(int i = 0; i < 3; i++) {
			PButton b;
			try {
				b = new PButton(new Rectangle(Constants.scaleIntToWidth(100), Constants.scaleIntToHeight(100+(200*i)), Constants.scaleIntToWidth(200), Constants.scaleIntToWidth(200)),new PImage(ImageIO.read(new File("res/generalAssets/obama.png"))), false);
				int sel = i;
				b.addListener(new Runnable() {
					@Override
					public void run() {
						if(sel == 0) {
							selectFirst = true;
							selectSecond = false;
							selectThird = false;
						} else if(sel == 1) {
							selectFirst = false;
							selectSecond = true;
							selectThird = false;
						}
						else if(sel == 2	) {
							selectFirst = false;
							selectSecond = false;
							selectThird = true;
						}
					}
				});
				selection[i] = b;
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

	public void draw(PApplet window) {
		background.draw(window);
		
		for(int i = 20*(page-1); i < 20*page; i++) {
			select[i].draw(window);
		}
		for(int i =0; i < 3; i++) {
			selection[i].draw(window);
		}
		if(first&&second&&third)
			button.draw(window);
		
		if (window.mousePressed) {
			cursor.draw(window);
		} else {
			cursor.clearTrail();
		}
		
}
	

	@Override
	public void dispose() {
		for (int i = 0; i < select.length; i ++) {
			
			select[i].removeListener();
			select[i] = null;
			
		}
		for (int i = 0; i < selection.length; i ++) {
			
			selection[i].removeListener();
			selection[i] = null;
			
		}
		
		background = null;
		cursor = null;
		button.removeListener();
		button = null;
	}

}
