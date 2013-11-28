package gui;

import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.*;

/**
 * Class MainFrame.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class MainFrame extends JFrame{
	
	private MainPanel panel;
	
	public MainFrame(String title){
		super(title);
		panel = new MainPanel();
		Container c = getContentPane();
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320,58);
		setResizable(false);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		c.add(panel);
	}

}
