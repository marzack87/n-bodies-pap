package gui;

import java.awt.Container;
import java.awt.Toolkit;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;

public class MainFrame extends JFrame{
	
	private MainPanel panel;
	
	public MainFrame(String title){
		super(title);
		panel = new MainPanel();
		Container c = getContentPane();
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 80);
		setResizable(false);
		setLocation(
	            (Toolkit.getDefaultToolkit().getScreenSize().width
	                - this.getWidth()) / 2,
	            (Toolkit.getDefaultToolkit().getScreenSize().height
	                - this.getHeight()) / 2);
		c.add(panel);
	}

}
