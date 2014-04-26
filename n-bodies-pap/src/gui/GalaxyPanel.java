package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import support.Util;
import entity.Controller;

/**
 * Class GalaxyPanel.
 * Component that contains the commands that are useful for the simulation 
 * and a legend to explain the meaning of the colors of bodies.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 * 
 * @see java.awt
 * @see javax.swing
 */
public class GalaxyPanel extends JPanel implements ActionListener, ChangeListener{

	private JButton btn_start,btn_stop,btn_step, btn_save, btn_reset;
	private JLabel lbl_cmd, lbl_dt, lbl_legend, lbl_one, lbl_two, lbl_three, lbl_four, lbl_sun;
	private JCheckBox chb_tracks;
	private JSlider sld_velocity;
	private Controller controller;
	
	/**
	 * Class GalaxyPanel default constructor.
	 * 
	 * @param contr Controller entity
	 * 
	 * @see entity.Controller
	 * @see concurrency.Visualiser
	 **/
	public GalaxyPanel(Controller contr){
		
		controller = contr;
		
		lbl_cmd = new JLabel(" Commands: ");
		lbl_cmd.setAlignmentX(CENTER_ALIGNMENT);
		
		// Create the command button
		btn_start = new JButton("Play");
		btn_step = new JButton("Step Mode");
		btn_stop = new JButton("Stop");
		btn_reset = new JButton("Reset");
		btn_reset.setEnabled(false);
		btn_save = new JButton("Save Data");
		btn_save.setEnabled(false);
		
		chb_tracks = new JCheckBox("Tracks");
		chb_tracks.setSelected(true);
		
		lbl_dt = new JLabel("  dt = 0.001");
		
		// Create the slider
		sld_velocity = new JSlider(JSlider.HORIZONTAL, 1, 6, 3);
		sld_velocity.addChangeListener(this);
		sld_velocity.setMajorTickSpacing(1);
		sld_velocity.setSnapToTicks(true);
		sld_velocity.setPaintTicks(true);
		// Create the slider label table
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer( 1 ), new JLabel(createImageIcon("images/slow.png", "slow")));
		labelTable.put(new Integer( 6 ), new JLabel(createImageIcon("images/fast.png", "fast")));
		sld_velocity.setLabelTable(labelTable);
		sld_velocity.setBorder(this.getBorder());
		sld_velocity.setPaintLabels(true);
		sld_velocity.setPreferredSize(new Dimension(10,10));
		
		// Create the legend
		lbl_legend = new JLabel("Legend: ");
		lbl_legend.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon icon_one = icon_body(1);
		lbl_one = new JLabel("Smaller mass ", icon_one, JLabel.LEFT);
		ImageIcon icon_two = icon_body(2);
		lbl_two = new JLabel("Mid-small mass  ", icon_two, JLabel.LEFT);
		ImageIcon icon_three = icon_body(3);
		lbl_three = new JLabel("Mid-big mass ", icon_three, JLabel.LEFT);
		ImageIcon icon_four = icon_body(4);
		lbl_four = new JLabel("Bigger mass ", icon_four, JLabel.LEFT);
		ImageIcon icon_sun = icon_body(5);
	    lbl_sun = new JLabel("Sun ", icon_sun, JLabel.LEFT);
		
