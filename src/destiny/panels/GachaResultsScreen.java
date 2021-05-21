package destiny.panels;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import destiny.assets.Constants;
import destiny.assets.Player;
import destiny.assets.RippleCursor;
import destiny.core.FadeImage;
import destiny.assets.Character;
import destiny.core.PButton;
import destiny.core.Screen;
import destiny.core.ScreenManager;
import destiny.net.MongoHandler;
import processing.core.PApplet;
import processing.core.PImage;
import org.bson.Document;


/**
 * MainScreen is the screen where the user can select which game modes to play
 * or determine which aspect of the game they want to explore
 * 
 * @author Jay Paek
 * @version 5/7/2021
 *
 */
public class GachaResultsScreen implements Screen {

	private FadeImage background;
	private RippleCursor cursor;
	private PButton back;
	private Character result;
	@Override
	public void setup(PApplet window) {
		background = new FadeImage("res/mainScreen/big.jpg");
		cursor = RippleCursor.createLowPerformanceCursor();
	
		try {
			back = new PButton(new Rectangle(Constants.scaleIntToWidth(50), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(250), Constants.scaleIntToWidth(200), Constants.scaleIntToWidth(200)),
					new PImage(ImageIO.read(new File("res/generalAssets/back.png"))), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		background.setCoords(0, 0);
		background.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
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
		result = new Character(MongoHandler.rollRandomCharacter(Player.getUserName()).getInteger("_id"));
		result.setCoords(Constants.scaleIntToWidth(800), Constants.scaleIntToHeight(400));
	}

	public void draw(PApplet window) {
		background.draw(window);
		back.draw(window);
		result.draw(window);
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
	}

}
