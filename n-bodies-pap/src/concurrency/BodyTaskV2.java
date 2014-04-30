package concurrency;

import java.util.ArrayList;

import support.Context;
import support.Util;
import support.V2d;
import entity.Body;

public class BodyTaskV2 implements Runnable {
	
	private final Body[] all_bodies;
	private ArrayList<Body> new_bodies = new ArrayList();
	private int first_index,last_index;
	private Body me;
	private Context cont;
	
	private double dt;
	
	/**
	 * Class BodyTask default constructor.
	 *
	 **/
	public BodyTaskV2(Context cont, Body[] all, int from, int to, double delta_t){
		all_bodies = all;
		first_index = from;
		last_index = to;
		dt = delta_t;
		this.cont = cont;
	}
	
	public void run() {
		
		V2d force = new V2d(0,0);
		for(;first_index <= last_index; first_index++){
			me = all_bodies[first_index];
			for (int i = 0; i < all_bodies.length; i++) {
				if (i != first_index) {
					force = force.sum(me.forceFrom(all_bodies[i]));
				}
			}
			if(me.getMassValue() != Util.SUN_MASS)me.move(force, dt);
			new_bodies.add(me);
		}
		for(Body body : new_bodies) cont.updateBody(me);
	}

}
