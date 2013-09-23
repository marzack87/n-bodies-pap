package main;

import gui.MainFrame;

public class Main {

	/**
	 * Class Main.
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
