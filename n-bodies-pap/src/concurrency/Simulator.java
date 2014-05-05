package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.*;

import entity.Body;
import support.Context;
import support.P2d;
import support.Util;
import support.V2d;

public class Simulator extends Thread {
	
	private boolean go;
	private boolean simulation;
	private Context context;
	private Semaphore sem;
	private boolean step;
	private Semaphore printed;
	Semaphore mutex_force = new Semaphore(1); 
	
	private static final int NTHREADS = Runtime.getRuntime().availableProcessors() + 1;
	private static final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);
	private List<Future<Body>> list = new ArrayList<Future<Body>>();
	private List<V2d> partial_force = new ArrayList<V2d>();
	private List<Thread> workers = new ArrayList<Thread>();
	
	public Simulator(Context c, Semaphore sem, Semaphore print) {
		super("Simulator");
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
					//loop();
					//loopV4();
					//loopV5();
					//loopV6();
					loopV7();
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
		log(" Sem: " + sem.availablePermits());
		try {
			this.printed.acquire();
			log(" Sem: " + sem.availablePermits());
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
		
		Body [] all_bodies_clone = context.allbodies.clone();
		double dt = context.dt;
		
		int n = NTHREADS/2;
		log("n " + n);
		int level = Math.round((float)(Math.log(n)/Math.log(2))) + 1;
		log("level " + level);
		double delta_t = context.dt;
		
		for (int i = 0; i < all_bodies_clone.length; i++) {
			//Body me = all_bodies_clone[i];
			//V2d force_dest = new V2d(0,0);
	     	SimulatorWorker master = new SimulatorWorker(all_bodies_clone, all_bodies_clone[i], 0, all_bodies_clone.length, level, delta_t);
	     	master.start();
	     	
	     	try {
	     		master.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//System.out.println("Body from workers: " + me);
			context.updateBody(all_bodies_clone[i]);
			//System.out.println("Body in context: " + context.allbodies[i]);
				
		}
		
		Util.total_iteration++;
		Util.last_iter_time = (System.nanoTime() - time)*1e-9;
		log(" Release Sem");
		this.sem.release();
		log(" Sem: " + sem.availablePermits());
		log(" Printed before: " + sem.availablePermits());
		try {
			this.printed.acquire();
			log(" Printed acquired: " + sem.availablePermits());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void loopV5(){
		/*
		 * - PER CIASCUN CORPO:
		 * - DIVIDO L'ARRAY DEI BODY E FACCIO CALCOLARE DAI WORKER LA FORZA TOTALE DELLA SOTTOPORZIONE DELL'ARRAY
		 * - SOMMO LE FORZE PARZIALI
		 * - MUOVO IL CORPO 
		 * - AGGIORNO IL CORPO NEL CONTEXT
		 */
		double time = System.nanoTime();
		
		Body [] all_bodies_clone = context.allbodies.clone();
		double dt = context.dt;
		
		int parts = all_bodies_clone.length/NTHREADS;
		
		for (int i = 0; i < all_bodies_clone.length; i++){
			for(int j = 0; j < NTHREADS; j++){
				if(j == NTHREADS-1){
					SimulatorWorker worker = new SimulatorWorker(all_bodies_clone, all_bodies_clone[i], j*parts, all_bodies_clone.length, partial_force, mutex_force);
					worker.start();
					workers.add(worker);
				}else{
					SimulatorWorker worker = new SimulatorWorker(all_bodies_clone, all_bodies_clone[i], j*parts, (j+1)*parts, partial_force, mutex_force);
					worker.start();
					workers.add(worker);
				}
			}
			for(Thread w : workers){
				try {
					w.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			workers.clear();
			V2d force_tot = new V2d(0,0);
			for(V2d f : partial_force)force_tot.sum(f);
			
			if(all_bodies_clone[i].getMassValue() != Util.SUN_MASS) all_bodies_clone[i].move(force_tot, dt);
			
			context.updateBody(all_bodies_clone[i]);
			partial_force.clear();
		}
		
				
		
		Util.total_iteration++;
		Util.last_iter_time = (System.nanoTime() - time);
		this.sem.release();
		log(" Sem: " + sem.availablePermits());
		try {
			this.printed.acquire();
			log(" Sem: " + sem.availablePermits());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void loopV6(){
		double time = System.nanoTime();
		
		Body [] all_bodies = context.allbodies;
		double dt = context.dt;
		
		int i=0;
		while(i<all_bodies.length){
			for(int j=0; j<NTHREADS; j++){
				Thread worker = new Thread(new BodyTaskV6(context, all_bodies, (i+j), dt));
				worker.start();
				workers.add(worker);
			}
			for(Thread w : workers)
				try {
					w.join();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			workers.clear();
			i+= NTHREADS;
		}		
		
		Util.total_iteration++;
		Util.last_iter_time = (System.nanoTime() - time);
		this.sem.release();
		log(" Sem: " + sem.availablePermits());
		try {
			this.printed.acquire();
			log(" Sem: " + sem.availablePermits());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void loopV7(){
		/*
		 * - PER CIASCUN CORPO:
		 * - DIVIDO L'ARRAY DEI BODY E FACCIO CALCOLARE DAI WORKER LA FORZA TOTALE DELLA SOTTOPORZIONE DELL'ARRAY
		 * - SOMMO LE FORZE PARZIALI
		 * - MUOVO IL CORPO 
		 * - AGGIORNO IL CORPO NEL CONTEXT
		 */
		double time = System.nanoTime();
		
		Body [] all_bodies_clone = context.allbodies.clone();
		double dt = context.dt;
		
		int parts = all_bodies_clone.length/NTHREADS;
		
		for (int i = 0; i < all_bodies_clone.length; i++){
			for(int j = 0; j < NTHREADS; j++){
				if(j == NTHREADS-1){
					Thread worker = new Thread(new BodyTaskV7(all_bodies_clone, all_bodies_clone[i], j*parts, all_bodies_clone.length, partial_force, mutex_force));
					worker.start();
					workers.add(worker);
				}else{
					Thread worker = new Thread(new BodyTaskV7(all_bodies_clone, all_bodies_clone[i], j*parts, (j+1)*parts, partial_force, mutex_force));
					worker.start();
					workers.add(worker);
				}
			}
			System.out.println("partial force: " + partial_force);
			V2d force_tot = new V2d(0,0);
			for(int k=0; k<partial_force.size(); k++) force_tot = force_tot.sum(partial_force.get(k));
			partial_force.clear();
			if(all_bodies_clone[i].getMassValue() != Util.SUN_MASS)all_bodies_clone[i].move(force_tot, dt);
			context.updateBody(all_bodies_clone[i]);
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
