package destiny.assets;

import org.bson.Document;

import destiny.core.PGif;
import destiny.net.MongoHandler;
import processing.core.PApplet;

/**
 * 
 * A class that represents a revolutionary. It holds their stats, image, and other info
 * 
 * @author Nathaniel
 * @version 5/13/2021
 *
 */
public class Character {
	
    private int health, attack, defense, mp, gauge;
    private PGif sprite, spriteStatic, spriteAttack, spriteFlinch, spriteMove;
    private PGif[] sprites;
      
    /**
     * 
     * Creates the revolutionary given their id number
     * 
     * @param id The id number of the revolutionary
     */
    public Character(int id) {
        
        Document statDoc = MongoHandler.getStatDoc(id);
        
        health = statDoc.getInteger("health");
        attack = statDoc.getInteger("attack");
        defense = statDoc.getInteger("defense");
        mp = statDoc.getInteger("mp");
        gauge = statDoc.getInteger("gauge");
        spriteStatic = new PGif(0, 0, Constants.getCharacterPath(id, "static"));
        spriteAttack = new PGif(0, 0, Constants.getCharacterPath(id, "attack"));
        spriteFlinch = new PGif(0, 0, Constants.getCharacterPath(id, "flinch"));
        spriteMove = new PGif(0, 0, Constants.getCharacterPath(id, "move"));
        sprite = spriteStatic;
        sprites = new PGif[]{spriteAttack, spriteFlinch, spriteMove, spriteStatic};
        
    }
	
    /**
     * 
     * Draws the revolutionary to the screen
     * 
     * @param window TApplet will have the revolutionary drawn to it
     */
	public void draw(PApplet window) {
		
		sprite.draw(window);
		
	}
	
	/**
	 * 
	 * Moves the revolutionary to the given coords
	 * 
	 * @param x The x coordinate of the top left
	 * @param y The y coordinate of the top left
	 */
	public void setCoords(int x, int y) {
		
		for (int i = 0; i < sprites.length; i ++) {
			
			sprites[i].setCoords(x, y);
			
		}
		
	}
	 
	/**
	 * 
	 * Changes the revolutionary's gif top a dif action
	 * 
	 * @param action The action that the gif should change to
	 */
	public void changeAction(String action) {
		
		sprite = sprites[Constants.getIntForAction(action)];
		
	}
	
	public void takeDamage(int attack, double multi) {
		
		health -= (int)(multi*attack/defense);
		
		final PGif temp = sprite;
		
		sprite = spriteFlinch;
		sprite.playOnce();
		sprite.addListener(new Runnable() {

			@Override
			public void run() {
				
				sprite.startLooping();
				sprite.restart();
				sprite = temp;
				
			}
			
		});
		
	}
	
	public void playActionOnce(String action) {
		
		final PGif temp = sprite;
		
		sprite = sprites[Constants.getIntForAction(action)];
		sprite.playOnce();
		
		sprite.addListener(new Runnable() {

			@Override
			public void run() {
				
				sprite.startLooping();
				sprite.restart();
				sprite = temp;
				
			}
			
		});
		
	}
	
	public void playActionOnce(String action, Runnable code) {
		
		final PGif temp = sprite;
		
		sprite = sprites[Constants.getIntForAction(action)];
		sprite.playOnce();
		
		sprite.addListener(new Runnable() {

			@Override
			public void run() {
				
				sprite.startLooping();
				sprite.restart();
				sprite = temp;
				code.run();
				
			}
			
		});
		
	}

	/**
	 * @return The health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param health The health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return The attack
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * @param attack The attack to set
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * @return The defense
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * @param defense The defense to set
	 */
	public void setDefense(int defense) {
		this.defense = defense;
	}

	/**
	 * @return The mp
	 */
	public int getMp() {
		return mp;
	}

	/**
	 * @param mp The mp to set
	 */
	public void setMp(int mp) {
		this.mp = mp;
	}

	/**
	 * @return The gauge
	 */
	public int getGauge() {
		return gauge;
	}

	/**
	 * @param guage The gauge to set
	 */
	public void setGauge(int gauge) {
		this.gauge = gauge;
	}
	
	/**
	 * @return if the hp < 0 or not
	 */
	public boolean isDead() {
		return health <= 0;
	}
}
