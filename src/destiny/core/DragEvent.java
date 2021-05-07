package destiny.core;

/**
 * 
 * This class is for detecting dragging on the window
 * 
 * @author Nathaniel
 * @version 12/5/2020
 */
interface DragEvent extends ClickEvent {

	/**
	 * detection of whether there was a drag of mouse on the window
	 * @param Event e
	 * @return if there was a click of a mouse button
	 */
	public boolean drag(Event e);
	
}
