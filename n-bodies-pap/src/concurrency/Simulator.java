package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


import entity.Body;
import support.Context;

public class Simulator extends Thread {
	
	private boolean go;
	private boolean simulation;
	private Context context;
	private Semaphore sem;
	
	private static final int NTHREADS = Runtime.getRuntime().availableProcessors() + 1;
	private static final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);
	private List<Future<Body>> list = new ArrayList<Future<Body>>();
	
	public Simulator(Context c, Semaphore sem) {
		context = c;
		go = false;
		simulation = true;
		this.sem = sem;
	}
	
	public void run(){
		while (simulation){
			while(go){
				loop();
			}
		}
	}
	
	public void play(){
		go = true;
	}
	
	public void pause(){
		go = false;
	}
	
	public void step(){
		loop();
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
		Body [] all_bodies = context.allbodies;
		for (int i = 0; i < all_bodies.length; i++){
			Callable<Body> task = new BodyTask(all_bodies, i);
			Future<Body> submit = exec.submit(task);
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
		this.sem.release();
	}
	
}
