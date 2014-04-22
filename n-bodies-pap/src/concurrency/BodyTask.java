package concurrency;

import java.util.concurrent.Callable;

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
		
		for (int i = 0; i < all_bodies.length; i++) {
			if (i != my_index) {
				
			}
		}
		
		return me;
	}

}
