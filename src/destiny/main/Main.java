package destiny.main;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import destiny.assets.Constants;
import destiny.panels.Window;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * 
 * The class that holds the main method of the program
 * 
 * @author Nathaniel
 * @version 5/13/2021
 *
 */
public class Main {

	public static void main(String[] args) {
		System.setProperty("java.library.path", "win32-x86-64");
		System.setProperty("jna.library.path", "win32-x86-64");
		System.out.println(System.getProperty("java.library.path"));
		System.out.println(System.getProperty("jna.library.path"));

		Window drawing = new Window();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		window.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		window.setLocation(0, 0);
		window.setMinimumSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
		window.setMaximumSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		window.setVisible(true);
		canvas.requestFocus();

	}
	
}
