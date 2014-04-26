package concurrency;

import java.util.concurrent.Semaphore;

import support.Context;
import gui.VisualiserPanel;
import entity.*;

/**
 * Class Visualiser.
 * Entity / thread which has the task of displaying the set of bodies, their movements and their interactions. 
 * Its behavior will vary according to the type of operation of the simulation: 
 * - Display a continuous-time behavior of the simulation by clicking the Play button in the commands provided in the GUI 
 * - Display a "snapshot" of a single state of the simulation (discrete-time behavior) by clicking the Step Mode button
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */

	
public class Visualiser extends Thread {
	    
    private Context cont;
    private VisualiserPanel v;
    private Semaphore sem;
    //private Semaphore printed;
    private boolean simulation;
    
    /**
	 * Class Visualiser constructor.
	 * 
	 * @param panel the VisualiserPanel where all bodies are printed
	 * @param contr the Controller entity with which the visualiser will communicate 
	 * 
	 * @see gui.VisualiserPanel
	 * @see entity.Controller
	 *
	 **/
    public Visualiser(Context cont, Semaphore sem/*, Semaphore print*/){
    	this.cont = cont;
    	this.sem = sem;
    	//this.printed = print;
    	simulation = true;
    }
    
    public void run(){
    	log("I'm running..");
    	while(simulation){ 
    		try {
				this.sem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		Body[] position = cont.allbodies;
    		v.updatePositions(position);
    		//this.printed.release();
    	}
    	log("I'm dying..");
    }
    
    public void suicide(){
		simulation = false;
	}
    
    /**
     * Private method log.
     * Prints to the console a log of the activity of the Visualiser.
     * 
     * @param msg the message to be printed
     */
	private void log(String msg){
        System.out.println("[VISUALISER] "+msg);
    }
	
	public void setVisualiserPanel(VisualiserPanel v){
		this.v = v;
	}
	
	public void flushHistoryPositions(){
		v.reset();
	}
	

}
