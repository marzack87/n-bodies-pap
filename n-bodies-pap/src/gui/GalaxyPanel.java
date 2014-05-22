package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import support.Util;
import entity.Controller;

/**
 * Class GalaxyPanel.
 * <p>
 * Component that contains the commands that are useful for the simulation 
 * and a legend to explain the meaning of the colors of bodies.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class GalaxyPanel extends JPanel implements ActionListener, ChangeListener{

	private JButton btn_start,btn_stop,btn_step, btn_save, btn_reset;
	private JLabel lbl_cmd, lbl_dt, lbl_legend, lbl_one, lbl_two, lbl_three, lbl_four, lbl_sun, lbl_n_iter_title, lbl_last_iter_time_title, lbl_FPS_title, lbl_n_iter, lbl_last_iter_time, lbl_FPS,lbl_stats;
	private JCheckBox chb_tracks, chb_velocity;
	private JSlider sld_velocity;
	private Controller controller;
	private Clip clip;
	
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
		btn_start.setAlignmentX(CENTER_ALIGNMENT);
		
		btn_step = new JButton("Step Mode");
		btn_step.setAlignmentX(CENTER_ALIGNMENT);
		
		btn_stop = new JButton("Stop");
		btn_stop.setAlignmentX(CENTER_ALIGNMENT);
		
		btn_reset = new JButton("Reset");
		btn_reset.setAlignmentX(CENTER_ALIGNMENT);
		btn_reset.setEnabled(false);
		
		btn_save = new JButton("Save Data");
		btn_save.setAlignmentX(CENTER_ALIGNMENT);
		btn_save.setEnabled(false);
		
		// Create the CheckBox for tracks and velocity
		chb_tracks = new JCheckBox("Tracks");
		chb_tracks.setSelected(false);
		chb_tracks.setAlignmentX(CENTER_ALIGNMENT);
		chb_velocity = new JCheckBox("Velocity");
		chb_velocity.setSelected(false);
		chb_velocity.setAlignmentX(CENTER_ALIGNMENT);
		
		// Label for dt
		double dt = Util.DEFAULT_DT;
		lbl_dt = new JLabel("dt = " + String.format( "%.6f", dt ));
		lbl_dt.setAlignmentX(CENTER_ALIGNMENT);
		
		// Create the slider
		sld_velocity = new JSlider(JSlider.HORIZONTAL, Util.MIN_SCALE, Util.MAX_SCALE, Util.MID_SCALE);
		sld_velocity.addChangeListener(this);
		sld_velocity.setMajorTickSpacing(1);
		sld_velocity.setSnapToTicks(true);
		sld_velocity.setPaintTicks(true);
		// Create the slider label table
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		
		String slow;
		String fast;
		if (Util.star_wars_theme){
			slow = "images/R2D2.png";
			fast = "images/M-Falcon.png";
		} else {
			slow = "images/slow.png";
			fast = "images/fast.png";
		}
		
		labelTable.put(new Integer( Util.MIN_SCALE ), new JLabel(createImageIcon(slow, "slow")));
		labelTable.put(new Integer( Util.MAX_SCALE ), new JLabel(createImageIcon(fast, "fast")));
		sld_velocity.setLabelTable(labelTable);
		sld_velocity.setBorder(this.getBorder());
		sld_velocity.setPaintLabels(true);
		sld_velocity.setPreferredSize(new Dimension(180,60));
		sld_velocity.setMaximumSize(new Dimension(180,60));
		
		// Performance labels
		lbl_stats = new JLabel(" Performance: ");
		lbl_stats.setAlignmentX(CENTER_ALIGNMENT);
		lbl_n_iter_title = new JLabel(" Iterations:");
		lbl_n_iter_title.setAlignmentX(CENTER_ALIGNMENT);
		lbl_n_iter = new JLabel("" + Util.total_iteration);
		lbl_n_iter.setFont(new Font("Arial", Font.BOLD, 20));
		lbl_n_iter.setAlignmentX(CENTER_ALIGNMENT);
		lbl_last_iter_time_title = new JLabel(" Last iter time:");
		lbl_last_iter_time_title.setAlignmentX(CENTER_ALIGNMENT);
		lbl_last_iter_time = new JLabel("" + Util.last_iter_time);
		lbl_last_iter_time.setFont(new Font("Arial", Font.BOLD, 20));
		lbl_last_iter_time.setAlignmentX(CENTER_ALIGNMENT);
		lbl_FPS_title = new JLabel(" FPS:");
		lbl_FPS_title.setAlignmentX(CENTER_ALIGNMENT);
		lbl_FPS = new JLabel("0");
		lbl_FPS.setFont(new Font("Arial", Font.BOLD, 20));
		lbl_FPS.setAlignmentX(CENTER_ALIGNMENT);
		
		// Create the legend
		lbl_legend = new JLabel("Legend: ");
		lbl_legend.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon icon_one = icon_body(1);
		lbl_one = new JLabel("Smaller mass ", icon_one, JLabel.LEFT);
		lbl_one.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon icon_two = icon_body(2);
		lbl_two = new JLabel("Mid-small mass  ", icon_two, JLabel.LEFT);
		lbl_two.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon icon_three = icon_body(3);
		lbl_three = new JLabel("Mid-big mass ", icon_three, JLabel.LEFT);
		lbl_three.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon icon_four = icon_body(4);
		lbl_four = new JLabel("Bigger mass ", icon_four, JLabel.LEFT);
		lbl_four.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon icon_sun = icon_body(5);
	    if (Util.star_wars_theme){
	    	lbl_sun = new JLabel("Death Star ", icon_sun, JLabel.LEFT);
	    } else {
	    	lbl_sun = new JLabel("Sun ", icon_sun, JLabel.LEFT);
	    }
	    lbl_sun.setAlignmentX(CENTER_ALIGNMENT);
		
		btn_start.addActionListener(this);
		btn_step.addActionListener(this);
		btn_stop.addActionListener(this);
        btn_reset.addActionListener(this);
		btn_save.addActionListener(this);
		chb_tracks.addActionListener(this);
		chb_velocity.addActionListener(this);
		
		// Star Wars Mode ON -> Change the skin LOL
		if (Util.star_wars_theme){
			setBackground(Color.black);
			lbl_cmd.setForeground(Color.YELLOW);
			lbl_dt.setForeground(Color.YELLOW);
			lbl_stats.setForeground(Color.YELLOW);
			lbl_n_iter_title.setForeground(Color.YELLOW);
			lbl_n_iter.setForeground(Color.YELLOW);
			lbl_last_iter_time_title.setForeground(Color.YELLOW);
			lbl_last_iter_time.setForeground(Color.YELLOW);
			lbl_FPS_title.setForeground(Color.YELLOW);
			lbl_FPS.setForeground(Color.YELLOW);
			lbl_legend.setForeground(Color.YELLOW);
			lbl_one.setForeground(Color.YELLOW);
			lbl_two.setForeground(Color.YELLOW);
			lbl_three.setForeground(Color.YELLOW);
			lbl_four.setForeground(Color.YELLOW);
			lbl_sun.setForeground(Color.YELLOW);
			chb_tracks.setForeground(Color.YELLOW);
			chb_velocity.setForeground(Color.YELLOW);
		}
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(lbl_cmd);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(btn_start);
		add(btn_step);
		add(btn_stop);
		add(btn_reset);
		add(btn_save);
		add(Box.createRigidArea(new Dimension(0,30)));
		add(lbl_dt);
		add(sld_velocity);
		add(Box.createRigidArea(new Dimension(0,40)));
		add(chb_tracks);
		add(chb_velocity);
		add(Box.createRigidArea(new Dimension(0,40)));
		add(lbl_stats);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(lbl_n_iter_title);
		add(lbl_n_iter);
		add(lbl_last_iter_time_title);
		add(lbl_last_iter_time);
		add(lbl_FPS_title);
		add(lbl_FPS);
		add(Box.createRigidArea(new Dimension(0,30)));
		add(lbl_legend);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(lbl_one);
		add(lbl_two);
		add(lbl_three);
		add(lbl_four);
		add(lbl_sun);
		add(Box.createRigidArea(new Dimension(0,200)));
		if(Util.star_wars_theme){
			ImageIcon logo = createImageIcon("images/starwars-logo.png", "Logo");
			JLabel logosw = new JLabel(logo);
			logosw.setAlignmentX(CENTER_ALIGNMENT);
			add(logosw);
		}
		
		// The Empire Strikes Back LOL
		String sound_path = "sound/imperial.wav";
		File soundFile = new File(getClass().getResource(sound_path).getPath());
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			// Get a sound clip resource.
	         clip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         clip.open(audioIn);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		
	}

	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == btn_start){
			// Start Button
			if(btn_start.getText().equals("Pause")){
				// Simulation is running -> Freeze it
				btn_start.setText("Play");
				log("Pause Button");
				controller.pause();
				btn_reset.setEnabled(true);
				btn_stop.setEnabled(true);
				btn_step.setEnabled(true);
			}else{
				log("Start Button");
				// Start/re-start simulation
				btn_start.setText("Pause");
				btn_step.setEnabled(true);
				btn_reset.setEnabled(false);
				if(btn_step.getText().equals("Next Step")){
					btn_step.setText("Step Mode");
				}
				if (!controller.SimulationIsAlive()){
					controller.startSimulation();
				}
				controller.play();
				if (Util.star_wars_theme) clip.loop(Clip.LOOP_CONTINUOUSLY);
				
			}
		} else if(source == btn_step){
			log("Step-Mode Button");
			// The simulation's step-by-step modality
			btn_step.setText("Next Step");
			btn_reset.setEnabled(true);
			if(btn_start.getText().equals("Pause")){
				btn_start.setText("Play");
			}
			if(controller.SimulationIsRunning()){
				controller.pause();
			}
			if (!controller.SimulationIsAlive()){
				// Start in step mode
				controller.startSimulation();
			}
			controller.step();
		} else if(source == btn_stop){
			log("Stop Button");
			// The simulation will finish
			if(btn_step.getText().equals("Next Step")){
				btn_step.setText("Step Mode");
			}else if(btn_start.getText().equals("Pause")){
				btn_start.setText("Play");
			}
			btn_save.setEnabled(true);
			btn_start.setEnabled(false);
			btn_step.setEnabled(false);
			btn_reset.setEnabled(false);
			btn_stop.setEnabled(false);
			
			controller.stopSimulation();
			Util.t_stop = System.nanoTime();
			log("Total execution time: " + (Util.t_stop-Util.t_start)*1e-9 + " sec");
			clip.stop();
		} else if(source == btn_save){
			log("Save Button");
			savefile();
		} else if(source == btn_reset){
				log("Reset Button");
				// Reset the simulation
				if(btn_step.getText().equals("Next Step")){
					btn_step.setText("Step Mode");
				}
				controller.reset();
				btn_start.setEnabled(true);
				btn_step.setEnabled(true);
				btn_stop.setEnabled(true);
				btn_save.setEnabled(false);
				
		} else if (source == chb_tracks) {
			if (chb_tracks.isSelected()){
				// Track selected or not
				log("Tracks ON");
				controller.tracks = true;
			} else {
				log("Tracks OFF");
				controller.tracks = false;
			}
		} else if (source == chb_velocity) {
			if (chb_velocity.isSelected()){
				// Velocity selected or not
				log("Velocity ON");
				controller.velocity = true;
			} else {
				log("Velocity OFF");
				controller.velocity = false;
			}
		}
		
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
	JSlider source = (JSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			int step = source.getValue();
			//step = source.getMaximum() - step;
			//double dt = (Util.DEFAULT_DT * step) / Util.MID_SCALE;
			double dt = (Util.DEFAULT_DT * (Math.pow(10, step-1)) / (Math.pow(10, (Util.MAX_SCALE - Util.MID_SCALE))));
			controller.setDeltaT(dt);
			lbl_dt.setText("dt = " + String.format( "%.7f", dt ));
		}	
	}
	
	/** 
	 * Private method createImageIcon.
	 * <p>
	 * Returns an ImageIcon, or null if the path was invalid.
	 * 
	 * @param path The path of the image file 
	 * @param description Description of the image
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
	 * Private method icon_body.
	 * <p>
	 * Used to create the icon of each body in the legend.
	 * 
	 * @param type
	 * @return image New ImageIcon
	 */
	@SuppressWarnings("unused")
	private ImageIcon icon_body(int type){
		int size = 0;
		if (type == 5) {
			if (Util.star_wars_theme){
				return createImageIcon("images/DeathStar.png", "DeathStar");
			} else {
				size = Util.SUN_RADIUS*2;
			}
		} else {
			if (Util.BODY_RADIUS<=2) {
				size = Util.BODY_RADIUS*5;
			} else {
				size = Util.BODY_RADIUS*(8/3);
			}
		}
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
	    
	    if(Util.star_wars_theme){
	    	if (type == 5){
	    		ImageIcon img = createImageIcon("images/DeathStar.png", "DeathStar");
    			g.drawImage(img.getImage(), image.getWidth()-1, image.getHeight()-1, this);
		    } else {
		    	g.setColor(c);
			    g.fillOval(0, 0, image.getWidth()-1, image.getHeight()-1);
		    }
	    } else {
	    	g.setColor(c);
		    g.fillOval(0, 0, image.getWidth()-1, image.getHeight()-1);
		    if (type == 5){
		    	g.setColor(Color.red);
		    	g.drawOval(0, 0, image.getWidth()-1, image.getHeight()-1);
		    }
	    } 
	    g.dispose();
	    return new ImageIcon(image);
	}
	
	/**
	 * Private method savefile.
	 * <p>
	 * Save all the information of the simulation in a text file.
	 */
	private void savefile(){
		try{
			// Opening of a new file "Stats".
			FileWriter f = new FileWriter("Stats.txt");
			PrintWriter out = new PrintWriter(f);
			out.println("--------------- N-Bodies Simulation ---------------");
			out.println("");
			out.println("Author:");
			out.println("Richiard Casadei(Sith) & Marco Zaccheroni(Jedi)");
			out.println("");
			out.println(" - Computer info -");
			out.println(" Name of the OS:  "+ Util.nameOS);
			out.println(" Version of the OS: " + Util.versionOS);
			out.println(" Architecture of the OS: " + Util.architectureOS);
			out.println(" Number of cores: " + Util.ncores);
			out.println("");
			out.println(" - Bodies and Galaxy info -");
			if(Util.one_sun){
				out.println(" A Sun is present and its mass is " + Util.SUN_MASS);
			}else{
				out.println(" No Sun present ");
			}
			out.println(" # of Bodies: " + Util.n_bodies);
			out.println(" The 4 mass levels of the bodies are: ");
			out.println(" Level 1:" + " - " + Util.SMALL_MASS + " (Smallest mass)");
			out.println(" Level 2:" + " - " + Util.MIDSMALL_MASS + " (Mid-Small mass)");
			out.println(" Level 3:" + " - " + Util.MIDBIG_MASS + " (Mid-Big mass)");
			out.println(" Level 4:" + " - " + Util.BIG_MASS + " (Biggest mass)");
			out.println(" Velocity Bodies range: 0 - " + Util.RANGE_BODIES_VELOCITY);
			out.println(" Galaxy Radius: " + Util.GALAXY_RADIUS);
			out.println("");
			out.println(" - Simulation info -");
			if(Util.soft_param_mode) {
				out.println(" Execution Mode: Physics");
			}else{
				out.println(" Execution Mode: Astronomical");
			}
			out.println(" # of iteration: " + Util.total_iteration);
			out.println(" Total execution time: " + (Util.t_stop-Util.t_start)*Math.pow(10, -9) + " sec");
			out.println(" Average # of iteration per sec: " + (Util.total_iteration/((Util.t_stop-Util.t_start)*Math.pow(10, -9))));
			out.println("");
			out.println("---------------------------------------------------");
			out.println("");
			out.println("            May the Force be with you");
			out.println("");
			out.println("---------------------------------------------------");
			out.close();
		}catch (Exception ex) { System.err.println(ex); }
		DoneDialog done = new DoneDialog();
		done.setVisible(true);
	
	}
	
	/**
	 * Method updatePerformanceData.
	 * <p>
	 * Called by the Visualiser thread to update the performance statistics shown in the GalaxyPanel
	 */
	public void updatePerformanceData(){
		lbl_n_iter.setText("" + Util.total_iteration);
		lbl_last_iter_time.setText("" + Util.last_iter_time);
		lbl_FPS.setText(String.format( "%.2f",(1e9 / Util.last_iter_time)));
	}
	
	/**
     * Private method log.
     * <p>
     * Prints to the console a log of the activity of the GalaxyPanel.
     * 
     * @param msg The message to be printed
     */
	private void log(String msg){
        System.out.println("[GALAXY PANEL] "+msg);
    }

}
