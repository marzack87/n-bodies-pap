package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


import entity.Body;
import support.Context;

public class Simulator extends Thread {
	
	private boolean go;
	private boolean simulation;
	private ExecutorService executor;
	private Context context;
	
	private static final int NTHREADS = Runtime.getRuntime().availableProcessors() + 1;
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
	private List<Future<Body>> list = new ArrayList<Future<Body>>();
	
	public Simulator(Context c) {
		context = c;
		go = false;
		simulation = true;
	}
	
	public void run(){
		// qui parte tutto
		
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
	
	private void loop(){
		/*
		 * - LEGGERE I DATI
		 * - CREARE I ÒBODY TASKÓ PASSANDOGLI I DATI
		 * - ASPETTIAMO CHE FINISCANO TORNANDOCI I LORO VALORI AGGIORNATI
		 * - AGGIORNIAMO I DATI
		 * - LI PASSIAMO AL VISUALIZZATORE 
		 */
		Body [] all_bodies = context.allbodies;
		for (int i = 0; i < all_bodies.length; i++){
			Callable<Body> task = new BodyTask(all_bodies, i);
			Future<Body> submit = executor.submit(task);
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
		// bisogna notificare il visualizzatore
	}
	
}
