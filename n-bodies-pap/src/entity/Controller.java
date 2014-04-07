package entity;

import java.io.File;

import support.*;


/**
 * Class Controller. 
 * Represent the entity that initialize the universe and the bodies taking the necessary 
 * data from the entity Generator.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class Controller {
	 
	private int n;
	private Context context;
	private Generator gen; 
	
	/**
	 * Class Controller default constructor.
	 * 
	 */
	public Controller(Context cont){
		
		gen = new Generator(cont);
		context = cont;
		
	}
	
	/**
	 * Method initWithRandomData.
	 * Initialize the set of the Body taking the data from a Generator object that 
	 * generates them randomly.
	 */
	public void initWithRandomData(){
		
		gen.initWithRandomData(10);
		
	}
	
	/**
	 * Method genFromFile.
	 * Initialize the set of the Body taking the data from a Generator object 
	 * that collects them from a file.
	 */
	public void genFromFile(File myfile){
		
		gen.initFromFile(myfile);
		
	}
	
	/**
	 * Method startSimulation.
	 * Let the Simulation to start.
	 */
	public void startSimulation(){
		// qui parte tutto
		//this.print_body();
	}
	
	
	public Body[] getAllBodiesFromContext(){
		
		return context.allbodies;
	}
	
	/**
	 * Method print_body.
	 * Print all the informations about all the bodies presented in the allbodies array 
	 */
	private void print_body(){
		System.out.println("Fuck");
		for(int i = 0; i<n; i++){
			System.out.println(context.allbodies[i].getPosition() + " " + context.allbodies[i].getVelocity() + " " + context.allbodies[i].getMass() );
		}
	}
	

}
