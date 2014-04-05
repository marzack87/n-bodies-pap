package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.*;

import support.Context;

/**
 * Class GalaxyFrame.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class GalaxyFrame extends JFrame{
	
	private GalaxyPanel gpanel;
	private VisualiserPanel vpanel;
	
	public GalaxyFrame(String title, Context cont){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(970,630);
		setResizable(false);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		
		gpanel = new GalaxyPanel(cont);
		vpanel = new VisualiserPanel(cont);
		getContentPane().add(vpanel, BorderLayout.CENTER);
	    getContentPane().add(gpanel, BorderLayout.LINE_END);
		
		// creazione del thread Visualiser(vpanel) e start di esso. 
	}

}
