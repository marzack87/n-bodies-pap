package gui;

import java.awt.*;

import javax.swing.*;

import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;

import java.awt.event.*;


/**
 * Class DoneDialog.
 *
 * @author Richard Casadei, Marco Zaccheroni.
 *
 **/
 
public class DoneDialog extends JDialog implements ActionListener{
	
	/**
	 * Class DoneDialog default constructor.
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
