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
	private int n;
	
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
	
	void print_body(){
		for(int i = 0; i<n; i++){
			System.out.println(allbodies[i].getPosition() + " " + allbodies[i].getVelocity() + " " + allbodies[i].getMass() );
		}
	}
	

}
