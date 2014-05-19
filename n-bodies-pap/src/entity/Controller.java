package entity;

import gui.VisualiserPanel;

import java.io.File;
import java.util.concurrent.Semaphore;

import concurrency.Simulator;
import concurrency.Visualiser;
import support.*;

/**
 * Class Controller.
 * <p> 
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
	private Visualiser visualiser;
	
	public boolean tracks;
	public boolean velocity;
	
	Semaphore sem = new Semaphore(0); 
	Semaphore print = new Semaphore(0);
	
	/**
	 * Class Controller constructor.
	 * <p>
	 * Initialize an object Generator and take a references of the context.
	 * 
	 * @param cont The context of the system
	 */
	public Controller(Context cont){
		
		gen = new Generator(cont);
		context = cont;
		
		tracks = false;
		velocity = false;
	}
	
	/**
	 * Method initThreads.
	 * <p>
	 * It creates the two thread Simulator and Visualiser.
	 */
	private void initThreads(){
		simulator = new Simulator(context, sem, print);
		visualiser = new Visualiser(context, sem, print);
	}
	
	/**
	 * Method initWithRandomData.
	 * <p>
	 * Initialize the set of the Body taking the data from a Generator object that 
	 * generates n (the parameter specified) bodies randomly.
	 * 
	 * @param n Number of Body
	 */
	public void initWithRandomData(int n, boolean sun){
		
		gen.initWithRandomData(n, sun);
		initThreads();
		
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
	 * Method genFromFile.
	 * <p>
	 * Initialize the set of the Body taking the data from a Generator object 
	 * that collects them from a file.
	 * 
	 * @param myfile The input file
	 */
	public void genFromFile(File myfile){
		
		File f = myfile;
		gen.initFromFile(f);
		initThreads();
		
	}
	
	/**
	 * Method play.
	 * <p>
	 * Call the  method play of the Simulator class.
	 */
	public void play(){
		simulator.play();
	}
	
	/**
	 * Method pause.
	 * <p>
	 * Call the  method pause of the Simulator class.
	 */
	public void pause(){
		simulator.pause();
	}
	
	/**
	 * Method print_body.
	 * <p>
	 * Print all the informations about all the bodies presented in the allbodies array 
	 */
	public void print_body(){
		System.out.println("Printing bodies data by Controller......");
		for(int i = 0; i<n; i++){
			System.out.println(context.allbodies[i].getPosition() + " " + context.allbodies[i].getVelocity() + " " + context.allbodies[i].getMass() );
		}
	}
	
	/**
	 * Method reset.
	 * <p>
	 * It reset the status of the simulation and take it back to the origin.
	 */
	public void reset(){
		context.resetBodies();
		visualiser.flushHistoryPositions();
		//context.print_body();
		// The Visualiser thread must be print the new situation
		sem.release();
	}
	
	/**
	 * Method setUpVisualiser.
	 * <p>
	 * Call the  method play of the Simulator class.
	 * 
	 * @param v The VisualiserPanel setted to the Visualiser thread
	 */
	public void setUpVisualiser(VisualiserPanel v){
		this.visualiser.setVisualiserPanel(v);
	}
	
	/**
	 * Method setDeltaT
	 * <p>
	 * It change the value of the time instant
	 * 
	 * @param dt The  time instant
	 */
	public void setDeltaT(double dt){
		context.dt = dt;
	}
	
	/**
	 * Method SimulationIsAlive.
	 * <p>
	 * Check if the Simulator thread is alive.
	 */
	public boolean SimulationIsAlive(){
		return simulator.isAlive();
	}
	
	/**
	 * Method SimulationIsRunning.
	 * <p>
	 * Check if the Simulator thread is runnig.
	 */
	public boolean SimulationIsRunning(){
		return simulator.go();
	}
	
	/**
	 * Method play.
	 * <p>
	 * Call the  method step of the Simulator class.
	 */
	public void step(){
		simulator.step();
	}
	
	/**
	 * Method startSimulation.
	 * <p>
	 * Let the Simulation to start.
	 */
	public void startSimulation(){
		visualiser.start();
		simulator.start();
	}
	
	/**
	 * Method stopSimulation.
	 * <p>
	 * It terminates the Simulation.
	 */
	public void stopSimulation(){
		simulator.suicide();
		sem.release();
		visualiser.suicide();
	}
	
	
}
