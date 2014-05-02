package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import entity.Body;
import support.Context;
import support.Util;
import support.V2d;

public class Simulator extends Thread {
	
	private boolean go;
	private boolean simulation;
	private Context context;
	private Semaphore sem;
	private boolean step;
	private Semaphore printed;
	
	private static final int NTHREADS = Runtime.getRuntime().availableProcessors() + 1;
	private static final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);
	private List<Future<Body>> list = new ArrayList<Future<Body>>();
	
	public Simulator(Context c, Semaphore sem, Semaphore print) {
		context = c;
		go = false;
		simulation = true;
		this.sem = sem;
		step = false;
		this.printed = print;
		
	}
	
	public void run(){
		log("I'm running..");
		log("Simulation started");
		Util.t_start = System.nanoTime();
		while (simulation){
			if (go || step){
					//double t0 = System.nanoTime();
					loop();
					//loopV4();
					//double t1 = System.nanoTime();
					//log("Task execution time: " + (t1-t0));
			}
		}
		log("I'm dead..");
	}
	
	public void play(){
		go = true;
	}
	
	public void pause(){
		go = false;
	}
	
	public void step(){
		this.pause();
		step = true;
		log("Step");
		step = false;
	}
	
	public void suicide(){
		go = simulation = false;
	}
	
	public boolean go(){
		return go;
	}
	
	private void loop(){
		/*
		 * - LEGGERE I DATI
		 * - CREARE I BODY TASK PASSANDOGLI I DATI
		 * - ASPETTIAMO CHE FINISCANO TORNANDOCI I LORO VALORI AGGIORNATI
		 * - AGGIORNIAMO I DATI
		 * - LI PASSIAMO AL VISUALIZZATORE
		 * - ATTENDIAMO CHE IL PAINT DEI CORPI SIA STATO ESEGUITO 
		 */
		double time = System.nanoTime();
		
		Body [] all_bodies = context.allbodies;
		double dt = context.dt;
		for (int i = 0; i < all_bodies.length; i++){
			Callable<Body> task = new BodyTask(all_bodies, i, dt);
			Future<Body> submit = exec.submit(task);
			//System.out.println(exec.toString());
			list.add(submit);
		}
		for (Future<Body> future : list){
			try {
				Body body = future.get();
				context.updateBody(body);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		list.clear();
		Util.total_iteration++;
		Util.last_iter_time = (System.nanoTime() - time);
		this.sem.release();
		try {
			this.printed.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void loopV4(){
		/*
		 * - MASTER&WORKER PATTERN
		 * - FACCIO TUTTO DENTRO RUN() SENZA CHIAMARE I METODI
		 * - POI SARA' IL MASTER AD AGGIORNARE IL BODY NEL CONTEST ED EVENTUALMENTE A GESTIRE LE COLLISIONI
		 * 	 RISCONTRATE DAI WORKER
		 */
		double time = System.nanoTime();
		
		Body [] all_bodies = context.allbodies.clone();
		double dt = context.dt;
		
		int n = NTHREADS/2;
		int level = Math.round((float)(Math.log(n)/Math.log(2))) + 1;
		double delta_t = context.dt;
		
		for (int i = 0; i < all_bodies.length; i++) {
			Body me = all_bodies[i];
			V2d force_dest = new V2d(0,0);
	     	SimulatorWorker worker1 = new SimulatorWorker(all_bodies, me, 0, all_bodies.length/2, level-1, force_dest, delta_t);
	     	SimulatorWorker worker2 = new SimulatorWorker(all_bodies, me, all_bodies.length/2, all_bodies.length, level-1, force_dest, delta_t);
	     	try {
	     		worker1.join();
	     		worker2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(me.getMassValue() != Util.SUN_MASS)me.move(force_dest, dt);
			/*
			 if(me.getMassValue() != Util.SUN_MASS){
			 
				if (me.collision){
					me.v = me.vel_after_collision;
					//System.out.println("Body " + index + " - vel_after_collision = " + vel_after_collision);
					V2d dp = me.v.mul(dt);
					me.p = me.p.sum(dp);
				} else {
					V2d a = force_dest.mul(1/me.mass);
					V2d dv = a.mul(dt);
					V2d dp = (me.v.sum(dv.mul(1/2))).mul(dt);
					me.p = me.p.sum(dp);
					me.v = me.v.sum(dv);
				}
				
				me.collision = false;
				me.vel_after_collision.x = 0;
				me.vel_after_collision.y = 0;	
			}
			*/
			
			context.updateBody(me);
				
		}
		
		Util.total_iteration++;
		Util.last_iter_time = (System.nanoTime() - time)*1e-9;
		this.sem.release();
		try {
			this.printed.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
     * Private method log.
     * Prints to the console a log of the activity of the Visualiser.
     * 
     * @param msg the message to be printed
     */
	private void log(String msg){
        System.out.println("[SIMULATOR] "+msg);
    }
	
}
