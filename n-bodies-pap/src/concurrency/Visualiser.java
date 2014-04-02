package concurrency;

import gui.VisualiserPanel;
import support.Context;

	
public class Visualiser extends Thread {
	    
    private Context context;
    
    public Visualiser(VisualiserPanel panel, Context cont){
    	context = cont;
    }
    
    public void run(){
    	// rappresentazione dello stato iniziale.
    	// attesa dello start
    	// attivita in concorrenza ai thread body per l'accesso alle risorse contenute nel context
    	
    	// comportamento scritto nel file ToDo - da verificare -
    }
    
    
    
    
    // DA A.RICCI
    //private VisualiserFrame frame;
	    
    /*public Visualiser(Context context){
    	stop = false;
	    this.context = context ;
	    frame = new VisualiserFrame(context);
	    frame.show();
   }
	    
    public void run(){
        while (!stop) {
        	frame.updatePosition(context.getPositions());
	        //log("update pos");
	        try {
                Thread.sleep(20);     
            } catch (Exception ex){
	        }
	    }
    }*/
	    
	private void log(String msg){
        System.out.println("[VISUALISER] "+msg);
    }

}
