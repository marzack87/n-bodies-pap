package concurrency;

import java.util.concurrent.Callable;

import support.Util;
import support.V2d;
import entity.Body;

/**
 * Class BodyTask.
 * Class that implements the Callable<?> Java interface.
 * 
 * It represents the task to be assigned to a thread in the ExecutorService pool and that, 
 * through the method call forceFrom the Body class, performs the operations necessary to calculate the force exerted on a single body
 * (passed as a parameter to the constructor of the class) from each body present in the "galaxy". 
 * After the computation of the total force exerted on the body, the task continues updating information(position, speed and possible collisions) 
 * inside the Body and finally returns the updated Body.
 * 
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 * 
 */

public class BodyTask implements Callable<Body> {
	
	private final Body[] all_bodies;
	private final int my_index;
	private Body me;
	
	private double dt;
	
	/**
	 * Class BodyTask default constructor.
	 * 
	 * @param all 		The bodies array
	 * @param i 		The array index representing the Body on which we want compute the total gravitational force
	 * @param delta_t 	Time istant
	 **/
	public BodyTask(Body[] all, int i, double delta_t){
		all_bodies = all;
		my_index = i;
		me = all_bodies[my_index];
		dt = delta_t;
	}
	
	/**
	 * Method call.
	 * 
	 * Simple implementation of the brute force algorithm for the calculation of the total gravitational force acting on a body.
	 * This method also updates the Body data, calling the Class Body method move().
	 * 
	 * @throws Exception
	 * @return me 	The updated Body
	 */
	public Body call() throws Exception {
		
		V2d force = new V2d(0,0);
		for (int i = 0; i < all_bodies.length; i++) {
			if (i != my_index) {
				force = force.sum(me.forceFrom(all_bodies[i]));
			}
		}
		
		if(me.getMassValue() != Util.SUN_MASS)me.move(force, dt);
		
		return me;
	}

}
