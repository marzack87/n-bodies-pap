package concurrency;

import java.util.concurrent.Semaphore;

import support.Context;
import gui.VisualiserPanel;
import entity.*;
	
/**
 * Class Visualiser.
 * <p>
 * Class that extends Thread Java class.<br>
 * Entity / thread which has the task of displaying the set of bodies, their movements and their interactions. 
 * </p>
 * <p>Its behavior will vary according to the type of operation of the simulation:</p> 
 * <ul>
 *	<li>Display a continuous-time behavior of the simulation by clicking the Play button in the commands provided in the GUI</li>
 *  <li>Display a "snapshot" of a single state of the simulation (discrete-time behavior) by clicking the Step Mode button</li>
 * <ul>
 * 
 *
 * @author Richiard Casadei, Marco Zaccheroni
 * 
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
	 * @param cont 		The Controller entity with which the visualiser will communicate 
	 * @param sem		The event semaphore to notify the computation end 
	 * @param print		The event semaphore to wait the end of VisualiserPanel repain
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
     * <p>
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
    * <p>
    * It terminate the execution of run method.  
    */
    public void suicide(){
		simulation = false;
	}
    
    
    /**
     * Private method log.
     * <p>
     * Prints to the console a log of the activity of the Visualiser.
     * 
     * @param msg the message to be printed
     */
	private void log(String msg){
        System.out.println("[VISUALISER] "+msg);
    }
	
	/**
	 * Method setVisualiserPanel.
	 * <p>
	 * Set the VisualiserPanel controlled by Visualiser Thread
	 * 
	 * @param v	The VisualiserPanel
	 */
	public void setVisualiserPanel(VisualiserPanel v){
		this.v = v;
	}
	
	/**
	 * Method flushHistoryPosition.
	 * <p>
	 * Reset the older position array stored in the VisualiserPanel
	 */
	public void flushHistoryPositions(){
		v.reset();
	}
	

}
