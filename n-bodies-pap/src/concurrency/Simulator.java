package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import entity.Body;
import concurrency.BodyTask;
import support.Context;
import support.Util;

/**
 * Class Simulator.
 * <p>
 * Class that extends Thread Java class.<br>
 * It represents the entity/thread which controls the behavior of the simulation reacting to the user inpunt.
 *  
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 *
 */

public class Simulator extends Thread {
	
	private boolean go;
	private boolean simulation;
	private Context context;
	private Semaphore sem;
	private int step;
	private Semaphore printed; 
	
	private static final int NTHREADS = Runtime.getRuntime().availableProcessors() + 1;
	private static final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);
	private List<Future<Body>> list = new ArrayList<Future<Body>>();
	private BodyTask[] bodytasks_array;
	
   /**
	* Class Simulator constructor.
	* 
	* @param c The context
	* @param sem The event semaphore to notify the computation end 
	* @param print The event semaphore to wait the end of VisualiserPanel repaint
	*/
	public Simulator(Context c, Semaphore sem, Semaphore print) {
		super("Simulator");
		context = c;
		go = false;
		simulation = true;
		this.sem = sem;
		step = 0;
		this.printed = print;
		bodytasks_array = new BodyTask[context.allbodies.length];
		for (int i = 0; i < context.allbodies.length; i++){
			bodytasks_array[i] = new BodyTask(context.allbodies, i, context.dt);
		}
	}
	
	/**
	 * Method go.
	 * <p>
	 * It returns the value of the simulation continuous-time mode   
	 * 
	 * @return go Boolean value
	 */
	public boolean go(){
		return go;
	}
	
	/**
     * Method run.
     * <p>
     * If one of the two condition(continuous mode or step-by-step mode) are satisfied it calls the private method loop.
     */
	public void run(){
		log("I'm running..");
		log("Simulation started");
		Util.t_start = System.nanoTime();
		while (simulation){
			if (go || step > 0){
					//double t0 = System.nanoTime();
					loop();
					if(step > 0) step--;
					//double t1 = System.nanoTime();
					//log("Task execution time: " + (t1-t0));
			}
		}
		log("I'm dead..");
	}
	
	/**
     * Private method loop.
     * The core of the simulation.
     * <p>
     * Following the master-worker pattern, the Simulator assigns to a ExecutorService pool(newFixedThreadPool(NTHREADS)) thread an appropriate BodyTask. 
     * After the ends of all tasks computation, the Simulator retrieves their results and update the context, 
     * then it reports to the Visualiser that the update is completed and waits for the termination of the VisualiserPanel repaint.
     */
	private void loop(){
		/*
		 * - LEGGERE I DATI
		 * - CREARE I BODY TASK PASSANDOGLI I DATI
		 * - ASPETTIAMO CHE FINISCANO TORNANDOCI I LORO VALORI AGGIORNATI
		 * - AGGIORNIAMO I DATI
		 * - SEGNALIAMO IL VISUALIZZATORE
		 * - ATTENDIAMO CHE IL PAINT DEI CORPI SIA STATO ESEGUITO 
		 */
		double time = System.nanoTime();
		double dt = context.dt;
		for (int i = 0; i < context.allbodies.length; i++){
			bodytasks_array[i].updateDt(dt);
			Callable<Body> task = bodytasks_array[i];
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
		//log(" Sem: " + sem.availablePermits());
		try {
			this.printed.acquire();
			//log(" Sem: " + sem.availablePermits());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method pause.
	 * <p>
	 * It freezes the simulation.  
	 */
	public void pause(){
		go = false;
	}
	
	/**
	 * Method play.
	 * <p>
	 * It starts the simulation in continuous-time mode.  
	 */
	public void play(){
		go = true;
	}
	
	/**
	 * Method step.
	 * <p>
	 * It permits to simulate the galaxy behavior one step at time.  
	 */
	public void step(){
		this.pause();
		step++;
		log("Step");
	}
	
	/**
	 * Method suicide.
	 * <p>
	 * It terminate the execution of run method.  
	 */
	public void suicide(){
		go = simulation = false;
	}
	
	/**
     * Private method log.
     * <p>
     * Prints to the console a log of the activity of the Visualiser.
     * 
     * @param msg The message to be printed
     */
	private void log(String msg){
        System.out.println("[SIMULATOR] "+msg);
    }
	
}
