package destiny.assets;

import destiny.core.PGif;

/**
 * 
 * A class that represents an Enemy
 * 
 * @author Nathaniel
 * @version 5/23/21
 *
 */
public class Enemy extends Character {

	private boolean isJay;
	
	/**
	 * 
	 * Creates an enemy from the given parameters
	 * 
	 * @param id The id of the character that the enemy should take stats from
	 * @param isJay Whether or not the enemy is Jay, if it is not it will be Kumar
	 */
	public Enemy(int id, boolean isJay) {
		
		super(id);
		
		this.isJay = isJay;
        spriteStatic = new PGif(0, 0, Constants.getEnemyPath(isJay,"static"));
        spriteAttack = new PGif(0, 0, Constants.getEnemyPath(isJay,"attack"));
        spriteFlinch = new PGif(0, 0, Constants.getEnemyPath(isJay,"flinch"));
        spriteMove = new PGif(0, 0, Constants.getEnemyPath(isJay,"move"));
		
        sprite = spriteStatic;
        
        if (!isJay)
        	this.buff(1.5);
        
        sprites = new PGif[]{spriteAttack, spriteFlinch, spriteMove, spriteStatic};
        
        for (PGif i : sprites)
        	i.flipHorizontal();
        
	}

	/**
	 * 
	 * Whether or not this enemy is Jay
	 * 
	 * @return If this enemy is Jay or not
	 */
	public boolean isJay() {
		
		return isJay;
		
	}
	
}
