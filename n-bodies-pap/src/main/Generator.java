package main;

/**
 * Class Generator. 
 * Represent the entity that takes care to recover the data from a file or to generate them in a random way.
 * If the mode choose to star the simulation is taking the data from a file, an object of this class recovered from the file these data: 
 * the number of planets (bodies) to create, in the below columns the Cartesian coordinates x and y of the position and the speed of every bodies and their relative masses.
 * Otherwise the object to initialize the simulation creates all the data in a random way.
 * The data recovered/created are passed to the Controller object through a matrix. 
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class Generator {
	
/* Si occupa di prendere i dati da file oppure
 * di generarli automaticamente in maniera casuale
 * 
 * DATI NECESSARI:
 * position (x, y)
 * velocity (x, y)
 * mass m
 */
	private int number;
	
	private int pos_range = 99;
	private int vel_range = 99;
	private int mas_range = 99;
	
	private double[] position_x;
	private double[] position_y;
	private double[] velocity_x;
	private double[] velocity_y;
	private double[] mass;
	
	/**
	 * Class Generator constructor.
	 */
	public Generator(){
		 
	}
	
	/**
	 * Method getDataFromFile.
	 * It recovers from the file the data necessary to the initialization of the bodies. 
	 */
	public void getDataFromFile(){
		//primo valore: NUMERO CORPI
		
		//poi tabella dei dati divisi da spazi
		// xxx xxx xxx xxx xxx
	}
	
	/**
	 * Method generateRandonData.
	 * It generates the data for the initialization in a random way.
	 */
	public void generateRandomData(int n){
		number = n;
		position_x = new double[number];
		position_y = new double[number];
		velocity_x = new double[number];
		velocity_y = new double[number];
		mass = new double[number];
		
		for (int i = 0; i < number; i++){
			//Random r = new Random();
			position_x[i] = (Math.random() * pos_range) + 1;
			position_y[i] = (Math.random() * pos_range) + 1;
			velocity_x[i] = (Math.random() * vel_range) + 1;
			velocity_y[i] = (Math.random() * vel_range) + 1;
			mass[i] = (Math.random() * mas_range) + 1;
		}
	}
	
	/**
	 * Method getNumberOfBodies.
	 * It returns the number of bodies.
	 * 
	 * @return number - Integer
	 */
	public int getNumberOfBodies(){
		return number;
	}
	
	/**
	 * Method getData.
	 * Create the matrix which contains the data of the bodies.
	 * Each line contains the Cartesian coordinates x and y of the position and the speed of every bodies and their relative masses
	 * 
	 * @return data - Data matrix
	 */
	public double[][] getData() {
		double[][] data = {position_x, position_y, velocity_x, velocity_y, mass};
		return data;
	}
	
}
