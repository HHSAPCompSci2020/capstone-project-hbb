package destiny.assets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Timer;

import org.bson.Document;

import destiny.net.MongoHandler;

public class Player {

	private static int stamina;
	private static int currency;
	private static ArrayList<Integer> characters;
	private static double staminaPerMin = 1;
	private static String userName;
	private static int levelsUnlocked;
	private static int thisLevel;
	private static Timer staminaUpdate = new Timer(60000, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			stamina += 1;
			if (stamina > 100)
				stamina = 100;
			else
				MongoHandler.updateStamina(userName, stamina, new Date(System.currentTimeMillis()));
			
			loadFromDocument(MongoHandler.getUserDoc(userName));
			
		}
		
	});
	
	
	private Player() {};
	
	@SuppressWarnings("unchecked")
	public static void loadFromDocument(Document d) {
		
		characters = d.get("characters", new ArrayList<Integer>().getClass());
		stamina = d.getInteger("stamina");
		stamina += (new Date(System.currentTimeMillis()).getTime() - d.getDate("last_update").getTime())/(int)(1000/staminaPerMin);
		if (stamina > 100)
			stamina = 100;
		currency = d.getInteger("currency");
		userName = d.getString("_id");
		staminaUpdate.start();
		levelsUnlocked = d.getInteger("levels_unlocked");
		
	}

	/**
	 * @return The stamina
	 */
	public static int getStamina() {
		return stamina;
	}
	/**
	 * @return The level
	 */
	public static int getLevel() {
		return thisLevel;
	}
	/**
	 * @param the level to set to
	 */
	public static void setLevel(int level) {
		thisLevel = level;
	}
	
	public static void decreaseStamina(int amt) {
		stamina -= amt;
		MongoHandler.updateStamina(userName, stamina, new Date(System.currentTimeMillis()));
	}

	/**
	 * @return The levelsUnlocked
	 */
	public static int getLevelsUnlocked() {
		return levelsUnlocked;
	}

	/**
	 * @return The currency
	 */
	public static int getCurrency() {
		return currency;
	}
	
	public static void useCurrency(int amt) {
		currency -= amt;
		MongoHandler.updateCurrency(userName, currency);
	}
	
	public static void addCurrency(int amt) {
		currency += amt;
		MongoHandler.updateCurrency(userName, currency);
	}

	/**
	 * @return The characters
	 */
	public static ArrayList<Integer> getCharacters() {
		return characters;
	}
	
	public static void passLevel() {
		
		if (levelsUnlocked < 20) {
			levelsUnlocked++;
			MongoHandler.progressLevel(userName);
		}
		
	}
	
	/**
	 * @return The username
	 */
	public static String getUserName() {
		return userName;
	}
}
