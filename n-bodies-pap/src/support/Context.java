package support;

import entity.Body;

/**
 * Class Context.
 * <p> 
 * Represents the context in which our simulation will be based and 
 * contains all the resources needed to regulate the behavior of the system (Semaphore and Future array list) and 
 * the resources on which the worker will act.  
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */

public class Context {
 
	public Body[] allbodies, allbodies_copy;
	public Boolean stop;
	
	public double dt;
	
	/**
	 * Class Context default constructor.
	 *
	 **/
    public Context(){
    	dt = Util.DEFAULT_DT;
    } 
    
    /**
     * Method copyBodies.
     * <p>
     * It save a copy of all bodies array.
     */
    public void copyBodies(){
    	for(int i=0; i<allbodies.length; i++) allbodies_copy[i] = allbodies[i].copy();
    }
    
    /**
     * Method getBody.
     * <p>
     * It returns a single body present specifying the index of the all bodies list. 
     * 
     * @param pos
     * @return allbodies[pos]	The Body returned 
     */ 
    public Body getBody(int pos){
    	return allbodies[pos];
    }
    
    /**
	 * Method getNumberOfBodies.
	 * <p>
	 * It returns the number of bodies.
	 * 
	 * @return number Number of bodies
	 */
	public int getNumberOfBodies(){
		return allbodies.length;
	}
   
	/**
     * Private method print_body.
     * <p>
     * Prints all the value of the Body.
     */
   public void print_body(){
		System.out.println("Printing bodies data from context......");
		for(int i = 0; i<allbodies.length; i++){
			System.out.println("AllBodies: " + this.allbodies[i].getPosition() + " " + this.allbodies[i].getVelocity() + " " + this.allbodies[i].getMass() );
			System.out.println("Copy: " + this.allbodies_copy[i].getPosition() + " " + this.allbodies_copy[i].getVelocity() + " " + this.allbodies_copy[i].getMass() );

		}
	}
	
    /**
     * Method resetBodies.
     * <p>
     * It reset the all bodies array to the initial condition.
     */
    public void resetBodies(){
    	for(int i=0; i<allbodies.length; i++) allbodies[i] = allbodies_copy[i].copy();
    }
   
   /**
    * Method synchronized updateBody.
    * <p>
    * It update the data of a single body present in the list of all bodies.
    * 
    * @param body	The Body updated that it will be inserted in the all bodies list.
    */
   public synchronized void updateBody(Body body){
   	allbodies[body.getIndex()] = body;
   }   
   
}