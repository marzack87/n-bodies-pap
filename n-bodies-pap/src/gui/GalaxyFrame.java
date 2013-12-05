package gui;

import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.*;

/**
 * Class GalaxyFrame.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class GalaxyFrame extends JFrame{
	
	private GalaxyPanel gpanel;
	
	public GalaxyFrame(String title){
		super(title);
		gpanel = new GalaxyPanel();
		Container c = getContentPane();
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setResizable(false);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		c.add(gpanel);
	}

}
