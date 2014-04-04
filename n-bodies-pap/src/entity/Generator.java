package entity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import support.*;

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
	
/* It take care of the file's data or it create the data randomly
 * 
 * Data needed:
 * position (x, y)
 * velocity (x, y)
 * mass m
 */
	private int number;
	
	private int posx_range = 800 - 5;
	private int posy_range = 600 - 5;
	private int vel_range = 50;
	private int mas_range = 20;
	
	private double[] position_x;
	private double[] position_y;
	private double[] velocity_x;
	private double[] velocity_y;
	private double[] mass;
	
	private Context context;
	
	/**
	 * Class Generator constructor.
	 */
	public Generator(Context cont){
		 
		context = cont;
	
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
		
		// dovremmo inizializzare le risorse di Context
		this.initBody();
		this.print_body();
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
			position_x[i] = (Math.random() * (posx_range - 1) ) + 1;
			position_y[i] = (Math.random() * (posy_range - 1) ) + 1;
			velocity_x[i] = (Math.random() * (vel_range - 1) ) + 1;
			velocity_y[i] = (Math.random() * (vel_range - 1) ) + 1;
			mass[i] = (Math.random() * mas_range) + 5;
		}
		
		// dovremmo inizializzare le risorse di Context
		this.initBody();
		this.print_body();
	}
	
	/**
	 * Method initBody.
	 * It creates and updates the array list of the Body Objects with the data taken from the initialization(both choice methods: random and from file).
	 */
	public void initBody(){
		context.allbodies = new Body[number];
		double[][] data = this.getData();
			for(int i = 0; i < number; i++){
				double mass = data[4][i];
				double[] position = {data[0][i], data[1][i]};
				double[] velocity = {data[2][i], data[3][i]};
				Vector pos = new Vector(position);
				Vector vel = new Vector(velocity);
				context.allbodies[i] = new Body(pos, vel, mass);
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
	 * It creates the matrix which contains the data of the bodies.
	 * Each line contains the Cartesian coordinates x and y of the position and the speed of every bodies and their relative masses
	 * 
	 * @return data - Data matrix
	 */
	public double[][] getData() {
		double[][] data = {position_x, position_y, velocity_x, velocity_y, mass};
		return data;
	}
	
	/**
	 * Method print_body.
	 * Print all the informations about all the bodies presented in the allbodies array 
	 */
	private void print_body(){
		System.out.println("Printing bodies data......");
		for(int i = 0; i<number; i++){
			System.out.println(context.allbodies[i].getPosition() + " " + context.allbodies[i].getVelocity() + " " + context.allbodies[i].getMass() );
		}
	}
	
	
}
