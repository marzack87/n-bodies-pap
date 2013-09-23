package main;

import support.Vector;
import entity.Body;

/**
 * Class Controller. 
 * Represent the entity that initialize the universe and the bodies taking the necessary 
 * data from the entity Generator.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class Controller {
	 
	private Body[] allbodies;
	private int n;
	
	/**
	 * Class Controller constructor.
	 * 
	 */
	public Controller(){	
		
	}
	
	/**
	 * Method initAll.
	 * Initialize the set of the Body taking the data from a Generator object in being based 
	 * on the choice of initialization from the customer through the GUI.
	 */
	public void initAll(){
		
		Generator gen = new Generator();
		
		//qui bisogna gestire la scelta di come inizializzare il sistema (Random o da file)
		gen.initWithRandomData(10);
		n = gen.getNumberOfBodies();
		
		//Inizializzazione degli n Body
		allbodies = new Body[n];
		double[][] data = gen.getData();
		for(int i = 0; i < n; i++){
			double mass = data[4][i];
			double[] position = {data[0][i], data[1][i]};
			double[] velocity = {data[2][i], data[3][i]};
			Vector pos = new Vector(position);
			Vector vel = new Vector(velocity);
			allbodies[i] = new Body(pos, vel, mass);
		}
		
		//inizializzazione del Sistema
		// ...
	}
	
	/**
	 * Method startSimulation.
	 * Let the Simulation to start.
	 */
	public void startSimulation(){
		// qui parte tutto
		this.print_body();
	}
	
	/**
	 * Method print_body.
	 * Print all the informations about all the bodies presented in the allbodies array 
	 */
	void print_body(){
		for(int i = 0; i<n; i++){
			System.out.println(allbodies[i].getPosition() + " " + allbodies[i].getVelocity() + " " + allbodies[i].getMass() );
		}
	}
	

}
