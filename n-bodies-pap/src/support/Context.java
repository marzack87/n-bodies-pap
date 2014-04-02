package support;

import java.util.*;

import entity.Body;


public class Context {

    // dove salveremo i body, non sono sicuro che dovra rimanere pubblico
	public Body[] allbodies;
	public Boolean stop;
	
    
    public Context(){
    	
    } 
    
    /**
     * Metod updateBody.
     * It update the data of a single body present in the list of all bodies.
     * 
     * @param bod
     * @param pos
     */
    public void updateBody(Body bod, int pos){
    	
    }
    
    /**
     * Metod getAllBody.
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
     * Metod getBody.
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
     * Metod getOtherBody.
     * It return all the value of the bodies except the body of the position specified.
     * 
     * @return
     */
    /*
    public Body getOtherBody(int mypos){
    	//dall'array o che altro mi faccio dare tutti i body
    	return;
    }*/
    
    //da rimettere privato
    public void print_body(){
		System.out.println("Printing bodies data from context......");
		for(int i = 0; i<allbodies.length; i++){
			System.out.println(this.allbodies[i].getPosition() + " " + this.allbodies[i].getVelocity() + " " + this.allbodies[i].getMass() );
		}
	}
}