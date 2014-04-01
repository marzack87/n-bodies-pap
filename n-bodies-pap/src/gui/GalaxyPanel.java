package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import entity.Controller;

/**
 * Class GalaxyPanel.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class GalaxyPanel extends JPanel implements ActionListener {

	private JButton start,pause,stop,step;
	private JLabel cmd;
	
	public GalaxyPanel(){
		
		cmd = new JLabel(" Commands: ");
		
		start = new JButton("Start");
		pause = new JButton("Pause");
		stop = new JButton("Stop");
		step = new JButton("Step by step");
		
		start.addActionListener(this);
		pause.addActionListener(this);
		stop.addActionListener(this);
		step.addActionListener(this);
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(cmd);
		add(start);
		add(pause);
		add(stop);
		add(step);
	}

	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == start){
			System.out.println("Start button");
			// The simulation will start
			Controller contr = new Controller();
			contr.startSimulation();
			// Il controller fara partire il visualizer(thread) <- forse questo dovra partire gia prima..subito dopo la creazione dell'interfaccia cosi da visualizzare
			// la posizione iniziale dei corpi
			// successivamente creera tutti i bodythread che inizieranno a fare il loro conti
		}
		if(source == pause){
			System.out.println("Pause button");
			// The simulation will be freezed
		}
		if(source == stop){
			System.out.println("Stop button");
			// The simulation will finish
			// si faranno terminare tutti i thread in qualche modo e il visualizer dopo aver stampato a video l'ultima posizione aggiornata morira anche lui
			if(step.getText().equals("Next step")){
				step.setText("Step by step");
			}
		}
		if(source == step){
			System.out.println("Step-by-step button");
			// The simulation's step-by-step modality
			// qui vedremo come implementare il tutto..
			step.setText("Next step");
			
		}

	}

}
