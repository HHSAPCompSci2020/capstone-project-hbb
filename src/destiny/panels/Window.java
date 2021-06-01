package destiny.panels;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import destiny.assets.Constants;
import destiny.core.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import processing.core.PApplet;
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

	private static Player sound;
	private static String currentSong; 
	private static String chillSong;
	private static boolean isPlaying;
	private static boolean play = true;
	
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
		
	}
	
	public static void loopSound(String path) {
		
		if (!path.contains("bgm"))
			chillSong = path;
		
		try {
			sound = new Player(new FileInputStream(path));
			isPlaying = true;
			play = true;
			currentSong = path;
		} catch (FileNotFoundException | JavaLayerException e) {
			e.printStackTrace();
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				try {
					sound.play();
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (sound.isComplete() && path.equals(currentSong) && isPlaying)
					loopSound(path);
				
			}
			
		}).start();;
		
	}
	
	/**
	 * 
	 * Stops the music
	 * 
	 */
	public static void stopSound() {
		
		sound.close();
		isPlaying = false;
		play = false;
		
	}
	
	/**
	 * 
	 * Overrides the screen default for music and plays the original BGM
	 * 
	 * @param override Whether or not to override the BGM
	 */
	public static void setBGMOverride(boolean override) {
		
		play = override;
		
	}

	/**
	 * 
	 * Draws the current screen to the window
	 * 
	 */
	public void draw() {
		
		Screen currentScreen = ScreenManager.getCurrentScreen();
		
		if (currentScreen instanceof OpeningScreen || currentScreen instanceof GachaResultsScreen) {
			
			if (sound != null) {
				sound.close();
				isPlaying = false;
				play = false;
			}
			
		} else {
			
			if (!isPlaying && play) {
				
				loopSound(chillSong);
				
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
