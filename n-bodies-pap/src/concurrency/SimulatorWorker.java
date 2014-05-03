package concurrency;

import java.util.concurrent.Callable;

import support.Context;
import support.Util;
import support.V2d;
import entity.Body;

public class SimulatorWorker extends Thread {
	
	private final Body[] all_bodies;
	private final int my_index;
	private Body me;
	private int middle; 
	private int from;
	private int to;
	private int level;
	private V2d force_dest;
	private double delta_t;
	
	private double dt;
	
	/**
	 * Class SimulatorWorker constructor.
	 *
	 **/
	public SimulatorWorker(Body[] all, Body body, int from, int to, int level, V2d force_dest, double delta_t){
		super("Simulator worker level: " + level);
		all_bodies = all;
		me = body;
		my_index = body.getIndex();
		dt = delta_t;
		this.from = from;
		this.to = to;
		this.level = level;
		this.force_dest = force_dest;
		this.delta_t = delta_t;
	}
	
	public void run() {
		
		
		if(level > 1){
			middle = (from+to)/2;
			
			log(" Creating other worker..");
			SimulatorWorker work1 = new SimulatorWorker(all_bodies, me, from, middle, level-1, force_dest, delta_t);
			SimulatorWorker work2 = new SimulatorWorker(all_bodies, me, middle, to, level-1, force_dest, delta_t);
			work1.start();
			work2.start();
			
			try {
				work1.join();
				work2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}else{
			
			/*
			V2d force = new V2d(0,0);
			
			for (; from < to; from++) {
				if (from != my_index) {
					//force = force.sum(me.forceFrom(all_bodies[i]));
					double G = 6.67;
					Body that = all_bodies[from];
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
					
					force_dest.sum(force);
				}
			}
			*/
			
			V2d force = new V2d(0,0);
			for (; from < to; from++) {
				if (from != my_index) {
					force_dest = force.sum(me.forceFrom(all_bodies[from]));
					log(" force from bodies: " + force);
					log(" force_dest: " + force_dest);
				}
			}
		}
		
	}
	
	/**
     * Private method log.
     * Prints to the console a log of the activity of the Visualiser.
     * 
     * @param msg the message to be printed
     */
	private void log(String msg){
        System.out.println("[SIMULATOR WORKER] LV. " + level + msg);
    }

}
