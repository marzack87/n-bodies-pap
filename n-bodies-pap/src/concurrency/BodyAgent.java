package concurrency;

import support.Context;

public class BodyAgent extends Thread {
	
	private Context context;
	
	/**
	 * Class BodyAgent default constructor.
	 *
	 **/
	public BodyAgent(Context cont, int i){
		
	}
	
	public void run(){
		while (context.stop != true){
			//Qui ci andra il comportamento di ogni singolo thread
		}
	}

}
