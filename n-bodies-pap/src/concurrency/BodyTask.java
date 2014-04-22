package concurrency;

import java.util.concurrent.Callable;

import support.V2d;
import entity.Body;

public class BodyTask implements Callable<Body> {
	
	private final Body[] all_bodies;
	private final int my_index;
	private Body me;
	
	/**
	 * Class BodyTask default constructor.
	 *
	 **/
	public BodyTask(Body[] all, int i){
		all_bodies = all;
		my_index = i;
		me = all_bodies[my_index];
	}
	
	public Body call() throws Exception {
		
		V2d force = new V2d(10,10);
		for (int i = 0; i < all_bodies.length; i++) {
			if (i != my_index) {
				//System.out.println("Forza " + me.forceFrom(all_bodies[i]));
				force.sum(me.forceFrom(all_bodies[i]));
			}
		}
		
		me.move(force);
		
		return me;
	}

}
