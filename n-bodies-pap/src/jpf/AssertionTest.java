package jpf;

/**
 * Class DeadlockTest.
 * <p>
 * Class test to verify that in the system a deadlock situation in all the possible scenarios envisaged is not present.<br>
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import jpf.DeadlockTest.BodyTask;
import jpf.DeadlockTest.Context;
import jpf.DeadlockTest.Generator;
import jpf.DeadlockTest.Simulator;
import jpf.DeadlockTest.Visualiser;
import entity.Body;
import gov.nasa.jpf.vm.Verify;
import support.V2d;

public class AssertionTest {
		
		static class Context{
			public Body[] allbodies;
			public double dt;
			public int step;
			public int number;
			public int assertion_variable;
			
			public Context(){
				dt = 0.01;
				step = 10;
				number = 10;
				assertion_variable = 0;
			}
			
			public synchronized void updateBody(Body body){
		   		allbodies[body.getIndex()] = body;
			}
			
			public synchronized void inc(){
				assertion_variable++;
		}
		
	}
	
	static class Generator{
		
		private Context c;
		
		private int[] position_x;
		private int[] position_y;
		private int[] velocity_x;
		private int[] velocity_y;
		private int[] mass;
		//private int number;
		
		public Generator(Context c){
			this.c = c;
			//number = c.number;
		}
		
		
		public void initRandom(int number){
			position_x = new int[number];
			position_y = new int[number];
			velocity_x = new int[number];
			velocity_y = new int[number];
			mass = new int[number];
			
			for (int i = 0; i < number; i++){
				position_x[i] = Verify.getInt(1, 1024);	//(Math.random() * ((Util.VisualiserAvailableSpace().width - 5)/Util.scaleFact - 1) ) + 1;
				position_y[i] = Verify.getInt(1, 800);	//(Math.random() * ((Util.VisualiserAvailableSpace().height - 5)/Util.scaleFact - 1) ) + 1;
				velocity_x[i] = Verify.getInt(1, 50);	//((Math.random() * (Util.RANGE_BODIES_VELOCITY - 1) ) + 1) - (Util.RANGE_BODIES_VELOCITY / 2);
				velocity_y[i] = Verify.getInt(1, 50);	//((Math.random() * (Util.RANGE_BODIES_VELOCITY - 1) ) + 1) - (Util.RANGE_BODIES_VELOCITY / 2);
				mass[i] = Verify.getInt(1, 10);
				System.out.println(" Px: " + position_x[i] +" Py: " + position_y[i] +" Vx: " + velocity_x[i] +" Vy: " + velocity_x[i]);
			}
			
			
			this.initBody(c, number);
		}
		
		public void initBody(Context c, int number){
			c.allbodies = new Body[number];
			int[][] data = this.getData();
			for(int i = 0; i < c.allbodies.length; i++){
				double mass = data[4][i];
				System.out.println("Mass: " + mass);
				V2d pos = new V2d(data[0][i], data[1][i]);
				V2d vel = new V2d(data[2][i], data[3][i]);
				c.allbodies[i] = new Body(pos, vel, mass, i);
			}
		}
		
		public int[][] getData(){
			int[][] data = {position_x, position_y, velocity_x, velocity_y, mass};
			return data;
		}
	}
	
	public static class Body { 
		public V2d p; //position
		public V2d v; //velocity
		public double mass; //mass
		public int index;
		
		public boolean collision;
		public V2d vel_after_collision;
		
		public Body(V2d p, V2d v, double mass, int index) {
			this.p = p;
			this.v = v;
			this.mass = mass;
			this.index = index;
			
			collision = false;
			vel_after_collision = new V2d(0,0);
		}
		
		public Body copy(){
			return new Body(this.p, this.v, this.mass, this.index);
			
		}
		
		public int getIndex(){
			return index;
		}
		
		public V2d forceFrom(Body that) {
			
			double G = 6.67*Math.pow(10,-11);
			V2d p_this = this.p; //new V2d(this.p.x, this.p.y);
			V2d p_that = that.p;  //new V2d(that.p.x, that.p.y);
			V2d delta = p_that.sub(p_this);
			double dist = that.p.dist(this.p);
			double F;
			
			F = (G * (this.mass) * (that.mass)) / (dist * dist);
	
			V2d delta_normalized = delta.getNormalized();
			V2d Force = delta_normalized.mul(F);
			
			//System.out.println(Force);
			return Force;
		}
		
		public synchronized void move(V2d f, double dt) { 
			
			V2d a = f.mul(1/mass);
			V2d dv = a.mul(dt);
			V2d dp = (v.sum(dv.mul(1/2))).mul(dt);
			p = p.sum(dp);
			v = v.sum(dv);
			
			collision = false;
			vel_after_collision = new V2d(0,0);
		}
		

	}

	
	static class Simulator extends Thread{
		
		private Context c;
		private Semaphore sem;
		private Semaphore printed;
		
		private static final ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
		private List<Future<Body>> list = new ArrayList<Future<Body>>();
		private BodyTask[] bodytasks_array;
		
		public Simulator(Context c, Semaphore s, Semaphore p){
			this.c = c;
			sem = s;
			printed = p;
			bodytasks_array = new BodyTask[c.allbodies.length];
			for (int i = 0; i < c.allbodies.length; i++){
				bodytasks_array[i] = new BodyTask(c.allbodies, i, c.dt, c);
			}
		}
		
		
		public void run(){
			
			//while(c.step > 0){
			double dt = c.dt;
			for (int i = 0; i < c.allbodies.length; i++){
					bodytasks_array[i].updateDt(dt);
					Callable<Body> task = bodytasks_array[i];
					Future<Body> submit = exec.submit(task);
					list.add(submit);
				}
				for (Future<Body> future : list){
					try {
						Body body = future.get();
							c.updateBody(body);
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
				list.clear();
				c.inc();
				this.sem.release();
				try {
					this.printed.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//c.step--;
			//}
		}
	}
	
	static class BodyTask implements Callable<Body> {
		
		private final Body[] all_bodies;
		private final int my_index;
		private Body me;
		private Context c;
		
		private double dt;
	
		public BodyTask(Body[] all, int i, double delta_t, Context c){
			all_bodies = all;
			my_index = i;
			me = all_bodies[my_index];
			dt = delta_t;
			this.c = c;
		}
		
		public Body call() throws Exception {
			
			V2d force = new V2d(0,0);
			for (int i = 0; i < all_bodies.length; i++) {
				if (i != my_index) {
					force = force.sum(me.forceFrom(all_bodies[i]));
				}
			}
			
			me.move(force, dt);
			c.inc();
			
			return me;
		}
		
		public void updateDt(double delta_time) {
			dt = delta_time;
		}

	}
	
	
	static class Visualiser extends Thread{
		
		private Context c;
		private Semaphore sem;
		private Semaphore printed;
		
		public Visualiser(Context c, Semaphore s, Semaphore p){
			this.c = c;
			sem = s;
			printed = p;
		}
		
		public void run(){
		
			//while(c.step > 0) {
				try {
					this.sem.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//System.out.println("Print Bodies updated");
				c.inc();
	    		this.printed.release();
			//}
		}
		
	}

	public static void main(String[] args) {
		
		Verify.beginAtomic();
		
		File f = new File("BodiesJPF.txt");
		
		Context c = new Context();
		Generator gen = new Generator(c);
		gen.initRandom(c.number);
		//gen.initFromFile(f);
		
		Semaphore sem = new Semaphore(0);
		Semaphore printed = new Semaphore(0);
		
		Simulator sim = new Simulator(c, sem, printed);
		Visualiser vis = new Visualiser(c, sem, printed);
		sim.start();
		vis.start();
		
		Verify.endAtomic();
		
		try {
			sim.join();
			vis.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assert c.assertion_variable == (c.number+2);
		
	}

}
