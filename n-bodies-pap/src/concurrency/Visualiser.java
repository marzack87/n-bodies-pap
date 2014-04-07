package concurrency;

import gui.VisualiserPanel;
import entity.*;

	
public class Visualiser extends Thread {
	    
    private Controller contr;
    private VisualiserPanel v;
    
    /**
	 * Class Visualiser default constructor.
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
	    
	private void log(String msg){
        System.out.println("[VISUALISER] "+msg);
    }

}
