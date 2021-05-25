package destiny.panels;

import destiny.assets.Constants;
import destiny.core.*;
import processing.core.PApplet;
import processing.sound.SoundFile;
import processing.video.Movie;

/**
 * 
 * This class represents the window that the game is displayed in. It extends from
 * PApplet and forwards all necessary events to the EventHandler and ScreenManager
 * 
 * @author Nathaniel
 * @version 12/5/2020
 */
public class Window extends PApplet {

	public static SoundFile sound;
	
	/**
	 * 
	 * Sets up the window
	 * 
	 */
	public void setup() {
		
		// Instantiate your screens here and add them to the SceenManager
		// You can start with the ScreenManager.setScreen(screenName, screen);
		// Then add screens that you will use later with ScreenManager.addScreen(screenName, screen);
		// You can switch to these screens on events that you want to monitor using -
		// ScreenManager.setCurrentScreenByName(screenName);
		ScreenManager.setScreen("opening", new OpeningScreen(), this);
		ScreenManager.addScreen("home", new HomeScreen());
		ScreenManager.addScreen("main", new MainScreen());
		ScreenManager.addScreen("gacha", new GachaScreen());
		ScreenManager.addScreen("gachaResult", new GachaResultsScreen());
		ScreenManager.addScreen("level", new LevelSelectScreen());
		ScreenManager.addScreen("prep", new BattlePrepScreen());
		ScreenManager.addScreen("battle", new BattleScreen());
		ScreenManager.addScreen("gallery", new GalleryScreen());
		sound = new SoundFile(this, Constants.getSoundPath("rondo.mp3"));
		sound.loop();
		
	}

	/**
	 * 
	 * Draws the current screen to the window
	 * 
	 */
	public void draw() {
		
		Screen currentScreen = ScreenManager.getCurrentScreen();
		
		if (currentScreen instanceof BattleScreen || currentScreen instanceof OpeningScreen || currentScreen instanceof GachaResultsScreen) {
			
			if (sound.isPlaying()) {
				sound.stop();
				sound.jump(0);
			}
			
		} else {
			
			if (!sound.isPlaying()) {
				
				sound.play();
				
			}
			
		}
		
		ScreenManager.drawCurrentScreen(this);
	}
	
	/**
	 * 
	 * Reads the next movie frame
	 * 
	 * @param m The movie that should have the next frame read
	 */
	public void movieEvent(Movie m) {
		m.read();
	}
	
	/**
	 * 
	 * Triggers all clickables
	 * 
	 */
	public void mousePressed() {
		EventHandler.notifyClickables(this);
	}
	
	/**
	 * 
	 * Triggers all things that should be notified on mouse release
	 * 
	 */
	public void mouseReleased() {
		EventHandler.notifyRelease(this);
	}
	
	/**
	 * 
	 * Triggers all draggables
	 * 
	 */
	public void mouseDragged() {
		EventHandler.notifyDraggables(this);
	}
	
}
