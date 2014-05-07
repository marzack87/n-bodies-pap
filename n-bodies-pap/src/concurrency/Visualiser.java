package concurrency;

import java.util.concurrent.Semaphore;

import support.Context;
import gui.VisualiserPanel;
import entity.*;
	
/**
 * Class Visualiser.
 * Class that extends Thread Java class.
 * 
 * Entity / thread which has the task of displaying the set of bodies, their movements and their interactions. 
 * Its behavior will vary according to the type of operation of the simulation: 
 * 
 * - Display a continuous-time behavior of the simulation by clicking the Play button in the commands provided in the GUI 
 * - Display a "snapshot" of a single state of the simulation (discrete-time behavior) by clicking the Step Mode button
 * 
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 * 
 * @see suppor.Context
 * @see gui.VisualiserPanel
 * @see entity
 * 
 * @see Semaphore
 * @see Thread
 */

public class Visualiser extends Thread {
	    
    private Context cont;
    private VisualiserPanel v;
    private Semaphore sem;
    private Semaphore printed;
    private boolean simulation;
    
    /**
	 * Class Visualiser constructor.
	 * 
	 * @param panel the VisualiserPanel where all bodies are printed
	 * @param contr the Controller entity with which the visualiser will communicate 
	 * 
	 * @see gui.VisualiserPanel
	 * @see entity.Controller
	 **/
    public Visualiser(Context cont, Semaphore sem, Semaphore print){
    	super("Visualiser");
    	this.cont = cont;
    	this.sem = sem;
    	this.printed = print;
    	simulation = true;
    }
    
    /**
     * Method run.
     * 
     * It waits until the Simulator thread end one computation and takes the updated data from the context,
     * and it updates the graphics view calling the VisualiserPanel method updatePosition.
     */
    public void run(){
    	log("I'm running..");
    	while(simulation){ 
    		try {
				this.sem.acquire();
				//log(" Sem acquired: " + sem.availablePermits());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		
    		//System.out.println("# " + Util.total_iteration + " - time " + Util.last_iter_time + " FPS " + 1e9 / Util.last_iter_time);
        	
    		Body[] position = cont.allbodies;
    		v.updatePositions(position);
    		this.printed.release();
    		//log(" Printed released: " + sem.availablePermits());
    	}
    	log("I'm dead..");
    }
    
   /**
    * Method suicide.
    * 
    * It terminate the execution of run method.  
    */
    public void suicide(){
		simulation = false;
	}
    
    
    /**
     * Private method log.
     * 
     * Prints to the console a log of the activity of the Visualiser.
     * 
     * @param msg the message to be printed
     */
	private void log(String msg){
        System.out.println("[VISUALISER] "+msg);
    }
	
	/**
	 * Method setVisualiserPanel.
	 * 
	 * Set the VisualiserPanel controlled by Visualiser Thread
	 * 
	 * @param v	The VisualiserPanel
	 */
	public void setVisualiserPanel(VisualiserPanel v){
		this.v = v;
	}
	
	/**
	 * Method flushHistoryPosition.
	 * 
	 * Reset the older position array stored in the VisualiserPanel
	 */
	public void flushHistoryPositions(){
		v.reset();
	}
	

}
