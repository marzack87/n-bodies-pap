package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagLayoutInfo;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

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
 * Class that extends the JDialog class, and that is the component in which the user, once he have chosen to create the bodies randomly, 
 * can specify their number and start their real initialization. 
 * Once confirmation of the number of bodies the component will call the component that will represent the "Galaxy"
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 * 
 * @see java.awt
 * @see javax.swing
 */

public class BodyDialog extends JDialog implements ActionListener, ChangeListener{
	
	private MainPanel mp;
	private JTextField body;
	private JButton well, custom;
	private JCheckBox sun;
	private JLabel sun_mass;
	
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
		
		setSize(new Dimension(250, 220));
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel s = new JLabel("HOW MANY BODIES?");
		add(s);
		s.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(Box.createVerticalStrut(5));
		
		body = new JTextField("100");
		body.setMaximumSize(new Dimension(this.getSize().width,25));
		add(body);
		
		body.setHorizontalAlignment(JLabel.CENTER);
		
		add(Box.createVerticalStrut(5));
		
		well = new JButton("GO!");
		well.addActionListener(this);
		well.setMinimumSize(new Dimension(this.getSize().width, 25));
		well.setMaximumSize(new Dimension(this.getSize().width, 25));
		well.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(well);
		add(Box.createVerticalStrut(5));
		
		// BARRETTA
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		Dimension d = sep.getPreferredSize();
		d.width = sep.getMaximumSize().width;
		sep.setMaximumSize(d);
		add(sep);
		
		add(Box.createVerticalStrut(5));
		
		JLabel o = new JLabel("OPTIONS:");
		o.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(o);
		
		add(Box.createVerticalStrut(5));
		
		sun = new JCheckBox("Sun");
		sun.setSelected(true);
		sun.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(sun);
		
		add(Box.createVerticalStrut(5));
		
		custom = new JButton("SHOW CUSTOM PARAMETERS");
		custom.addActionListener(this);
		custom.setMinimumSize(new Dimension(220, 30));
		custom.setMaximumSize(new Dimension(220, 30));
		custom.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(custom);
		
		add(Box.createVerticalStrut(5));
		
		sun_mass = new JLabel("SUN MASS:");
		
		add(sun_mass);
		sun_mass.setAlignmentX(Component.CENTER_ALIGNMENT);
		sun_mass.setVisible(false);
		
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		
	}
	
	public void actionPerformed(ActionEvent e){
		
		JButton source = (JButton) e.getSource();
		if (source == well){
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
		} else if (source == custom) {
			if (custom.getText().equals("SHOW CUSTOM PARAMETERS")) {
				sun_mass.setVisible(true);
				custom.setText("HIDE CUSTOM PARAMETERS");
				setSize(new Dimension(250, 350));
			} else {
				sun_mass.setVisible(false);
				custom.setText("SHOW CUSTOM PARAMETERS");
				setSize(new Dimension(250, 220));
			}
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
	JSlider source = (JSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			int step = source.getValue();
			if (step == 1) {
				sun_mass.setVisible(true);
				setSize(new Dimension(250, 350));
			} else {
				sun_mass.setVisible(false);
				setSize(new Dimension(250, 250));
			}
		}	
	}
	
}
