package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import entity.Controller;
import support.Context;

/**
 * Class GalaxyPanel.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class GalaxyPanel extends JPanel implements ActionListener{

	private JButton start,pause,stop,step;
	private JLabel cmd, legend, white, cyan, blue, black;
	private Context context;
	
	public GalaxyPanel(Context cont){
		
		context = cont;
		
		cmd = new JLabel("Commands: ");
		cmd.setAlignmentX(CENTER_ALIGNMENT);
		
		start = new JButton("Start");
		stop = new JButton("Stop");
		step = new JButton("Step Mode");
		
		
		legend = new JLabel("Legend: ");
		legend.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon icon_white = createImageIcon("images/white.png", "white circle");
		white = new JLabel("Smaller mass", icon_white, JLabel.LEFT);
		ImageIcon icon_cyan = createImageIcon("images/cyan.png", "cyan circle");
		cyan = new JLabel("Mid-small mass", icon_cyan, JLabel.LEFT);
		ImageIcon icon_blue = createImageIcon("images/blue.png", "blue circle");
		blue = new JLabel("Mid-big mass", icon_blue, JLabel.LEFT);
		ImageIcon icon_black = createImageIcon("images/black.png", "black circle");
		black = new JLabel("Bigger mass", icon_black, JLabel.LEFT);
		
		start.addActionListener(this);
		step.addActionListener(this);
		stop.addActionListener(this);
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(cmd);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(start);
		add(stop);
		add(step);
		add(Box.createRigidArea(new Dimension(0,400)));
		add(legend);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(white);
		add(cyan);
		add(blue);
		add(black);
		
	}

	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == start){
			if(start.getText().equals("Pause")){
			System.out.println("Simulation freezed");
			start.setText("Play");
			}else{
			System.out.println("Start button");
			start.setText("Pause");
			}
		}
		if(source == stop){
			System.out.println("Stop button");
			// The simulation will finish
			// si faranno terminare tutti i thread in qualche modo e il visualizer dopo aver stampato a video l'ultima posizione aggiornata morira anche lui
			if(step.getText().equals("Next Step")){
				step.setText("Step Mode");
			}
			if(start.getText().equals("Pause")){
				start.setText("Play");
			}
		}
		if(source == step){
			System.out.println("Step-by-step button");
			// The simulation's step-by-step modality
			// qui vedremo come implementare il tutto..
			step.setText("Next Step");
			if(start.getText().equals("Pause")){
				start.setText("Play");
			}
		}
	}
	
	/** 
	 * Private method createImageIcon
	 * Returns an ImageIcon, or null if the path was invalid.
	 * 
	 * @param path - String 
	 * @param description - String
	 */
	private ImageIcon createImageIcon(String path,String description) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
}
