package entity;

import java.io.File;

import concurrency.Simulator;
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
	private Simulator simulator;
	
	/**
	 * Class Controller constructor.
	 * Initialize an object Generator and take a references of the context.
	 * 
	 * @param cont The context of the system
	 * 
	 * @see support.Context
	 */
	public Controller(Context cont){
		
		gen = new Generator(cont);
		context = cont;
		
		simulator = new Simulator(cont);
	}
	
	/**
	 * Method initWithRandomData.
	 * Initialize the set of the Body taking the data from a Generator object that 
	 * generates n (the parameter specified) bodies randomly.
	 * 
	 * @param n Number of Body
	 */
	public void initWithRandomData(int n){
		
		gen.initWithRandomData(n);
		
	}
	
	/**
	 * Method genFromFile.
	 * Initialize the set of the Body taking the data from a Generator object 
	 * that collects them from a file.
	 * 
	 * @param myfile The input file
	 * 
	 * @see File
	 */
	public void genFromFile(File myfile){
		
		File f = myfile;
		gen.initFromFile(f);
		
	}
	
	/**
	 * Method startSimulation.
	 * Let the Simulation to start.
	 */
	// chiamato dal play
	public void startSimulation(){
		simulator.start();
	}
	
	public void stopSimulation(){
		simulator.suicide();
	}
	
	public boolean SimulationIsRunning(){
		return simulator.isAlive();
	}
	
	public void play(){
		simulator.play();
	}
	
	public void pause(){
		simulator.pause();
	}
	
	public void step(){
		simulator.step();
	}
	
	/**
	 * Method getAllBodiesFromContext.
	 * It returns the array of the bodies from the context. 
	 * 
	 * @return allbodies Bodies array 
	 */
	public Body[] getAllBodiesFromContext(){
		
		return context.allbodies;
	}
	
	/**
	 * Method print_body.
	 * Print all the informations about all the bodies presented in the allbodies array 
	 */
	private void print_body(){
		System.out.println("Printing bodies data by Controller......");
		for(int i = 0; i<n; i++){
			System.out.println(context.allbodies[i].getPosition() + " " + context.allbodies[i].getVelocity() + " " + context.allbodies[i].getMass() );
		}
	}
	

}
