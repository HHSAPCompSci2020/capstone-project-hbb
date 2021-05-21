package destiny.assets;

import destiny.core.PGif;

public class Enemy extends Character {

	public Enemy(int id, boolean isJay) {
		
		super(id);
		
        spriteStatic = new PGif(0, 0, Constants.getEnemyPath(isJay,"static"));
        spriteAttack = new PGif(0, 0, Constants.getEnemyPath(isJay,"attack"));
        spriteFlinch = new PGif(0, 0, Constants.getEnemyPath(isJay,"flinch"));
        spriteMove = new PGif(0, 0, Constants.getEnemyPath(isJay,"move"));
		
	}

	
	
}
