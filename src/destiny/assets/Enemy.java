package destiny.assets;

import destiny.core.PGif;

public class Enemy extends Character {

	private boolean isJay;
	
	public Enemy(int id, boolean isJay) {
		
		super(id);
		
		this.isJay = isJay;
        spriteStatic = new PGif(0, 0, Constants.getEnemyPath(isJay));
        spriteAttack = new PGif(0, 0, Constants.getEnemyPath(isJay));
        spriteFlinch = new PGif(0, 0, Constants.getEnemyPath(isJay));
        spriteMove = new PGif(0, 0, Constants.getEnemyPath(isJay));
		
	}

	public boolean isJay() {
		
		return isJay;
		
	}
	
}
