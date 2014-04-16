package concurrency;

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
	    
    private Controller contr;
    private VisualiserPanel v;
    
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
    public Visualiser(VisualiserPanel panel, Controller contr){
    	this.contr = contr;
    	v = panel;
    }
    
    public void run(){
    	while(true){ //condizione dei pulsanti
    		// wait tutti hanno fatto i loro calcoli
    		// lock dell'array dei bodies
    		Body[] position = contr.getAllBodiesFromContext();
    		// rilascio il lock
    		v.updatePositions(position);
    	}
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

}
