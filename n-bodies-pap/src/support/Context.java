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
	public Body[] allbodies;
	public Boolean stop;
	public Dimension available_space;
	public Dimension visualiser_space;
	
	/**
	 * Class Context default constructor.
	 *
	 **/
    public Context(){
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int w = (int) screenSize.getWidth() - 50;
    	int h = (int) screenSize.getHeight() - 50;
    	available_space = new Dimension(w, h);
    	visualiser_space = new Dimension(w - 180, h - 30);
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
    /*
    public Body getBody(int pos){
    	// restituisce un singolo body data una posizione
    	return;
    }*/
    
    /**
     * Method getOtherBody.
     * It return all the value of the bodies except the body of the position specified.
     * 
     * @return
     */
    /*
    public Body getOtherBody(int mypos){
    	//dall'array o che altro mi faccio dare tutti i body
    	return;
    }*/
    
    /**
     * Private method print_body.
     * Prints all the value of the Body.
     * 
     */
    private void print_body(){
		System.out.println("Printing bodies data from context......");
		for(int i = 0; i<allbodies.length; i++){
			System.out.println(this.allbodies[i].getPosition() + " " + this.allbodies[i].getVelocity() + " " + this.allbodies[i].getMass() );
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