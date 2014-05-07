package main;
 
import entity.Controller;
import gui.MainFrame;
import support.Context;

/**
 *  N-BODY SIMULATION
 *  <p>
 *  Progetto di Programmazione Avanza e Paradigmi<br>
 *  Anno accademico 2013-2014<br>
 *  Prof. Alessandro Ricci<br>
 *  
 *  @author Richiard Casadei, Marco Zaccheroni
 */

public class Main {

	/**
	 * Class Main.
	 * <p>
	 * Starter point of N-Body Simulation
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create the context witch contains all the resource
		Context cont = new Context();
		
		// The entity Controller which will create the BodyAgent
		Controller contr = new Controller(cont);
		
		// Start GUI
		MainFrame initFrame = new MainFrame("N-Body Simulation", contr);
		initFrame.setVisible(true);
	
	}

}
