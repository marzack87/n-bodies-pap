package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.*;

/**
 * Class GalaxyFrame.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class GalaxyFrame extends JFrame{
	
	private GalaxyPanel gpanel;
	private VisualiserPanel vpanel;
	
	public GalaxyFrame(String title){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(930,630);
		setResizable(true);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		
		gpanel = new GalaxyPanel();
		vpanel = new VisualiserPanel();
		add(vpanel, BorderLayout.CENTER);
		add(gpanel, BorderLayout.LINE_END);	
	}

}
