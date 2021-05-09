package destiny.core;

public class ScreenFader extends Fader {

	public ScreenFader() {
		
		this(0, 0, 0.5f);
		
	}
	
	public ScreenFader(float startTint, float targetTint, float fadeSpeed) {
		
		super(startTint, targetTint, fadeSpeed);
		
		
	}
	
	public void fadeToBlack() {
		
		this.setTargetTint(255);
		
	}
	
	public void fadeToWhite() {
		
		this.fadeWhite(true);
		this.setTargetTint(255);
		
	}
	
	public void fadeIn() {
		
		this.setTargetTint(0);
		
	}

}
