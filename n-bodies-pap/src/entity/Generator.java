package entity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import support.*;

/**
 * Class Generator. 
 * <p>
 * Represent the entity that takes care to recover the data from a file or to generate them in a random way.<br>
 * If the mode choose to star the simulation is taking the data from a file, an object of this class recovered from the file these data:<br> 
 * the number of planets (bodies) to create, in the below columns the Cartesian coordinates x and y of the position and the speed of every bodies and their relative masses.<br>
 * Otherwise the object to initialize the simulation creates all the data in a random way.><br>
 * The data recovered/created are stored in a matrix used to initialize the context. 
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
	
	private double posx_range;
	private double posy_range;
	private double vel_range;
	
	private double[] position_x;
	private double[] position_y;
	private double[] velocity_x;
	private double[] velocity_y;
	private double[] mass;
	
	private Context context;
	
	/**
	 * Class Generator constructor.
	 * <p>
	 * It takes a reference of the system context
	 * 
	 * @param cont The system context
	 */
	public Generator(Context cont){
		 
		context = cont;
		
		posx_range = (Util.VisualiserAvailableSpace().width - 5)/Util.scaleFact;
		posy_range = (Util.VisualiserAvailableSpace().height - 5)/Util.scaleFact;
		
		vel_range = Util.RANGE_BODIES_VELOCITY;
		
	}
	 
	/**
	 * Method initFromFile.
	 * <p>
	 * It recovers from the file the data necessary to the initialization of the bodies. 
	 * 
	 * @param f The input file
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
				  Util.n_bodies = number;
				  position_x = new double[number];
				  position_y = new double[number];
				  velocity_x = new double[number];
				  velocity_y = new double[number];
				  mass = new double[number];
			  } else {
				  System.err.println("File is empty");
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
			  
		}catch (Exception ex){
			System.err.println(ex); 
		}
		
		// Initialize the context
		this.initBody(false);
		//this.print_body();
	}
	
	/**
	 * Method initWithRandonData.
	 * <p>
	 * It generates the data for the initialization in a random way.
	 * 
	 * @param n Number of Body
	 */
	public void initWithRandomData(int n, boolean sun){
		number = n;
		Util.n_bodies = number;
		position_x = new double[number];
		position_y = new double[number];
		velocity_x = new double[number];
		velocity_y = new double[number];
		mass = new double[number];
		
		for (int i = 0; i < number; i++){
			position_x[i] = (Math.random() * (posx_range - 1) ) + 1;
			position_y[i] = (Math.random() * (posy_range - 1) ) + 1;
			velocity_x[i] = ((Math.random() * (vel_range - 1) ) + 1) - (vel_range / 2);
			velocity_y[i] = ((Math.random() * (vel_range - 1) ) + 1) - (vel_range / 2);
			int index_mass = (int)Math.round(Math.random() * 3);
			//System.out.println("mass index: " + index_mass);
			mass[i] = Util.MASSES[index_mass];
			//System.out.println("mass: " + mass[i]);
		}
		
		// Initialize the context
		this.initBody(sun);
		//this.print_body();
	}
	
	/**
	 * Method initBody.
	 * <p>
	 * It creates and updates the array list of the Body Objects with the data taken from the initialization(both choice methods: random and from file).
	 */
	public void initBody(boolean sun){
		context.allbodies = new Body[number];
		context.allbodies_backup = new Body[number];
		double[][] data = this.getData();
		for(int i = 0; i < number; i++){
			double mass = data[4][i];
			V2d pos = new V2d(data[0][i], data[1][i]);
			V2d vel = new V2d(data[2][i], data[3][i]);
			context.allbodies[i] = new Body(pos, vel, mass, i);
		}
		if(sun){
			double mass_1 = Util.SUN_MASS;
			V2d pos_1 = new V2d(Util.VisualiserAvailableSpace().getWidth()/(Util.scaleFact*2), Util.VisualiserAvailableSpace().getHeight()/(Util.scaleFact*2));
			V2d vel_1 = new V2d(0, 0);
			context.allbodies[number-1] = new Body(pos_1, vel_1, mass_1, number-1);
		}
		context.copyBodies();
		//context.print_body();
	}
	
	/**
	 * Method getData.
	 * <p>
	 * It creates the matrix which contains the data of the bodies.<br>
	 * Each line contains the Cartesian coordinates x and y of the position and the speed of every bodies and their relative masses.
	 * 
	 * @return data Data matrix
	 */
	public double[][] getData() {
		double[][] data = {position_x, position_y, velocity_x, velocity_y, mass};
		return data;
	}
	
	
	/**
	 * Method getNumberOfBodies.
	 * <p>
	 * It returns the number of bodies.
	 * 
	 * @return number Number of bodies
	 */
	public int getNumberOfBodies(){
		return number;
	}
	
	/**
	 * Method print_body.
	 * <p>
	 * Print all the informations about all the bodies presented in the allbodies array. 
	 */
	public void print_body(){
		System.out.println("Printing bodies data by Generator......");
		for(int i = 0; i<number; i++){
			System.out.println(context.allbodies[i].getPosition() + " " + context.allbodies[i].getVelocity() + " " + context.allbodies[i].getMass() );
		}
	}
	
	
}
