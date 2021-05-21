package destiny.assets;

import org.bson.Document;

public class StatsBox {
	
	private int health, attack, defense, mp, gauge;
    private final int totalHealth, totalMp;

	public StatsBox(Document character) {
		
		this.totalHealth = character.getInteger("health");
		this.totalMp = character.getInteger("mp");
		this.health = totalHealth;
		this.mp = totalMp;
		this.attack = character.getInteger("attack");
		this.defense = character.getInteger("defense");
		this.gauge = character.getInteger("gauge");
		
	}
	
}
