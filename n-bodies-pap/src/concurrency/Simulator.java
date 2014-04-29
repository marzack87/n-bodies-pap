package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import entity.Body;
import support.Context;
import support.Util;

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
					double t0 = System.nanoTime();
					//loop();
					loopV2();
					double t1 = System.nanoTime();
					log("Task execution time: " + (t1-t0));
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
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		list.clear();
		Util.total_iteration++;
		Util.last_iter_time = System.nanoTime() - time;
		this.sem.release();
		try {
			this.printed.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void loopV2(){
		/*
		 * - LEGGERE I DATI
		 * - CREARE I BODY TASK PASSANDOGLI I DATI
		 * - ASPETTIAMO CHE FINISCANO TORNANDOCI I LORO VALORI AGGIORNATI
		 * - AGGIORNIAMO I DATI
		 * - LI PASSIAMO AL VISUALIZZATORE 
		 */
		double time = System.nanoTime();
		
		Body [] all_bodies = context.allbodies.clone();
		double dt = context.dt;
		for (int i = 0; i < all_bodies.length; i++){
			 exec.execute(new BodyTaskV2(context, all_bodies, i, dt));
			//System.out.println(exec.toString());
		}
		try {
			exec.awaitTermination(600, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		}
		Util.total_iteration++;
		Util.last_iter_time = System.nanoTime() - time;
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
