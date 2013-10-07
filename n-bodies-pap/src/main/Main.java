package main;

import entity.Controller;
import gui.MainFrame;

public class Main {

	/**
	 * Class Main.
	 * Starter point of N-Body Simulation
	 * 
	 * @param args
	 */
	public static void main(String[] args) { 
		
		//MainFrame initFrame = new MainFrame("What the fuck you want??");
		//initFrame.setVisible(true);
		
		Controller contr = new Controller();
		contr.initAll();
		contr.startSimulation();
	}

}