		btn_start.addActionListener(this);
		btn_step.addActionListener(this);
		btn_stop.addActionListener(this);
        btn_reset.addActionListener(this);
		btn_save.addActionListener(this);
		chb_tracks.addActionListener(this);
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(lbl_cmd);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(btn_start);
		add(btn_step);
		add(btn_stop);
		add(btn_reset);
		add(btn_save);
		add(Box.createRigidArea(new Dimension(0,50)));
		add(lbl_dt);
		add(sld_velocity);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(chb_tracks);
		add(Box.createRigidArea(new Dimension(0,50)));
		add(lbl_legend);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(lbl_one);
		add(lbl_two);
		add(lbl_three);
		add(lbl_four);
		add(lbl_sun);
		add(Box.createRigidArea(new Dimension(0,10)));
		
	}

	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == btn_start){
			// Start Button
			if(btn_start.getText().equals("Pause")){
				btn_start.setText("Play");
				log("Simulation freezed");
				controller.pause();
			}else{
				log("Start button");
				btn_start.setText("Pause");
				if(btn_step.getText().equals("Next Step")){
					btn_step.setText("Step Mode");
				}
				
				if (!controller.SimulationIsAlive()){
					controller.startSimulation();
				}
				controller.play();
			}
		} else if(source == btn_step){
			log("Step-by-step button");
			// The simulation's step-by-step modality
			btn_step.setText("Next Step");
			if(btn_start.getText().equals("Pause")){
				btn_start.setText("Play");
			}
			if (!controller.SimulationIsAlive()){
				controller.startSimulation();
			}
			if(controller.SimulationIsRunning()){
				controller.pause();
			}
			controller.step();
		} else if(source == btn_stop){
			log("Stop Button");
			// The simulation will finish
			if(btn_step.getText().equals("Next Step")){
				btn_step.setText("Step Mode");
			}
			if(btn_start.getText().equals("Pause")){
				btn_start.setText("Play");
			}
			btn_save.setEnabled(true);
			btn_start.setEnabled(false);
			btn_step.setEnabled(false);
			btn_reset.setEnabled(true);
			btn_stop.setEnabled(false);
			
			controller.stopSimulation();
		} else if(source == btn_save){
			log("Save Button");
			savefile();
		} else if(source == btn_reset){
				log("Reset Button");
				// Reset the simulation
				controller.reset();
				btn_start.setEnabled(true);
				btn_step.setEnabled(true);
				btn_stop.setEnabled(true);
				btn_save.setEnabled(false);
		} else if (source == chb_tracks) {
			if (chb_tracks.isSelected()){
				// Track selected or not
				log("tracks ON");
				controller.tracks = true;
			} else {
				log("tracks OFF");
				controller.tracks = false;
			}
		}
		
	}
	
	/** 
	 * Private method createImageIcon.
	 * Returns an ImageIcon, or null if the path was invalid.
	 * 
	 * @param path The path of the image file 
	 * @param description Description of te image
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
	 * Save all the information of the simulation in a text file.
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
	
	private ImageIcon icon_body(int type){
		int size = (type == 5) ? 20 : 10;
		BufferedImage image=new BufferedImage(size, size, BufferedImage.TYPE_3BYTE_BGR);
	    Graphics2D g = image.createGraphics();
	    g.setColor(new Color(0xEEEEEE));
	    g.fillRect(0, 0, image.getWidth(), image.getHeight());
	    
	    Color c = null;
	    
	    if (type == 1) {
	    	c = Util.light_one;
	    } else if (type == 2) {
	    	c = Util.light_two;
	    } else if (type == 3) {
	    	c = Util.light_three;
	    } else if (type == 4) {
	    	c = Util.light_five;
	    } else if (type == 5) {
	    	c = Util.sun;
	    }
	    
	    g.setColor(c);
	    g.fillOval(0, 0, image.getWidth()-1, image.getHeight()-1);
	    if (type == 5){
	    	 g.setColor(Color.red);
	    	g.drawOval(0, 0, image.getWidth()-1, image.getHeight()-1);
	    }
	    
	    g.dispose();
		
	    return new ImageIcon(image);
	}
	
	private void log(String msg){
        System.out.println("[GALAXY PANEL] "+msg);
    }


	@Override
	public void stateChanged(ChangeEvent e) {
	JSlider source = (JSlider)e.getSource();
	if (!source.getValueIsAdjusting()) {
		int step = source.getValue();
		step = source.getMaximum() - step;
		double dt = 1 / (Math.pow(10, step));
		controller.setDeltaT(dt);
		lbl_dt.setText("  dt = " + dt);
	}	
	}
	
}
