package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.*;

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
		setSize(980,630);
		setResizable(false);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		
		
		vpanel = new VisualiserPanel(contr);
		Visualiser v = new Visualiser(vpanel, contr);
		gpanel = new GalaxyPanel(contr, v);
		getContentPane().add(vpanel, BorderLayout.CENTER);
	    getContentPane().add(gpanel, BorderLayout.LINE_END);
	    
	    
		 
	}

}
