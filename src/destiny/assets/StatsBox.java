package destiny.assets;

import org.bson.Document;

import processing.core.PApplet;

public class StatsBox {
	
    private final int totalHealth, totalMp, attack, defense;
    private int[] stats;
    private String[] names;
    private int width, height, x, y;

	public StatsBox(Document character, int x, int y, int width, int height) {
		
		this.totalHealth = character.getInteger("health");
		this.totalMp = character.getInteger("mp");
		this.attack = character.getInteger("attack");
		this.defense = character.getInteger("defense");
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		stats = new int[]{totalHealth, totalMp, attack, defense};
		names = new String[]{"Total Health: ", "Total MP: ", "Attack: ", "Defense: "};
		
	}
	
	public void draw(PApplet window) {
		
		window.pushStyle();
		
		window.noFill();
		window.textSize((width+height)/20);
		
		window.rect(x, y, width, height);
		
		for (int i = 0; i < stats.length; i ++)
			window.text(names[i] + stats[i], x + 10, y + height/5*i);
		
		window.popStyle();
		
	}
	
}
