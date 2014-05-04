package concurrency;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import support.Context;
import support.P2d;
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
	private V2d force_dst;
	private double delta_t;
	private Semaphore mutex_force_dst;
	
	private V2d force;
	public List<V2d> forcelist;
	private Semaphore mutex_force;
	
	private double dt;
	
	/**
	 * Class SimulatorWorker constructor for Master.
	 *
	 **/
	public SimulatorWorker(Body[] all, Body body, int from, int to, int level, double delta_t){
		super("Simulator worker level: " + level);
		all_bodies = all;
		me = body;
		my_index = body.getIndex();
		dt = delta_t;
		this.from = from;
		this.to = to;
		this.level = level;
		this.force_dst = new V2d(0,0);
		this.delta_t = delta_t;
		mutex_force_dst = new Semaphore(1);
	}
	
	/**
	 * Class SimulatorWorker constructor for Workers.
	 *
	 **/
	public SimulatorWorker(Body[] all, Body body, int from, int to, int level, V2d force_dest, double delta_t, Semaphore mutex){
		super("Simulator worker level: " + level);
		all_bodies = all;
		me = body;
		my_index = body.getIndex();
		dt = delta_t;
		this.from = from;
		this.to = to;
		this.level = level;
		this.force_dst = force_dest;
		this.delta_t = delta_t;
		this.mutex_force_dst = mutex;
	}
	
	/**
	 * Class SimulatorWorker constructor for loopV5.
	 *
	 **/
	public SimulatorWorker(Body[] all, Body body, int from, int to, List<V2d> forcelist, Semaphore mutex_force){
		
		all_bodies = all;
		me = body;
		my_index = body.getIndex();
		this.from = from;
		this.to = to;
		this.mutex_force = mutex_force;
		this.forcelist = forcelist;
		force = new V2d(0,0);
	}
	
	public void runold() {
		
		
		if(level > 0){
			middle = (from+to)/2;
			
			//log(" Creating other worker..");
			SimulatorWorker work1 = new SimulatorWorker(all_bodies, me, from, middle, level-1, force_dst, delta_t, mutex_force_dst);
			SimulatorWorker work2 = new SimulatorWorker(all_bodies, me, middle, to, level-1, force_dst, delta_t, mutex_force_dst);
			work1.start();
			work2.start();
			
			try {
				work1.join();
				work2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
				
				//log(" force dst: " + force_dst);
				
				if(me.getMassValue() != Util.SUN_MASS) me.move(force_dst, dt);
				
				/*if(me.getMassValue() != Util.SUN_MASS){
					 
					if (me.collision){
						me.v = me.vel_after_collision;
						//System.out.println("Body " + index + " - vel_after_collision = " + vel_after_collision);
						V2d dp = me.v.mul(dt);
						me.p = me.p.sum(dp);
					} else {
						V2d a = force_dst.mul(1/me.mass);
						V2d dv = a.mul(dt);
						V2d dp = (me.v.sum(dv.mul(1/2))).mul(dt);
						me.p = me.p.sum(dp);
						me.v = me.v.sum(dv);
					}
					
					me.collision = false;
					me.vel_after_collision.x = 0;
					me.vel_after_collision.y = 0;	
				}*/
			
		}else{
			
			
			V2d force = new V2d(0,0);
			
			for (; from < to; from++) {
				if (from != my_index) {
					//force = force.sum(me.forceFrom(all_bodies[i]));
					double G = 6.67;
					/*Body that = all_bodies[from];
					V2d p_this = new V2d(me.p.x, me.p.y);
					V2d p_that = new V2d(that.p.x, that.p.y);
					V2d delta = p_that.min(p_this);
					double dist = that.p.dist(me.p);*/
					double dist = all_bodies[from].p.dist(me.p);
					V2d delta = me.p.sub(all_bodies[from].p);
					
					if (all_bodies[from].getMassValue() == Util.SUN_MASS){
						if (dist*Util.scaleFact <= (Util.SUN_RADIUS + Util.BODY_RADIUS)){
							//collision(that);
							me.collision = true;
							
							V2d final_v = me.v.mul(me.mass - all_bodies[from].mass).sum(all_bodies[from].v.mul(2 * all_bodies[from].mass)).mul(1 / (me.mass + all_bodies[from].mass));
							me.vel_after_collision = me.vel_after_collision.sum(final_v);
						}
					} else {
						if (dist*Util.scaleFact <= (Util.BODY_RADIUS * 2)) {
							me.collision = true;
							
							V2d final_v = me.v.mul(me.mass - all_bodies[from].mass).sum(all_bodies[from].v.mul(2 * all_bodies[from].mass)).mul(1 / (me.mass + all_bodies[from].mass));
							me.vel_after_collision = me.vel_after_collision.sum(final_v);
						
						}
					}
					
					double F = (G * (me.mass) * (all_bodies[from].mass)) / (dist * dist);
					
					V2d delta_normalized = delta.getNormalized();
					force = delta_normalized.mul(F);
					try {
						mutex_force_dst.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					force_dst.sum(force);
					mutex_force_dst.release();
				}
			}
			
			
			/*V2d force = new V2d(0,0);
			for (; from < to; from++) {
				if (from != my_index) {
					force = force.sum(me.forceFrom(all_bodies[from]));
					log(" force from bodies: " + force);
					force_dst = force_dst.sum(force);
					log(" force_dst: " + force_dst);
				}
			}*/
		}
		//log("Dead..");
		
	}
	
	public void run() {
			
			V2d forceFrom = new V2d(0,0);
			
			for (; from < to; from++) {
				if (from != my_index) {
					forceFrom = forceFrom.sum(me.forceFrom(all_bodies[from]));
					/*
					double G = 6.67;
					double dist = all_bodies[from].p.dist(me.p);
					
					if (all_bodies[from].getMassValue() == Util.SUN_MASS){
						if (dist*Util.scaleFact <= (Util.SUN_RADIUS + Util.BODY_RADIUS)){
							
							me.collision = true;
							
							V2d final_v = me.v.mul(me.mass - all_bodies[from].mass).sum(all_bodies[from].v.mul(2 * all_bodies[from].mass)).mul(1 / (me.mass + all_bodies[from].mass));
							me.vel_after_collision = me.vel_after_collision.sum(final_v);
						}
					} else {
						if (dist*Util.scaleFact <= (Util.BODY_RADIUS * 2)) {
							me.collision = true;
							
							V2d final_v = me.v.mul(me.mass - all_bodies[from].mass).sum(all_bodies[from].v.mul(2 * all_bodies[from].mass)).mul(1 / (me.mass + all_bodies[from].mass));
							me.vel_after_collision = me.vel_after_collision.sum(final_v);
						
						}
					}
					
					double F = (G * (me.mass) * (all_bodies[from].mass)) / (dist * dist);
					// qui forse potremmo fare un controllo per il valore della forza..se e' troppo piccola salta
					
					V2d delta = me.p.sub(all_bodies[from].p);
					V2d delta_normalized = delta.getNormalized();
					forceFrom = delta_normalized.mul(F);
					*/
					
				}
				force.sum(forceFrom);
				
			}
			
			try {
				mutex_force.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			forcelist.add(force);
			mutex_force.release();
			
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
