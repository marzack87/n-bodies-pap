package entity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

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
	
	private int pos_range = 100;
	private int vel_range = 50;
	private int mas_range = 10;
	
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
	 * Method initFromFile.
	 * It recovers from the file the data necessary to the initialization of the bodies. 
	 * 
	 * @param The input file
	 */
	public void initFromFile(File f){
		try{
			
			  FileInputStream fstream = new FileInputStream(f);
			  
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  
			  if ((strLine = br.readLine()) != null){
				  number = Integer.valueOf(strLine); 
				  position_x = new double[number];
				  position_y = new double[number];
				  velocity_x = new double[number];
				  velocity_y = new double[number];
				  mass = new double[number];
			  } else {
				  //Il file e' vuoto, meglio lanciare una eccezione
			  }
			  int i = 0;
			  while ((strLine = br.readLine()) != null)   {
				  String[] values = strLine.split(" ");
				  position_x[i] = Double.valueOf(values[0]);
				  position_y[i] = Double.valueOf(values[1]);
				  velocity_x[i] = Double.valueOf(values[2]);
				  velocity_y[i] = Double.valueOf(values[3]);
				  mass[i] = Double.valueOf(values[4]);
				  i++;
			  }
			  //Close the input stream
			  in.close();
			  
		}catch (Exception e){
			  
		}
	}
	
	/**
	 * Method initWithRandonData.
	 * It generates the data for the initialization in a random way.
	 * 
	 * @param n - Number of Body
	 */
	public void initWithRandomData(int n){
		number = n;
		position_x = new double[number];
		position_y = new double[number];
		velocity_x = new double[number];
		velocity_y = new double[number];
		mass = new double[number];
		
		for (int i = 0; i < number; i++){
			position_x[i] = (Math.random() * (pos_range - 1) ) + 1;
			position_y[i] = (Math.random() * (pos_range - 1) ) + 1;
			velocity_x[i] = (Math.random() * (vel_range - 1) ) + 1;
			velocity_y[i] = (Math.random() * (vel_range - 1) ) + 1;
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