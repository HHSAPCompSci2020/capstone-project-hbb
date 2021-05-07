package destiny.core;

import java.util.HashMap;

import processing.core.PApplet;

/**
 * 
 * This class manages all the screens in the game and decides which one should be 
 * drawn and when
 * 
 * @author Nathaniel
 * @version 12/5/2020
 */
public class ScreenManager {
	
	private static String currentScreen;
	private static HashMap<String, Screen> allScreens = new HashMap<>();
	private static boolean disposeFlag = false;
	private static String nextScreen;
	
	private ScreenManager() {}
	
	/**
	 * add a Screen to the ScreenManager
	 * @param key
	 * @param s
	 */
	public static void addScreen(String key, Screen s) {
		
		allScreens.put(key, s);
		
	}
	/**
	 * set the screen to the given screen name
	 * @param key screen name
	 * @param s Screen object
	 * @param window
	 */
	public static void setScreen(String key, Screen s, PApplet window) {
		
		addScreen(key, s);
		
		EventHandler.clearScreen();
		if (currentScreen != null)
			allScreens.get(currentScreen).dispose();
		currentScreen = key;
		allScreens.get(currentScreen).setup(window);
		
	}
	/**
	 * draw the screen that is selected
	 * @param window
	 */
	public static void drawCurrentScreen(PApplet window) {
		
		window.clear();
		window.background(255, 255, 255);
		if (currentScreen == null)
			return;
		allScreens.get(currentScreen).draw(window);
		
		if (disposeFlag) {
			disposeScreen(window);
			disposeFlag = false;
		}
	}

	private static void disposeScreen(PApplet window) {
		
		EventHandler.clearScreen();
		if (currentScreen != null)
			allScreens.get(currentScreen).dispose();
		currentScreen = nextScreen;
		allScreens.get(currentScreen).setup(window);
		nextScreen = null;
		
	}
	
	/**
	 * set the screen with the given screen name
	 * @param name The name of the screen
	 * @param window
	 */
	public static void setCurrentScreenByName(String name, PApplet window) {
		
		if (!allScreens.containsKey(name))
			throw new NullPointerException("That screen does not exist");
		else {

			disposeFlag = true;
			nextScreen = name;
			
		}
	}
	
}