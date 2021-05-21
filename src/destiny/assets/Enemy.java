package destiny.assets;

import destiny.core.PGif;

public class Enemy extends Character {

	private boolean isJay;
	
	public Enemy(int id, boolean isJay) {
		
		super(id);
		
		this.isJay = isJay;
        spriteStatic = new PGif(0, 0, Constants.getEnemyPath(isJay,"static"));
        spriteAttack = new PGif(0, 0, Constants.getEnemyPath(isJay,"attack"));
        spriteFlinch = new PGif(0, 0, Constants.getEnemyPath(isJay,"flinch"));
        spriteMove = new PGif(0, 0, Constants.getEnemyPath(isJay,"move"));
		
        sprite = spriteStatic;
        
        sprites = new PGif[]{spriteAttack, spriteFlinch, spriteMove, spriteStatic};
        
	}

	public boolean isJay() {
		
		return isJay;
		
	}
	
}
