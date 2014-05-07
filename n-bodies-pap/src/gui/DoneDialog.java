package gui;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;


/**
 * Class DoneDialog.
 * <p>
 * Class that extends the JDialog class.<br>
 * It is the component,that will inform the user about the correct saving of the simulation information.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 **/
 
public class DoneDialog extends JDialog implements ActionListener{
	
	/**
	 * Class DoneDialog constructor.
	 *
	 **/
	public DoneDialog(){
		
		super();
		setSize(new Dimension(350, 70));
		setLayout(new BorderLayout());
		JLabel s = new JLabel("Your simulation stats was successfully saved.");
		add(s,BorderLayout.CENTER);
		s.setHorizontalAlignment(JLabel.CENTER);
		JButton well = new JButton("Well done!");
		well.addActionListener(this);
		add(well,BorderLayout.SOUTH);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		
	}
	
	public void actionPerformed(ActionEvent e){
		System.exit(0);
	}

}
