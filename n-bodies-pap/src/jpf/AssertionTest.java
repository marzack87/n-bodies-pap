package jpf;

/**
 * Class DeadlockTest.
 * <p>
 * Class test to verify that in the system a deadlock situation in all the possible scenarios envisaged is not present.<br>
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */

/*import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;*/
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import gov.nasa.jpf.vm.Verify;
import support.Util;
import support.V2d;
import entity.Body;

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
	   		inc();
		}
		
		public void print_body() throws Exception{
			System.out.println("Printing bodies data from context......");
			for(int i = 0; i<allbodies.length; i++){
				System.out.println("AllBodies: " + this.allbodies[i].getPosition() + " " + this.allbodies[i].getVelocity() + " " + this.allbodies[i].getMass() );
			}
		}
		
		public void inc(){
			assertion_variable++;
		}
	}
	
	static class Generator{
		
		private Context c;
		
		private double[] position_x;
		private double[] position_y;
		private double[] velocity_x;
		private double[] velocity_y;
		private double[] mass;
		private int number;
		
		public Generator(Context c){
			this.c = c;
		}
		
		
		public void initRandom(int number){
			position_x = new double[number];
			position_y = new double[number];
			velocity_x = new double[number];
			velocity_y = new double[number];
			mass = new double[number];
			
			for (int i = 0; i < number; i++){
				position_x[i] = (double)Verify.getInt(1, (int) ((Util.VisualiserAvailableSpace().width - 5)/Util.scaleFact - 1));	//(Math.random() * ((Util.VisualiserAvailableSpace().width - 5)/Util.scaleFact - 1) ) + 1;
				position_y[i] = (double)Verify.getInt(1, (int) ((Util.VisualiserAvailableSpace().width - 5)/Util.scaleFact - 1));	//(Math.random() * ((Util.VisualiserAvailableSpace().height - 5)/Util.scaleFact - 1) ) + 1;
				velocity_x[i] = (double)Verify.getInt(1, (int) ((((Util.RANGE_BODIES_VELOCITY - 1) ) + 1) - (Util.RANGE_BODIES_VELOCITY / 2)));	//((Math.random() * (Util.RANGE_BODIES_VELOCITY - 1) ) + 1) - (Util.RANGE_BODIES_VELOCITY / 2);
				velocity_y[i] = (double)Verify.getInt(1, (int) ((((Util.RANGE_BODIES_VELOCITY - 1) ) + 1) - (Util.RANGE_BODIES_VELOCITY / 2)));	//((Math.random() * (Util.RANGE_BODIES_VELOCITY - 1) ) + 1) - (Util.RANGE_BODIES_VELOCITY / 2);
				int index_mass = Verify.getInt(0, 3);
				mass[i] = Util.MASSES[index_mass];
			}
			
			this.initBody(c, number);
		}
		
		/*
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
					  i++;
				  }
				  in.close();
				  
			}catch (Exception ex){
				System.err.println(ex); 
			}

			this.initBody(c, number);
		}
		*/
		
		public void initBody(Context c, int number){
			c.allbodies = new Body[number];
			double[][] data = this.getData();
			for(int i = 0; i < c.allbodies.length; i++){
				double mass = data[4][i];
				V2d pos = new V2d(data[0][i], data[1][i]);
				V2d vel = new V2d(data[2][i], data[3][i]);
				c.allbodies[i] = new Body(pos, vel, mass, i);
			}
		}
		
		public double[][] getData(){
			double[][] data = {position_x, position_y, velocity_x, velocity_y, mass};
			return data;
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
		
		private double dt;
	
		public BodyTask(Body[] all, int i, double delta_t){
			all_bodies = all;
			my_index = i;
			me = all_bodies[my_index];
			dt = delta_t;
		}
		
		public Body call() throws Exception {
			
			V2d force = new V2d(0,0);
			for (int i = 0; i < all_bodies.length; i++) {
				if (i != my_index) {
					force = force.sum(me.forceFrom(all_bodies[i]));
				}
			}
			
			me.move(force, dt);
			
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
				System.out.println("Print Bodies updated");
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
		gen.initBody(c, c.number);
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
		assert c.assertion_variable == (c.number+1);
	}

}
