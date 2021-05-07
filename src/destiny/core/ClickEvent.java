package destiny.core;

/**
 * This class is for detecting clicking on the window
 * 
 * @author Nathaniel
 * @version 12/5/2020
 */
interface ClickEvent {
	
	/**
	 * detection of whether there was a click of a mouse button
	 * @param Event e
	 * @return if there was a click of a mouse button
	 */
	public boolean click(Event e);
	/**
	 * detection of whether there was a release of a mouse button
	 * @param Event e
	 * @return if there was a release of a mouse button
	 */
	public boolean release(Event e);

}
