package destiny.assets;

import destiny.core.PGif;

public class Enemy extends Character {

	public Enemy(int id, boolean isJay) {
		
		super(id);
		
        spriteStatic = new PGif(0, 0, Constants.getEnemyPath(isJay));
        spriteAttack = new PGif(0, 0, Constants.getEnemyPath(isJay));
        spriteFlinch = new PGif(0, 0, Constants.getEnemyPath(isJay));
        spriteMove = new PGif(0, 0, Constants.getEnemyPath(isJay));
		
	}

	
	
}
