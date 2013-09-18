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
	 * It takes the data stored by the Generator object and use them to initialize the bodies.
	 * 
	 * @param gen - Generator Object
	 */
	public Controller(Generator gen){
		
		n = gen.getNumberOfBodies();
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
