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
	
	/**
	 * Class Controller constructor.
	 * 
	 */
	public Controller(Context cont){
		
		context = cont;
		
	}
	
	/**
	 * Method initAll.
	 * Initialize the set of the Body taking the data from a Generator object that 
	 * generates them randomly.
	 */
	public void initAll(){
		
		
		/*Generator gen = new Generator();
		
		//qui bisogna gestire la scelta di come inizializzare il sistema (Random o da file)
		gen.initWithRandomData(10);
		
		
		//Inizializzazione degli n Body
		n = gen.getNumberOfBodies();
		allbodies = new Body[n];
		double[][] data = gen.getData();
		for(int i = 0; i < n; i++){
			double mass = data[4][i];
			double[] position = {data[0][i], data[1][i]};
			double[] velocity = {data[2][i], data[3][i]};
			Vector pos = new Vector(position);
			Vector vel = new Vector(velocity);
			allbodies[i] = new Body(pos, vel, mass);
		}
		
		//inizializzazione del Sistema
		// ...
		*/
	}
	
	/**
	 * Method initAllWithFile.
	 * Initialize the set of the Body taking the data from a Generator object 
	 * that collects them from a file.
	 */
	public void initAllWithFile(File myfile){
		
		/*File file = myfile;
		Generator gen = new Generator();
		gen.initFromFile(file);
		
		//Inizializzazione degli n Body
		n = gen.getNumberOfBodies();
		allbodies = new Body[n];
		double[][] data = gen.getData();
		for(int i = 0; i < n; i++){
			double mass = data[4][i];
			double[] position = {data[0][i], data[1][i]};
			double[] velocity = {data[2][i], data[3][i]};
			Vector pos = new Vector(position);
			Vector vel = new Vector(velocity);
			allbodies[i] = new Body(pos, vel, mass);
		}
		
		//inizializzazione del Sistema
		// ...
		 * 
		 */
	}

	
	/**
	 * Method startSimulation.
	 * Let the Simulation to start.
	 */
	public void startSimulation(){
		// qui parte tutto
		//this.print_body();
	}
	
	/**
	 * Method print_body.
	 * Print all the informations about all the bodies presented in the allbodies array 
	 */
	/*private void print_body(){
		System.out.println("Fuck");
		for(int i = 0; i<n; i++){
			System.out.println(allbodies[i].getPosition() + " " + allbodies[i].getVelocity() + " " + allbodies[i].getMass() );
		}
	}*/
	

}
