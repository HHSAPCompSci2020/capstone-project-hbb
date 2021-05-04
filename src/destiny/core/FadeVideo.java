package destiny.core;
import processing.core.PApplet;
import processing.video.Movie;

/**
 * 
 * This class uses the Movie class from processing and adds fading capability to it
 * 
 * @author Nathaniel
 * @version 12/5/2020
 */
public class FadeVideo extends Fader {
	
	private Movie video;
	
	private int x, y, w = -1, h = -1;
	private float targetVol, volChange;
	private boolean isChanging;
	
	public FadeVideo(PApplet window, String pathname) {
		
		super(0, 255, 0.5f);
		video = new Movie(window, pathname);
		
	}
	
	public FadeVideo(PApplet window, String pathname, float startTint, float targetTint, float fadeSpeed, int xCord, int yCord, int width, int height) {
		
		super(startTint, targetTint, fadeSpeed);
		x = xCord;
		y = yCord;
		video = new Movie(window, pathname);
		w = width;
		h = height;
		
	}
	
	public FadeVideo(PApplet window, String pathname, float startTint, float targetTint, float fadeSpeed, int xCord, int yCord) {
		
		super(startTint, targetTint, fadeSpeed);
		x = xCord;
		y = yCord;
		video = new Movie(window, pathname);
		
	}
	
	@Override
	public void draw(PApplet window) {
		
		window.pushStyle();
		
		super.draw(window);
		
		if (w < 0)
			window.image(video, x, y);
		else
			window.image(video, x, y, w, h);
		
		if (isChanging) {
			
			this.scrollVolume(volChange);
			
			if (this.isFinished()) {
				
				isChanging = false;
				this.video.volume(targetVol);
				
			}
			
		}
		
		window.popStyle();
		
	}
	
	public void setCoords(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public void resize(int w, int h) {
		
		this.w = w;
		this.h = h;
		
	}
	
	public void scrollVolume(float scroll) {
		
		
		float vol = (float)video.playbin.getVolume() + scroll;
		video.volume(vol);
		
	}
	
	public void setVolume(float vol) {
		
		video.volume(vol);
		
	}
	
	public void fadeVolumeTo(float vol) {
		
		isChanging = true;
		volChange = (float)(vol-video.playbin.getVolume())/this.ticksTillTarget();
		targetVol = vol;
		
	}
	
	public float getSecondMark() {
		
		return video.time();
		
	}
	
	public boolean isPlaying() {
		
		return video.isPlaying();
		
	}
	
	public void loop() {
		
		video.loop();
		
	}
	
	public void mute() {
		
		video.volume(0);
		
	}
	
	public void stop() {
		
		video.stop();
		
	}
	
}
