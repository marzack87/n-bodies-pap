package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Class GalaxyPanel.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class GalaxyPanel extends JPanel implements ActionListener {

	private JButton add,remove,start,pause,stop,step;
	
	public GalaxyPanel(){
		
		start = new JButton("Start");
		pause = new JButton("Pause");
		stop = new JButton("Stop");
		step = new JButton("Step by step");
		
		start.addActionListener(this);
		pause.addActionListener(this);
		stop.addActionListener(this);
		step.addActionListener(this);
		
		setLayout(new GridLayout(4,1));
		add(start);
		add(pause);
		add(stop);
		add(step);
		
	}

	
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
