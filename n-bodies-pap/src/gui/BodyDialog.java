package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
 * can specify their number and start their real initialization. 
 * Once confirmation of the number of bodies the component will call the component that will represent the "Galaxy"
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */

public class BodyDialog extends JDialog implements ActionListener, ChangeListener{
	
	private MainPanel mp;
	private JTextField body, s_mass, first_mass, second_mass, third_mass, fourth_mass;
	private JButton ok, custom;
	private JCheckBox sun, star_wars;
	private JLabel sun_mass, lvl1_mass, lvl2_mass, lvl3_mass, lvl4_mass;
	
	/**
	 * Class BodyDialog constructor.
	 * 
	 * @param mp MainPanel component 
	 **/
	public BodyDialog(MainPanel mp){
		
		super();
		this.mp = mp;
		
		setSize(new Dimension(250, 220));
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel s = new JLabel("How many bodies do you want?");
		add(s);
		s.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(Box.createVerticalStrut(5));
		
		body = new JTextField("100");
		body.setMaximumSize(new Dimension(this.getSize().width,25));
		add(body);
		
		body.setHorizontalAlignment(JLabel.CENTER);
		
		add(Box.createVerticalStrut(5));
		
		ok = new JButton("Go!");
		ok.addActionListener(this);
		ok.setMinimumSize(new Dimension(this.getSize().width, 25));
		ok.setMaximumSize(new Dimension(this.getSize().width, 25));
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(ok);
		add(Box.createVerticalStrut(5));
		
		// BARRETTA
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		Dimension d = sep.getPreferredSize();
		d.width = sep.getMaximumSize().width;
		sep.setMaximumSize(d);
		add(sep);
		
		add(Box.createVerticalStrut(5));
		
		JLabel o = new JLabel("Options:");
		o.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(o);
		
		add(Box.createVerticalStrut(5));
		
		sun = new JCheckBox("Star");
		sun.setSelected(true);
		sun.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(sun);
		
		add(Box.createVerticalStrut(5));
		
		custom = new JButton("Show Custom Parameters >>");
		custom.addActionListener(this);
		custom.setMinimumSize(new Dimension(220, 30));
		custom.setMaximumSize(new Dimension(220, 30));
		custom.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(custom);
		
		add(Box.createVerticalStrut(5));
		
		star_wars = new JCheckBox("Star Wars Mode");
		star_wars.setSelected(Util.star_wars_mode);
		star_wars.setAlignmentX(Component.CENTER_ALIGNMENT);
		star_wars.setVisible(false);
		add(star_wars);
		add(Box.createVerticalStrut(5));
		
		sun_mass = new JLabel("Star mass:");
		sun_mass.setAlignmentX(Component.CENTER_ALIGNMENT);
		sun_mass.setVisible(false);
		add(sun_mass);
		add(Box.createVerticalStrut(5));
		
		lvl1_mass = new JLabel("Lv.1 mass:");
		lvl1_mass.setAlignmentX(Component.CENTER_ALIGNMENT);
		lvl1_mass.setVisible(false);
		add(lvl1_mass);
		lvl2_mass = new JLabel("Lv.2 mass:");
		lvl2_mass.setAlignmentX(Component.CENTER_ALIGNMENT);
		lvl2_mass.setVisible(false);
		add(lvl2_mass);
		lvl3_mass = new JLabel("Lv.3 mass:");
		lvl3_mass.setAlignmentX(Component.CENTER_ALIGNMENT);
		lvl3_mass.setVisible(false);
		add(lvl3_mass);
		lvl4_mass = new JLabel("Lv.4 mass:");
		lvl4_mass.setAlignmentX(Component.CENTER_ALIGNMENT);
		lvl4_mass.setVisible(false);
		add(lvl4_mass);
		
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		
	}
	
	public void actionPerformed(ActionEvent e){
		
		JButton source = (JButton) e.getSource();
		if (source == ok){
			try{
				int n = Integer.parseInt(body.getText());
				Util.one_sun = sun.isSelected();
				Util.star_wars_mode = star_wars.isSelected();
				mp.initWithRandom(n,sun.isSelected());
			}catch(Exception ex) { 
				ex.printStackTrace();
				System.err.println(ex); 
				JOptionPane.showMessageDialog(this, "Wrong input data, a number MUST be inserted!!!", "Error", JOptionPane.ERROR_MESSAGE, null);
			}
			this.setVisible(false);
		} else if (source == custom) {
			if (custom.getText().equals("Show Custom Parameters >>")) {
				setSize(new Dimension(250, 350));
				
				sun_mass.setVisible(true);
				star_wars.setVisible(true);
				lvl1_mass.setVisible(true);
				lvl2_mass.setVisible(true);
				lvl3_mass.setVisible(true);
				lvl4_mass.setVisible(true);
				
				custom.setText("<< Hide Custom Parameters");
				
			} else {
				sun_mass.setVisible(false);
				star_wars.setVisible(false);
				lvl1_mass.setVisible(false);
				lvl2_mass.setVisible(false);
				lvl3_mass.setVisible(false);
				lvl4_mass.setVisible(false);
				custom.setText("Show Custom Parameters >>");
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
