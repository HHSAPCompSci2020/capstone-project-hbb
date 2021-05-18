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
import destiny.assets.Character;

/**
 * BattlePrepScreen is where the user can select which revolutionaries they want to use for battle
 * @author Jay Paek
 * @version 5/7/2021
 *
 */
public class GalleryScreen implements Screen {
	
	private FadeImage background;
	private RippleCursor cursor;
	private PButton back;
	private PButton[] select;
	private int page;
	private Character rev;

	
	@Override
	public void setup(PApplet window) {
		background = new FadeImage("res/battlePrepScreen/nathaniel.PNG");
		cursor = RippleCursor.createLowPerformanceCursor();
		page = 1;
		rev = new Character(1);
		try {
			back = new PButton(new Rectangle(Constants.scaleIntToWidth(50), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(250), Constants.scaleIntToWidth(200), Constants.scaleIntToHeight(200)),
					new PImage(ImageIO.read(new File("res/generalAssets/back.png"))), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		background.setCoords(0, 0);
		background.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		
		select = new PButton[Constants.TOTAL_CHARACTERS];


		for(int i = 0; i < Constants.NUM_OF_CHARACTERS; i++) {
			PButton b;
			int id = i+1;
			try {
				b = new PButton(new Rectangle(Constants.scaleIntToWidth(800+(i%20%5*200)), Constants.scaleIntToHeight(100+(i%20/5*200)), Constants.scaleIntToWidth(200), Constants.scaleIntToWidth(200)), new PImage(ImageIO.read(new File("res/generalAssets/obama.png"))), false);
				b.addListener(new Runnable() {
					@Override
					public void run() {
						rev = new Character(id);
					}
				});
				select[i] = b;
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
		
		background = null;
		cursor = null;

	}

}
