package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import support.Util;

/**
 * Class BodyDialog.
 * <p>
 * Class that extends the JDialog class.<br>
 * It represents the component in which the user, once he have chosen to create the bodies randomly, 
 * can specify their number and start their real initialization.<br> 
 * Once confirmation of the number of bodies the component will call the component that will represent the "Galaxy"
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */

public class BodyDialog extends JDialog implements ActionListener{
	
	private MainPanel mp;
	private JTextField body;
	private JButton ok;
	
	/**
	 * Class BodyDialog constructor.
	 * 
	 * @param mp MainPanel component 
	 **/
	public BodyDialog(MainPanel mp){
		
		super();
		this.mp = mp;
		
		setSize(new Dimension(230, 125));
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		add(Box.createVerticalStrut(5));
		
		JLabel s = new JLabel("How many bodies do you want?");
		add(s);
		s.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(Box.createVerticalStrut(5));
		
		body = new JTextField("100");
		body.setMinimumSize(new Dimension(this.getSize().width, 30));
		body.setMaximumSize(new Dimension(this.getSize().width, 30));
		add(body);
		
		body.setHorizontalAlignment(JLabel.CENTER);
		
		add(Box.createVerticalStrut(5));
		
		ok = new JButton("Go!");
		ok.addActionListener(this);
		ok.setMinimumSize(new Dimension(220, 30));
		ok.setMaximumSize(new Dimension(220, 30));
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(ok);
		add(Box.createVerticalStrut(5));
		
		
		
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		
	}
	
	public void actionPerformed(ActionEvent e){
		
		JButton source = (JButton) e.getSource();
		if (source == ok){
			try{
				int n = Integer.parseInt(body.getText());
				if(n<2) {
					JOptionPane.showMessageDialog(this, "Insert at least two bodies!!!", "Warning", JOptionPane.WARNING_MESSAGE, null);
				}else{
					mp.initWithRandom(n,Util.one_sun);
				}
			}catch(Exception ex) { 
				ex.printStackTrace();
				System.err.println(ex); 
				JOptionPane.showMessageDialog(this, "Wrong input data, a number MUST be inserted!!!", "Error", JOptionPane.ERROR_MESSAGE, null);
			}
			this.setVisible(false);
		}
		
	}
		
}
