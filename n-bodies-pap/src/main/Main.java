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
		
		MainFrame initFrame = new MainFrame("N-Body Simulation");
		initFrame.setVisible(true);
		
		/*Controller contr = new Controller();
		contr.initAll();
		contr.startSimulation();
		*/
	}

}
