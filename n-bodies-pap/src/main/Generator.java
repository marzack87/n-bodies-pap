//prova

package main;

import java.util.Random;

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
	
	private int range = 99;
	
	private double[] position_x;
	private double[] position_y;
	private double[] velocity_x;
	private double[] velocity_y;
	private double[] mass;
	
	
	public Generator(){
		 
	}
	
	public void getDataFromFile(){
		//primo valore: NUMERO CORPI
		
		//poi tabella dei dati divisi da spazi
		// xxx xxx xxx xxx xxx
	}
	
	public void generateRandomData(int n){
		number = n;
		position_x = new double[number];
		position_y = new double[number];
		velocity_x = new double[number];
		velocity_y = new double[number];
		mass = new double[number];
		
		for (int i = 0; i < number; i++){
			//Random r = new Random();
			position_x[i] = (Math.random() * range) + 1;
			position_y[i] = (Math.random() * range) + 1;
			velocity_x[i] = (Math.random() * range) + 1;
			velocity_y[i] = (Math.random() * range) + 1;
			mass[i] = (Math.random() * range) + 1;
		}
	}

	public int getNumberOfBodies(){
		return number;
	}
	
	public double[][] getData() {
		double[][] data = {position_x, position_y, velocity_x, velocity_y, mass};
		return data;
	}
	
}
