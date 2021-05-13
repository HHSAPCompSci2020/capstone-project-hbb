package destiny.assets;

import java.awt.Toolkit;

/**
 * 
 * A class to hold some values that are constant throughout runtime
 * 
 * @author Nathaniel
 * @version 12/5/2020
 */
public class Constants {
	
	public static final int NUM_OF_CHARACTERS = 20;
	public static final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 65;
	public static final int TOTAL_LEVELS = 20;
	public static final int TOTAL_CHARACTERS = 20;
	
	
	private Constants() {}

	/**
	 * 
	 * Gives the string corresponding to the integer action
	 * 
	 * @param act The integer form of the action you are looking for
	 * @return The string form of the action for the integer given. Will return null if not an actual integer action
	 */
	public static String getActionForInt(int act) {
		
		switch(act) {
		
		case 0:
			return "attack";
		case 1:
			return "flinch";
		case 2:
			return "move";
		case 3:
			return "static";
		default:
			return null;
			
		}
		
	}
	
	public static int getIntForAction(String action) {
		
		switch(action) {
		
		case "attack":
			return 0;
		case "flinch":
			return 1;
		case "move":
			return 2;
		case "static":
			return 3;
		default:
			return -1;
			
		}
		
	}
	
	
	public static String getCharacterPath(int id, String action) {
		
		return "res/characters/"+id+"/asset"+id+"/-"+action+".gif";
		
	}
	
	public static int scaleIntToWidth(double num) {
		
		return (int)(num/1920*SCREEN_WIDTH);
		
	}
	
	public static int scaleIntToHeight(double num) {
		
		return (int)(num/1015*SCREEN_HEIGHT);
		
	}
	
}
