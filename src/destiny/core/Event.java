package destiny.core;

/**
 * 
 * This class is for all events
 * 
 * @author Nathaniel
 * @version 12/5/2020
 */
class Event {
	
	private final int mouseX, mouseY;
	
	/**
	 * create Event bawsedon mouse x and y
	 * @param mX
	 * @param mY
	 */
	public Event(int mX, int mY) {
		
		this.mouseX = mX;
		this.mouseY = mY;
		
	}
	
	public int getMouseX() {
		
		return mouseX;
		
	}
	
	public int getMouseY() {
		
		return mouseY;
		
	}
	
}
