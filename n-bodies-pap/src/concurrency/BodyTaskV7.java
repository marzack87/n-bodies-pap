package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;



import java.util.concurrent.Semaphore;


//import support.Util;
import support.V2d;
import entity.Body;

public class BodyTaskV7 implements Runnable {
	
	private final Body[] all_bodies;
	private final int my_index;
	private Body me;
	private int middle; 
	private int from;
	private int to;
	private List<V2d> force_list;
	private Semaphore mutex;
	
	/**
	 * Class BodyTaskV7 default constructor.
	 *
	 **/
	public BodyTaskV7(Body[] all, Body body, int from, int to, List<V2d> force_list, Semaphore mutex){
		all_bodies = all;
		me = body;
		my_index = body.getIndex();
		this.from = from;
		this.to = to;
		this.force_list = force_list;
		this.mutex = mutex;
	}
	
	public void run() {
		
		V2d force = new V2d(0,0);
		for (int i = 0; i < all_bodies.length; i++) {
			if (i != my_index) {
				force = force.sum(me.forceFrom(all_bodies[i]));
			}
		}
		
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		force_list.add(force);
		mutex.release();
	}

}
