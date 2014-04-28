package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.*;

import support.Util;
import concurrency.Visualiser;
import entity.Controller;


/**
 * Class GalaxyFrame.
 * Component that contains the display window of the bodies and the useful commands for the simulation.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 * 
 * @see java.awt
 * @see javax.swing
 */
public class GalaxyFrame extends JFrame{
	
	private GalaxyPanel gpanel;
	private VisualiserPanel vpanel;
	
	/**
	 * Class GalaxyFrame constructor.
	 * 
	 * @param title Frame name
	 * @param contr Controller entity
	 * 
	 * @see String
	 * @see entity.Controller
	 **/
	public GalaxyFrame(String title, Controller contr){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Util.displayAvailableSpace());
		setResizable(false);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		
		gpanel = new GalaxyPanel(contr);
		vpanel = new VisualiserPanel(contr, gpanel);
		contr.setUpVisualiser(vpanel);
		getContentPane().add(vpanel, BorderLayout.CENTER);
	    getContentPane().add(gpanel, BorderLayout.LINE_END);
	    
	    
		 
	}

}
