package main;

import support.Vector;
import entity.Body;

/**
 * Class Controller. 
 * Rappresent the entity that inizialize the universe and the bodies taking the data from the
 * entity Generator
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 * 
 *
 */
public class Controller {
	 
	private Body[] allbodies;
	
	public Controller(Generator gen){
		
		int n = gen.getNumberOfBodies();
		for(int i = 0; i < n; i++){
			double mass = 0; // gen.getMass
			double[] position = {/*pos_x, pos_y*/};
			double[] velocity = {/*vel_x, vel_y*/};
			Vector pos = new Vector(position);
			Vector vel = new Vector(velocity);
			allbodies[i] = new Body(pos, vel, mass);
		}
		
		
		
	}

	

}
