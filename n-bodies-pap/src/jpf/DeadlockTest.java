package jpf;

/**
 * Class DeadlockTest.
 * <p>
 * Class test to verify that in the system a deadlock situation in all the possible scenarios envisaged is not present.<br>
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import concurrency.BodyTask;
import support.Util;
import support.V2d;
import entity.Body;

public class DeadlockTest {
	
	static class Context{
		public Body[] allbodies;
		public double dt;
		
		public Context(){
			dt = 0.01;
			allbodies = new Body[400];
		}
		
		public synchronized void updateBody(Body body){
	   		allbodies[body.getIndex()] = body;
		}
		
		public void print_body() throws Exception{
			System.out.println("Printing bodies data from context......");
			for(int i = 0; i<allbodies.length; i++){
				System.out.println("AllBodies: " + this.allbodies[i].getPosition() + " " + this.allbodies[i].getVelocity() + " " + this.allbodies[i].getMass() );
			}
		}
	}
	
	static class Generator{
		
		private Context c;
		
		private double[] position_x;
		private double[] position_y;
		private double[] velocity_x;
		private double[] velocity_y;
		private double[] mass;
		
		public Generator(Context c){
			this.c = c;
				initRandom(400);
		}
		
		// inizializzo l'array allbodies in modo random
		public void initRandom(int number){
			position_x = new double[number];
			position_y = new double[number];
			velocity_x = new double[number];
			velocity_y = new double[number];
			mass = new double[number];
			
			for (int i = 0; i < number; i++){
				position_x[i] = (Math.random() * ((Util.VisualiserAvailableSpace().width - 5)/Util.scaleFact - 1) ) + 1;
				position_y[i] = (Math.random() * ((Util.VisualiserAvailableSpace().height - 5)/Util.scaleFact - 1) ) + 1;
				velocity_x[i] = ((Math.random() * (Util.RANGE_BODIES_VELOCITY - 1) ) + 1) - (Util.RANGE_BODIES_VELOCITY / 2);
				velocity_y[i] = ((Math.random() * (Util.RANGE_BODIES_VELOCITY - 1) ) + 1) - (Util.RANGE_BODIES_VELOCITY / 2);
				int index_mass = (int)Math.round(Math.random() * 3);
				mass[i] = Util.MASSES[index_mass];
			}
			
			this.initBody(c, number);
		}
		
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
		
		public Simulator(Context c, Semaphore s, Semaphore p){
			this.c = c;
			sem = s;
			printed = p;
		}
		
		// prende tutto fai i calcoli senza pulsanti ecc
		// segnala il visualizzatore
		// attesa della print
		public void run(){
			
			Body [] all_bodies = c.allbodies;
			double dt = c.dt;
			for (int i = 0; i < all_bodies.length; i++){
				Callable<Body> task = new BodyTask(all_bodies, i, dt);
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
			System.out.println("Simulator dead");
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
		// faccio una prima stampa dei corpi
		// metto in attesa
		// stampo nuovamente
		// segnalo
			System.out.println("Print Bodies");
			try {
				savefile("AllBodiesJPF.txt");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				this.sem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Print Bodies updated");
			try {
				savefile("AllBodiesUpdatedJPF.txt");
			} catch (Exception e) {
				e.printStackTrace();
			}
    		this.printed.release();
    		System.out.println("Visualiser dead");
		}
		
		private void savefile(String s) throws Exception{
			try{
				// Opening of a new file "Stats".
				FileWriter f = new FileWriter(s);
				PrintWriter out = new PrintWriter(f);
				out.println("--------------- N-Bodies Simulation ---------------");
				out.println("");
				out.println("Author:");
				out.println("Richiard Casadei(Sith) & Marco Zaccheroni(Jedi)");
				out.println("");
				out.println(" - Bodies and Galaxy info -");
				for(int i = 0; i<c.allbodies.length; i++){
					out.println("Body: " + c.allbodies[i].getIndex() + " " + c.allbodies[i].getPosition() + " " + c.allbodies[i].getVelocity() + " " + c.allbodies[i].getMass() );
				}
				out.close();
			}catch (Exception ex) { System.err.println(ex); }
		}
	}

	public static void main(String[] args) {
		
		// inizializzo il generatore ed il context
		Context c = new Context();
		Generator gen = new Generator(c);
		
		Semaphore sem = new Semaphore(0);
		Semaphore printed = new Semaphore(0);
		
		// creo i due thread
		Simulator sim = new Simulator(c, sem, printed);
		Visualiser vis = new Visualiser(c, sem, printed);
		// li faccio partire
		sim.start();
		vis.start();

	}

}
