package main;

/**
 *  N-BODY SIMULATION
 *  
 *  Progetto di Programmazione Avanza e Paradigmi
 *  Anno accademico 2013-2014
 *  Prof. Alessandro Ricci
 *  
 *  @author Richiard Casadei, Marco Zaccheroni
 */
 
import entity.Controller;
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
		
		// The entity Controller which will create the BodyAgent
		Controller contr = new Controller(cont);
		
		// Start GUI
		MainFrame initFrame = new MainFrame("N-Body Simulation", contr);
		initFrame.setVisible(true);
	
	}

}
