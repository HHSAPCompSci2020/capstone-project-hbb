package destiny.assets;

import org.bson.Document;

import destiny.core.PGif;
import destiny.net.MongoHandler;
import processing.core.PApplet;

public class Character {
	
    private int health, attack, defense, mp, guage;
    private PGif sprite, spriteStatic, spriteAttack, spriteFlinch, spriteMove;
    private PGif[] sprites;
        
    public Character(int id) {
        
        Document statDoc = MongoHandler.getStatDoc(id);
        
        health = statDoc.getInteger("health");
        attack = statDoc.getInteger("attack");
        defense = statDoc.getInteger("defense");
        mp = statDoc.getInteger("mp");
        guage = statDoc.getInteger("guage");
        spriteStatic = new PGif(0, 0, Constants.getCharacterPath(id, "static"));
        spriteAttack = new PGif(0, 0, Constants.getCharacterPath(id, "attack"));
        spriteFlinch = new PGif(0, 0, Constants.getCharacterPath(id, "flinch"));
        spriteMove = new PGif(0, 0, Constants.getCharacterPath(id, "move"));
        sprite = spriteStatic;
        sprites = new PGif[]{spriteAttack, spriteFlinch, spriteMove, spriteStatic};
        
    }
	
	public void draw(PApplet window) {
		
		sprite.draw(window);
		
	}
	
	public void setCoords(int x, int y) {
		
		for (int i = 0; i < sprites.length; i ++) {
			
			sprites[i].setCoords(x, y);
			
		}
		
	}
	
	public void changeAction(String action) {
		
		sprite = sprites[Constants.getIntForAction(action)];
		
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
	 * @return The guage
	 */
	public int getGuage() {
		return guage;
	}

	/**
	 * @param guage The guage to set
	 */
	public void setGuage(int guage) {
		this.guage = guage;
	}
	
}
