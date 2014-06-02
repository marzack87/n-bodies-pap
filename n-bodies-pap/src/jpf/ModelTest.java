package jpf;

/**
 * Class DeadlockTest.
 * <p>
 * Class test to verify the safety e liveness propriety in the system.<br>
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import gov.nasa.jpf.vm.Verify;

public class ModelTest {
	
	static class Context{
		public Body[] allbodies;
		public double dt;
		public int step;
		public int number;
		protected int assertion_variable;
		
		public Context(){
			dt = 0.01;
			step = 10;
			number = 2;
			assertion_variable = 0;
		}
		
		public synchronized void updateBody(Body body){
	   		allbodies[body.getIndex()] = body;
		}
		
		public synchronized void inc(){
			assertion_variable++;
		}
		
		public int getVariable(){
			return assertion_variable;
		}
		
	}
	
	// For init random
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
				position_x[i] = Verify.getInt(1, 1024);	
				position_y[i] = Verify.getInt(1, 800);	
				velocity_x[i] = Verify.getInt(1, 50);	
				velocity_y[i] = Verify.getInt(1, 50);	
				mass[i] = Verify.getInt(1, 10);
				//System.out.println(" Px: " + position_x[i] +" Py: " + position_y[i] +" Vx: " + velocity_x[i] +" Vy: " + velocity_x[i]);
			}
			
			
			this.initBody(c, number);
		}
		
		public void initBody(Context c, int number){
			c.allbodies = new Body[number];
			int[][] data = this.getData();
			for(int i = 0; i < c.allbodies.length; i++){
				double mass = data[4][i];
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
	
	// For init from file
	/*static class Generator{
		
		private Context c;
		
		private double[] position_x;
		private double[] position_y;
		private double[] velocity_x;
		private double[] velocity_y;
		private double[] mass;
		private int number;
		
		public Generator(Context c){
			this.c = c;
			number = c.number;
		}
		
		public void initFromFile(File f){
			try{
				
				  FileInputStream fstream = new FileInputStream(f);

				  DataInputStream in = new DataInputStream(fstream);
				  BufferedReader br = new BufferedReader(new InputStreamReader(in));
				  String strLine;
				  
				  if ((strLine = br.readLine()) != null){
					  number = Integer.valueOf(strLine);
					  position_x = new double[number];
					  position_y = new double[number];
					  velocity_x = new double[number];
					  velocity_y = new double[number];
					  mass = new double[number];
				  } else {
					  System.err.println("File is empty");
				  }
				  int i = 0;
				  while ((strLine = br.readLine()) != null)   {
					  String[] values = strLine.split(" ");
					  position_x[i] = Double.valueOf(values[0]);
					  position_y[i] = Double.valueOf(values[1]);
					  velocity_x[i] = Double.valueOf(values[2]);
					  velocity_y[i] = Double.valueOf(values[3]);
					  mass[i] = Double.valueOf(values[4]);
					  System.out.println(" Px: " + position_x[i] +" Py: " + position_y[i] +" Vx: " + velocity_x[i] +" Vy: " + velocity_x[i]);
					  i++;
				  }
				  in.close();
				  
			}catch (Exception ex){
				System.err.println(ex); 
			}

			this.initBody(c, number);
		}
		
		public void initBody(Context c, int number){
			c.allbodies = new Body[number];
			double[][] data = this.getData();
			for(int i = 0; i < c.allbodies.length; i++){
				double mass = data[4][i];
				System.out.println("Mass: " + mass);
				V2d pos = new V2d(data[0][i], data[1][i]);
				V2d vel = new V2d(data[2][i], data[3][i]);
				c.allbodies[i] = new Body(pos, vel, mass, i);
			}
		}
		
		public double[][] getData(){
			double[][] data = {position_x, position_y, velocity_x, velocity_y, mass};
			return data;
		}
	}*/
	
	public static class Body { 
		public V2d p; //position
		public V2d v; //velocity
		public double mass; //mass
		public int index;
		
		public Body(V2d p, V2d v, double mass, int index) {
			this.p = p;
			this.v = v;
			this.mass = mass;
			this.index = index;
		}
		
		public Body copy(){
			return new Body(this.p, this.v, this.mass, this.index);
			
		}
		
		public int getIndex(){
			return index;
		}
		
		public V2d forceFrom(Body that) {

			double G = 6.67*Math.pow(10,-11);
			V2d p_this = this.p; 
			V2d p_that = that.p;  
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
			
		}

	}
	
	public static class V2d{

	    public double x,y;

	    public V2d(double x,double y){
	        this.x=x;
	        this.y=y;
	    }
	    
	    public double abs(){
	        return (double)Math.sqrt(x*x+y*y);
	    }
	    
	    public double dist(V2d v){
	    	return Math.sqrt(( x - v.x )*( x - v.x )+( y - v.y )*( y - v.y ));
	    	
	    }
	    
	    public V2d getNormalized(){
	        double module=(double)Math.sqrt(x*x+y*y);
	        //return new V2d(x/module,y/module);
	        this.x = x/module;
	        this.y = y/module;
	        return this;
	    }
	    
	    public V2d mul(double fact){
	        //return new V2d(x*fact,y*fact);
	        this.x = x*fact;
	        this.y = y*fact;
	        return this;
	    }

	    public synchronized V2d sum(V2d v){
			//return new V2d(x+v.x,y+v.y);
			this.x = this.x+v.x;
			this.y = this.y+v.y;
			return this;
	    }
	    
	    public V2d sub(V2d v){
	        //return new V2d(x-v.x,y-v.y);
	    	this.x = this.x-v.x;
	    	this.y = this.y-v.y;
	    	return this;
	    }

	    public String toString(){
	        return "V2d("+x+","+y+")";
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
				bodytasks_array[i] = new BodyTask(c.allbodies, i, c.dt);
				c.inc();
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
			exec.shutdown();
		}
	}
	
static class BodyTask implements Callable<Body> {
		
		private final Body[] all_bodies;
		private final int my_index;
		private Body me;
		
		private double dt;
	
		public BodyTask(Body[] all, int i, double delta_t){
			all_bodies = all;
			my_index = i;
			me = all_bodies[my_index];
			dt = delta_t;
		}
		
		public Body call() throws Exception {
			
			V2d force = new V2d(0,0);
			
			Verify.beginAtomic();
			
			for (int i = 0; i < all_bodies.length; i++) {
				if (i != my_index) {
					force = force.sum(me.forceFrom(all_bodies[i]));
				}
			}
			
			me.move(force, dt);
			//c.inc();
			Verify.endAtomic();
			
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
				// Here the Visualiser call updatePosition()
				c.inc();
	    		this.printed.release();
			//}
		}
		
	}

	public static void main(String[] args) {
		
		Verify.beginAtomic();
		
		//File f = new File("BodiesJPF.txt");
		
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
		int value = c.getVariable();
		assert value == 4;
		
	}

}
