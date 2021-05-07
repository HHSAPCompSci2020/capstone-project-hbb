package destiny.core;


import java.util.Deque;
import java.util.LinkedList;

import processing.core.PApplet;
/**
 * This class handles all events that are triggered in the PApplet and filters them
 * to any listeners on those events
 * 
 * @author Nathaniel
 * @version 12/5/2020
 */
public class EventHandler {
	
	private static Deque<ClickEvent> clickables = new LinkedList<ClickEvent>();
	private static Deque<DragEvent> draggables = new LinkedList<DragEvent>();
	
	@SuppressWarnings("rawtypes")
	private static Deque[] allComps = {clickables, draggables};
	
	/**
	 * removes a click event from the clickables
	 * @param ClickEvent clicker
	 */
	static void addClickable(ClickEvent clicker) {
		
		if (clickables.contains(clicker))
			return;
		clickables.addFirst(clicker);
		
	}
	
	/**
	 * removes a click event from the clickables
	 * @param ClickEvent clicker
	 */
	static void removeClickable(ClickEvent clicker) {
		
		clickables.remove(clicker);
		
	}
	
	/**
	 * adds a drag event from the draggables
	 * @param DragEvent drag
	 */
	static void addDraggable(DragEvent drag) {
		
		if (draggables.contains(drag))
			return;
		draggables.addFirst(drag);	
		
	}
	
	/**
	 * removes a drag event from the draggables
	 * @param DragEvent drag
	 */
	static void removeDraggable(DragEvent drag) {
		
		draggables.remove(drag);
		
	}
	
	/**
	 * communicates with the window for clickable events
	 * @param window
	 */
	public static void notifyClickables(PApplet window) {
		
		if (!window.mousePressed)
			throw new IllegalArgumentException("The PApplet provided has not been clicked");
		
		Event pack = makeEvent(window);
		
		for (ClickEvent e : clickables) {
			
			if (e.click(pack))
				break;
			
		}
		
	}
	
	/**
	 * communicates with the window for draggable events
	 * @param window
	 */
	public static void notifyDraggables(PApplet window) {
		
		Event pack = makeEvent(window);
		
		for (DragEvent e : draggables) {
			
			if (e.drag(pack))
				break;
			
		}
		
	}
	
	/**
	 * communicates with the window for release events
	 * @param window
	 */
	public static void notifyRelease(PApplet window) {
		
		if (window.mousePressed)
			throw new IllegalArgumentException("The PApplet provided has not had the mouse released");
		
		
		Event pack = makeEvent(window);
		
		for (ClickEvent e : clickables) {
			
			e.release(pack);
			
		}
		
	}
	
	/**
	 * clears the screen of all draggables and clickables
	 */
	static void clearScreen() {
		
		for (int i = 0; i < allComps.length; i ++)
			allComps[i].clear();
		
	}
	
	private static Event makeEvent(PApplet window) {
		
		return new Event(window.mouseX, window.mouseY);
		
	}
	
}
