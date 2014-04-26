package support;

import java.awt.Dimension;
import java.awt.Toolkit;

import entity.Body;

/**
 * Class Context. 
 * Represents the context in which our simulation will be based and 
 * contains all the resources needed to regulate the behavior of the system (Semaphore and Future array list) and 
 * the resources on which the worker will act.  
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class Context {

    // per rimettere allbodies privato dovremo implementare i metodi per la restituzione dei body 
	public Body[] allbodies, allbodies_copy;
	public Boolean stop;
	
	public double dt;
	
	/**
	 * Class Context default constructor.
	 *
	 **/
    public Context(){
    	
    	dt = 0.001;
    } 
    
    /**
     * Method updateBody.
     * It update the data of a single body present in the list of all bodies.
     * 
     * @param body
     */
    public void updateBody(Body body){
    	allbodies[body.getIndex()] = body;
    }
    
    /**
     * Method getAllBody.
     * It return all the value of the bodies.
     * 
     * @return
     */
    /*
    public Body getAllBody(){
    	//dall'array o che altro mi faccio dare tutti i body
    	return;
    }*/
    
    /**
     * Method getBody.
     * It update the data of a single body present in the list of all bodies.
     * 
     * @param pos
     * @return
     */
    
    public Body getBody(int pos){
    	return allbodies[pos];
    }
    
    /**
     * Method copyBodies.
     * It save a copy of allbodies array.
     */
    public void copyBodies(){
    	for(int i=0; i<allbodies.length; i++) Util.allbodies_copy[i] = allbodies[i];
    }
    
    /**
     * Method resetBodies.
     * It save a copy of allbodies array.
     */
    public void resetBodies(){
    	for(int i=0; i<allbodies.length; i++) allbodies[i] = Util.allbodies_copy[i];
    }
    
    /**
     * Private method print_body.
     * Prints all the value of the Body.
     * 
     */
   public void print_body(){
		System.out.println("Printing bodies data from context......");
		for(int i = 0; i<allbodies.length; i++){
			System.out.println("AllBodies: " + this.allbodies[i].getPosition() + " " + this.allbodies[i].getVelocity() + " " + this.allbodies[i].getMass() );
			System.out.println("Copy: " + this.allbodies_copy[i].getPosition() + " " + this.allbodies_copy[i].getVelocity() + " " + this.allbodies_copy[i].getMass() );

		}
	}
    
    /**
	 * Method getNumberOfBodies.
	 * It returns the number of bodies.
	 * 
	 * @return number Number of bodies
	 */
	public int getNumberOfBodies(){
		return allbodies.length;
	}
}