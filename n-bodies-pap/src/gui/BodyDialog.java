package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class BodyDialog extends JDialog implements ActionListener{
	
	private MainPanel mp;
	private JTextField body;
	
	/**
	 * Class DoneDialog default constructor.
	 *
	 **/
	public BodyDialog(MainPanel mp){
		
		super();
		this.mp = mp;
		
		setSize(new Dimension(350, 100));
		setLayout(new BorderLayout());
		JLabel s = new JLabel("How many bodies do you want?");
		add(s,BorderLayout.NORTH);
		s.setHorizontalAlignment(JLabel.CENTER);
		body = new JTextField("100");
		add(body,BorderLayout.CENTER);
		body.setHorizontalAlignment(JLabel.CENTER);
		JButton well = new JButton("OK");
		well.addActionListener(this);
		add(well,BorderLayout.SOUTH);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		
	}
	
	public void actionPerformed(ActionEvent e){
		
		int n = Integer.parseInt(body.getText());
		mp.initWithRandom(n);
		this.setVisible(false);
		
	}

}
