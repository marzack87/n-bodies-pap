package concurrency;

import java.util.concurrent.Callable;

import support.Context;
import support.Util;
import support.V2d;
import entity.Body;

public class BodyTaskV3 implements Callable<Body> {
	
	private final Body[] all_bodies;
	private final int my_index;
	private Body me;
	private Context cont;
	
	private double dt;
	
	/**
	 * Class BodyTaskV2 default constructor.
	 *
	 **/
	public BodyTaskV3(Context cont, Body[] all, int i, double delta_t){
		this.cont = cont;
		all_bodies = all;
		my_index = i;
		me = all_bodies[my_index];
		dt = delta_t;
	}
	
	public Body call() {
		
		V2d force = new V2d(0,0);
		for (int i = 0; i < all_bodies.length; i++) {
			if (i != my_index) {
				//force = force.sum(me.forceFrom(all_bodies[i]));
				double G = 6.67;
				Body that = all_bodies[i];
				V2d p_this = new V2d(me.p.x, me.p.y);
				V2d p_that = new V2d(that.p.x, that.p.y);
				V2d delta = p_that.min(p_this);
				double dist = that.p.dist(me.p);
				
				if (that.getMassValue() == Util.SUN_MASS){
					if (dist*Util.scaleFact <= (Util.SUN_RADIUS + Util.BODY_RADIUS)){
						//collision(that);
						me.collision = true;
						
						V2d final_v = me.v.mul(me.mass - that.mass).sum(that.v.mul(2 * that.mass)).mul(1 / (me.mass + that.mass));
						me.vel_after_collision = me.vel_after_collision.sum(final_v);
					}
				} else {
					if (dist*Util.scaleFact <= (Util.BODY_RADIUS * 2)) {
						me.collision = true;
						
						V2d final_v = me.v.mul(me.mass - that.mass).sum(that.v.mul(2 * that.mass)).mul(1 / (me.mass + that.mass));
						me.vel_after_collision = me.vel_after_collision.sum(final_v);
					
					}
				}
				
				double F = (G * (me.mass) * (that.mass)) / (dist * dist);
				
				V2d delta_normalized = delta.getNormalized();
				force = delta_normalized.mul(F);
			}
		}
		
		//if(me.getMassValue() != Util.SUN_MASS)me.move(force, dt);
		if(me.getMassValue() != Util.SUN_MASS){
			if (me.collision){
				me.v = me.vel_after_collision;
				//System.out.println("Body " + index + " - vel_after_collision = " + vel_after_collision);
				V2d dp = me.v.mul(dt);
				me.p = me.p.sum(dp);
			} else {
				V2d a = force.mul(1/me.mass);
				V2d dv = a.mul(dt);
				V2d dp = (me.v.sum(dv.mul(1/2))).mul(dt);
				me.p = me.p.sum(dp);
				me.v = me.v.sum(dv);
			}
			
			me.collision = false;
			me.vel_after_collision.x = 0;
			me.vel_after_collision.y = 0;
			
		}
		
		cont.updateBody(me);
		return me;
		
	}

}
