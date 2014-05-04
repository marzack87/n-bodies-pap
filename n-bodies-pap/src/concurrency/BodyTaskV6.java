package concurrency;

import support.Context;
import support.Util;
import support.V2d;
import entity.Body;

public class BodyTaskV6 implements Runnable {
	
	private final Body[] all_bodies;
	private final int my_index;
	private Body me;
	private Context context;
	
	private double dt;
	
	/**
	 * Class BodyTask default constructor.
	 *
	 **/
	public BodyTaskV6(Context context, Body[] all, int i, double delta_t){
		all_bodies = all;
		my_index = i;
		me = all_bodies[my_index];
		dt = delta_t;
		this.context = context;
	}
	
	public void run(){
		
		V2d force = new V2d(0,0);
		for (int i = 0; i < all_bodies.length; i++) {
			if (i != my_index) {
				force = force.sum(me.forceFrom(all_bodies[i]));
			}
		}
		
		if(me.getMassValue() != Util.SUN_MASS)me.move(force, dt);
		
		context.updateBody(me);
		
	}

}
