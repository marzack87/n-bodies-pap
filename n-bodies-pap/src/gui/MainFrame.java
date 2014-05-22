package gui;

import java.awt.Toolkit;

import javax.swing.*;

import entity.Controller;

/**
 * Class MainFrame.
 * <p>
 * Component in which the user can decide how to initialize the bodies.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class MainFrame extends JFrame{
	
	private MainPanel panel;
	
	/**
	 * Class MainFrame constructor.
	 *
	 **/
	public MainFrame(String title, Controller contr){
		super(title);
		panel = new MainPanel(contr);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(260, 230);
		setResizable(false);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		getContentPane().add(panel);
	}

}
