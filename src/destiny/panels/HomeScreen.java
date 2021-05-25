package destiny.panels;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import destiny.assets.Constants;
import destiny.assets.RippleCursor;
import destiny.core.FadeGif;
import destiny.core.FadeImage;
import destiny.core.FadeVideo;
import destiny.core.PButton;
import destiny.core.Screen;
import destiny.core.ScreenManager;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * HomeScreen is the screen right after the opening screen
 * @author Jay Paek
 * @version 5/7/2021
 *
 */
public class HomeScreen implements Screen {

	private FadeVideo background;
	private RippleCursor cursor;
	private PButton button;
	
	@Override
	public void setup(PApplet window) {
		background = new FadeVideo(window, "res/generalAssets/impact.mp4");
		background.setFadeSpeed(50);
		cursor = RippleCursor.createLowPerformanceCursor();
		try {
			button = new PButton(new Rectangle(Constants.SCREEN_WIDTH - Constants.scaleIntToWidth(500), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(300), Constants.scaleIntToWidth(400), Constants.scaleIntToWidth(200)),new PImage(ImageIO.read(new File("res/generalAssets/play.png"))), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		background.setCoords(0, 0);
		background.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		background.loop();
	}

	@Override
	public void draw(PApplet window) {
			background.draw(window);
			button.draw(window);
			window.pushStyle();
			window.stroke(255, 255, 255);
			window.textSize(Constants.scaleIntToWidth(70));
			window.text("Welcome To Destiny Roll!", Constants.SCREEN_WIDTH/2 - Constants.scaleIntToWidth(400), Constants.SCREEN_HEIGHT/3);
			window.textSize(Constants.scaleIntToWidth(30));
			window.text("By: Nathaniel Thomas and Jay Paek", Constants.SCREEN_WIDTH/2 - Constants.scaleIntToWidth(210), Constants.SCREEN_HEIGHT/3 + Constants.scaleIntToHeight(100));
			window.popStyle();
			if (window.mousePressed) {
				cursor.draw(window);
			} else {
				cursor.clearTrail();
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
		button.removeListener();
		button = null;
	}

}
