package main;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) { 
		Generator gen = new Generator();
		gen.generateRandomData(10);
		Controller contr = new Controller(gen);
	}

}
