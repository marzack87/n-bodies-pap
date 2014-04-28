package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import support.Util;

/**
 * Class BodyDialog.
 * Class that extends the JDialog class, and that is the component in which the user, once he have chosen to create the bodies randomly, 
 * can specify their number and start their real initialization. 
 * Once confirmation of the number of bodies the component will call the component that will represent the "Galaxy"
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 * 
 * @see java.awt
 * @see javax.swing
 */

public class BodyDialog extends JDialog implements ActionListener{
	
	private MainPanel mp;
	private JTextField body;
	private JButton well;
	private JCheckBox sun;
	
	/**
	 * Class BodyDialog constructor.
	 * 
	 * @param mp MainPanel component
	 * 
	 * @see MainPanel 
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
		sun = new JCheckBox("Sun");
		sun.setSelected(true);
		add(sun,BorderLayout.EAST);
		well = new JButton("OK");
		well.addActionListener(this);
		add(well,BorderLayout.SOUTH);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		
	}
	
	public void actionPerformed(ActionEvent e){
		
			try{
				int n = Integer.parseInt(body.getText());
				Util.one_sun = sun.isSelected();
				mp.initWithRandom(n,sun.isSelected());
			}catch(Exception ex) { 
				ex.printStackTrace();
				System.err.println(ex); 
				JOptionPane.showMessageDialog(this, "Wrong input data, a number MUST be inserted!!!", "Error", JOptionPane.ERROR_MESSAGE, null);
			}
			this.setVisible(false);
		
	}

}
