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
	
	private static Timer staminaUpdate = new Timer(60000, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			stamina += 1;
			MongoHandler.updateStamina(stamina, new Date(System.currentTimeMillis()), userName);
			
		}
		
	});
	
	
	private Player() {};
	
	@SuppressWarnings("unchecked")
	public static void loadFromDocument(Document d) {
		
		characters = d.get("characters", new ArrayList<Integer>().getClass());
		stamina = d.getInteger("stamina");
		stamina += (new Date(System.currentTimeMillis()).getTime() - d.getDate("last_update").getTime())/(int)(1000/staminaPerMin);
		currency = d.getInteger("currency");
		userName = d.getString("_id");
		staminaUpdate.start();
		
	}

	/**
	 * @return The stamina
	 */
	public static int getStamina() {
		return stamina;
	}
	
	public static void decreaseStamina(int amt) {
		stamina -= amt;
		MongoHandler.updateStamina(stamina, new Date(System.currentTimeMillis()), userName);
	}

	/**
	 * @return The currency
	 */
	public static int getCurrency() {
		return currency;
	}

	/**
	 * @return The characters
	 */
	public static ArrayList<Integer> getCharacters() {
		return characters;
	}
	
}
