package entity;

import gui.VisualiserPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.concurrent.Semaphore;

import concurrency.Simulator;
import concurrency.Visualiser;
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
	private Visualiser visualiser;
	
	public boolean tracks;
	
	Semaphore sem = new Semaphore(0); 
	
	// emerald
	public final Color one = new Color(0x00CD7B);
	// peter river
	public final Color two = new Color(0x48A3DA);
	// amethyst
	public final Color three = new Color(0x9F57B1);
	// wet asphalt
	public final Color four = new Color(0x34495C);
	// alizarin
	public final Color five = new Color(0xEB4942);
	// sun flower
	public final Color sun = new Color(0xF1C442);
	
	public final Color dark_one = new Color(0x009D63);
	public final Color dark_two = new Color(0x20719D);
	public final Color dark_three = new Color(0x7D3F84);
	public final Color dark_four = new Color(0x253542);
	public final Color dark_five = new Color(0xA72C2E);
	
	public final Color light_one = new Color(0x00FCA2);
	public final Color light_two = new Color(0x39B5FA);
	public final Color light_three = new Color(0xEC7CF8);
	public final Color light_four = new Color(0x6A91B1);
	public final Color light_five = new Color(0xFF8982);
	
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
		
		simulator = new Simulator(cont, sem);
		visualiser = new Visualiser(cont, sem);
		
		tracks = true;
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
		visualiser.start();
		simulator.start();
	}
	
	public void stopSimulation(){
		simulator.suicide();
		sem.release();
		visualiser.suicide();
	}
	
	public boolean SimulationIsAlive(){
		return simulator.isAlive();
	}
	
	public boolean SimulationIsRunning(){
		return simulator.go();
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
	
	public void setUpVisualiser(VisualiserPanel v){
		this.visualiser.setVisualiserPanel(v);
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
	
	public Dimension getVisualizerSpace(){
		return context.visualiser_space;
	}

	public Dimension getAvailableSpace(){
		return context.available_space;
	}
	
	public void setDeltaT(double dt){
		context.dt = dt;
	}
	
}
