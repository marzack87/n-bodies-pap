package concurrency;

import java.util.concurrent.Callable;

import support.V2d;
import entity.Body;

public class BodyTask implements Callable<Body> {
	
	private final Body[] all_bodies;
	private final int my_index;
	private Body me;
	
	private double dt;
	
	/**
	 * Class BodyTask default constructor.
	 *
	 **/
	public BodyTask(Body[] all, int i, double delta_t){
		all_bodies = all;
		my_index = i;
		me = all_bodies[my_index];
		dt = delta_t;
	}
	
	public Body call() throws Exception {
		
		V2d force = new V2d(0,0);
		for (int i = 0; i < all_bodies.length; i++) {
			if (i != my_index) {
				force = force.sum(me.forceFrom(all_bodies[i]));
			}
		}
		
		if(me.getMassValue() != 500000000)me.move(force, dt);
		
		return me;
	}

}
