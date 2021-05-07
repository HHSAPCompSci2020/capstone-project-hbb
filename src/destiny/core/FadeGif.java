package destiny.core;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * This class uses the existing PGif class and adds fading functionality to it
 * 
 * @author Nathaniel
 * @version 12/5/2020
 */
public class FadeGif extends Fader {
	
	private PGif myGif;
	
	/**
	 * create a FadeGif by retrieving a GIF from a path
	 * @param pathname
	 */
	public FadeGif(String pathname) {
		
		super(0, 255, 0.2f);
		myGif = new PGif(0, 0, pathname);
		
	}
	
	/**
	 * create a FadeGif by retrieving a GIF from a path and desired top left corner
	 * @param pathname
	 */
	public FadeGif(String pathname, int x, int y) {
		
		super(0, 255, 0.2f);
		myGif = new PGif(x, y, pathname);
		
	}
	
	/**
	 * create a FadeGif by retrieving a GIF from a path and fade speed and opacities 
	 * @param pathname
	 */
	public FadeGif(String pathname, float startTint, float targetTint, float fadeSpeed) {
		
		super(startTint, targetTint, fadeSpeed);
		myGif = new PGif(0, 0, pathname);
		
	}
	
	/**
	 * create a FadeGif by retrieving a GIF from a path and desired dimension of the GIF 
	 * @param pathname
	 */
	public FadeGif(String pathname, int x, int y, int width, int height) {
		
		super(0, 255, 0.2f);
		myGif = new PGif(x, y, pathname);
		myGif.resize(width, height);
		
	}
	
	/**
	 * create a FadeGif by retrieving a GIF from a path and desired dimension of the GIF and fade speed and opacities 
	 * @param pathname
	 */
	public FadeGif(String pathname, float startTint, float targetTint, float fadeSpeed, int xCord, int yCord, int width, int height) {
		
		super(startTint, targetTint, fadeSpeed);
		myGif = new PGif(xCord, yCord, pathname);
		myGif.resize(width, height);
		
	}
	
	/**
	 * create a FadeGif by retrieving a GIF from a path and fade speed and opacities and top left corner coordinates
	 * @param pathname
	 */
	public FadeGif(String pathname, float startTint, float targetTint, float fadeSpeed, int xCord, int yCord) {
		
		super(startTint, targetTint, fadeSpeed);
		myGif = new PGif(xCord, yCord, pathname);
		
	}
	
	/**
	 * create a FadeGif from PGif object
	 * @param gif pathname
	 */
	public FadeGif(PGif gif) {
		
		super(0, 255, 0.2f);
		myGif = gif.copy();
		
	}
	
	
	public FadeGif(PGif gif, float startTint, float targetTint, float fadeSpeed) {
		
		super(startTint, targetTint, fadeSpeed);
		myGif = gif.copy();
		
	}
	
	@Override
	public void draw(PApplet window) {
		
		window.pushStyle();
		
		super.draw(window);
		
		myGif.draw(window);
		
		window.popStyle();
		
	}
	
	public PImage getCurrentImage() {
		
		return myGif.getCurrentImage();
		
	}
	
	/**
	 * set top left coordinate of the gif 
	 * @param xCord
	 * @param yCord
	 */
	public void setCoords(int xCord, int yCord) {
		
		myGif.setCoords(xCord, yCord);
		
	}
	
	/**
	 * move the gif by given x and y 
	 * @param xShift
	 * @param yShift
	 */
	public void translate(int xShift, int yShift) {
		
		myGif.translate(xShift, yShift);
		
	}
	
	public int getWidth() {
		
		return myGif.getWidth();
		
	}
	
	public int getHeight() {
		
		return myGif.getHeight();
		
	}
	
	/**
	 * return true when the Gif is Finished
	 */
	public boolean isFinished() {
		
		return  myGif.isFinished();
		
	}
	
	/**
	 * resize the gif to the desired shape
	 * @param w
	 * @param h
	 */
	public void resize(int w, int h) {
		
		myGif.resize(w, h);
		
	}
	
	/**
	 * scale the width by a given factor
	 * @param width
	 */
	public void scaleByWidth(int width) {
		
		myGif.scaleByWidth(width);
		
	}
	/**
	 * scale the gif by a given factor
	 * @param s
	 */
	public void scale(float s) {
		
		myGif.scale(s);
		
	}
	
	/**
	 * stop looping gif
	 */
	public void stopLooping() {
		
		myGif.stopLooping();
		
	}
	/**
	 * stop looping the gif
	 */
	public void startLooping() {
		
		myGif.startLooping();
		
	}
	
	/**
	 * only play the gif once
	 */
	public void playOnce() {
		
		myGif.playOnce();
		
	}
	/**
	 * restart the gif and play again
	 */
	public void restart() {
		
		myGif.restart();
		
	}
	/**
	 * overrise the delay between the gifs
	 * @param delay
	 */
	public void overrideDelay(double delay) {
		
		myGif.overrideDelay(delay);
		
	}
	
	/**
	 * add delay between gif playthroughs
	 */
	public void defaultDelay() {
		
		myGif.defaultDelay();
		
	}
	
	/**
	 * get a copy of the FadeGif
	 * @return
	 */
	public PGif copy() {
		
		return myGif.copy();
		
	}

}
