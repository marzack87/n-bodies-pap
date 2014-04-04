package main;
 
import gui.MainFrame;
import support.Context;

public class Main {

	/**
	 * Class Main.
	 * Starter point of N-Body Simulation
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create the context witch contains all the resource
		Context cont = new Context();
		
		// Start GUI
		MainFrame initFrame = new MainFrame("N-Body Simulation", cont);
		initFrame.setVisible(true);
	
	}

}
