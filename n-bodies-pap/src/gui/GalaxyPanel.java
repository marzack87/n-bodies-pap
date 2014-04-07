package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.*;

import entity.Controller;
import support.Context;

/**
 * Class GalaxyPanel.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class GalaxyPanel extends JPanel implements ActionListener{

	private JButton start,pause,stop,step, save;
	private JLabel cmd, legend, white, cyan, blue, black;
	private Context context;
	
	/**
	 * Class GalaxyPanel default constructor.
	 *
	 **/
	public GalaxyPanel(Context cont){
		
		context = cont;
		
		cmd = new JLabel(" Commands: ");
		cmd.setAlignmentX(CENTER_ALIGNMENT);
		
		start = new JButton("Play");
		step = new JButton("Step Mode");
		stop = new JButton("Stop");
		save = new JButton("Save Data");
		save.setEnabled(false);
		
		legend = new JLabel("Legend: ");
		legend.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon icon_white = createImageIcon("images/white.png", "white circle");
		white = new JLabel("Smaller mass ", icon_white, JLabel.LEFT);
		ImageIcon icon_cyan = createImageIcon("images/cyan.png", "cyan circle");
		cyan = new JLabel("Mid-small mass  ", icon_cyan, JLabel.LEFT);
		ImageIcon icon_blue = createImageIcon("images/blue.png", "blue circle");
		blue = new JLabel("Mid-big mass ", icon_blue, JLabel.LEFT);
		ImageIcon icon_black = createImageIcon("images/black.png", "black circle");
		black = new JLabel("Bigger mass ", icon_black, JLabel.LEFT);
		
		start.addActionListener(this);
		step.addActionListener(this);
		stop.addActionListener(this);
		save.addActionListener(this);
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(cmd);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(start);
		add(step);
		add(stop);
		add(save);
		add(Box.createRigidArea(new Dimension(0,350)));
		add(legend);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(white);
		add(cyan);
		add(blue);
		add(black);
		
		// The entity Controller which will create the BodyAgent
		Controller contr = new Controller(context);
		System.out.println("Controller created");
		
	}

	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		// Start Button
		if(source == start){
			if(start.getText().equals("Pause")){
				start.setText("Play");
				System.out.println("Simulation freezed");
			}else{
				System.out.println("Start button");
				start.setText("Pause");
				if(step.getText().equals("Next Step")){
					step.setText("Step Mode");
				}
			}
		}
		// Step Mode Button
		if(source == step){
			System.out.println("Step-by-step button");
			// The simulation's step-by-step modality
			// qui vedremo come implementare il tutto..
			step.setText("Next Step");
			if(start.getText().equals("Pause")){
				start.setText("Play");
			}
		}
		// Stop Button
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
			save.setEnabled(true);
			start.setEnabled(false);
			step.setEnabled(false);
			stop.setEnabled(false);
		}
		// Save Button
		if(source == save){
			System.out.println("Save Button");
			savefile();
		}
		
	}
	
	/** 
	 * Private method createImageIcon.
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
	
	/**
	 * Private method savefile.
	 * 
	 */
	private void savefile(){
		try{
			// Opening of a new file "Stats".
			FileWriter f = new FileWriter("Stats.txt");
			PrintWriter out = new PrintWriter(f);
			out.println("N-Bodies Simulation Performance Stats:");
			out.close();
		}catch (Exception ex) { System.err.println(ex); }
		DoneDialog done = new DoneDialog();
		done.setVisible(true);
	
	}
	
}
