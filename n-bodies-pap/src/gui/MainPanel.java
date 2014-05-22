package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.filechooser.FileFilter;
import javax.swing.*;

import support.Util;
import entity.Controller;


/**
 * Class MainPanel.
 * <p>
 * Component containing the two buttons for selecting the method of initialization of the bodies.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class MainPanel extends JPanel implements ActionListener, ItemListener{
	
	private JButton create, open;
	private JCheckBox star_wars, sun;
	private JComboBox<String> cb;  
	private Controller contr;
	private JLabel o;
	final static String ONE = "Collision Mode";
    final static String TWO = "Softening Param Mode";
	
	/**
	 * Class MainPanel default constructor.
	 * 
	 * @param contr Controller entity
	 **/
	public MainPanel(Controller contr){
		
		this.contr = contr;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createVerticalStrut(5));
		
		JLabel s = new JLabel("How do you want to create the Bodies?");
		add(s);
		s.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(Box.createVerticalStrut(5));
		
		create = new JButton("Create Randomly");
		create.setMinimumSize(new Dimension(220, 30));
		create.setMaximumSize(new Dimension(220, 30));
		create.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(create);
		create.addActionListener(this);
		
		open = new JButton("Read datas from file");
		open.setMinimumSize(new Dimension(220, 30));
		open.setMaximumSize(new Dimension(220, 30));
		open.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(open);
		open.addActionListener(this);
		
		add(Box.createVerticalStrut(5));
		
		// BARRETTA
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		Dimension d = sep.getPreferredSize();
		d.width = sep.getMaximumSize().width;
		sep.setMaximumSize(d);
		sep.setVisible(true);
		add(sep);
		
		add(Box.createVerticalStrut(5));
		
		o = new JLabel("Options:");
		o.setAlignmentX(Component.CENTER_ALIGNMENT);
		o.setVisible(true);
		add(o);
		
		String comboBoxItems[] = { ONE, TWO };
		cb = new JComboBox<String>(comboBoxItems);
		cb.setEditable(false);
		cb.setMinimumSize(new Dimension(220, 30));
		cb.setMaximumSize(new Dimension(220, 30));
		cb.addActionListener(this);
		add(cb);
		
		add(Box.createVerticalStrut(5));
		
		star_wars = new JCheckBox("Star Wars Theme");
		star_wars.setSelected(Util.star_wars_theme);
		star_wars.setAlignmentX(Component.CENTER_ALIGNMENT);
		star_wars.addItemListener(this);
		add(star_wars);
		
		sun = new JCheckBox("Death Star");
		sun.setSelected(Util.one_sun);
		sun.setAlignmentX(Component.CENTER_ALIGNMENT);
		sun.addItemListener(this);
		add(sun);
		
		add(Box.createVerticalStrut(5));
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		// Initialize the body through file.
		if (source == open){	
			JFileChooser choose = new JFileChooser(System.getProperty("user.dir"));
			choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
			choose.setFileFilter(new TxtFileFilter());
			choose.setApproveButtonText("Open");
			choose.setPreferredSize(new Dimension(400,400));
			int value=choose.showOpenDialog(this);
			// Can't it open the file?! Send an error
			if (value != JFileChooser.APPROVE_OPTION){
				JOptionPane.showMessageDialog(this, "No files open", "Error", JOptionPane.ERROR_MESSAGE, null);
			}else{
				// File is opened
				File f = choose.getSelectedFile();
				log("Open Button");
				
				contr.genFromFile(f);
				
				// Open GALAXY GUI
				GalaxyFrame gframe = new GalaxyFrame("N-Body Simulation: GALAXY", contr);
				gframe.setVisible(true);
				
			}
		}
		// Initialize the body randomly.
		else if (source == create){
			log("Create Button");
			
			JDialog body = new BodyDialog(this);
			body.setVisible(true);
			
		}
		else if (source == cb){
			String s = (String) cb.getSelectedItem();
			if(s.equals("Softening Param Mode")){
				Util.soft_param_mode = true;
			}else{
				Util.soft_param_mode = false;
			}
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();

	    if (source == star_wars) {
	    	if(!star_wars.isSelected()){
				Util.star_wars_theme = false;
				sun.setText("Sun");
			}else{
				Util.star_wars_theme = true;
				sun.setText("Death Star");
			}
	    }else if(source == sun) {
	    	if(!sun.isSelected()){
				Util.one_sun = false;
			}else{
				Util.one_sun = true;
			}
	    }
		
	}
	
	/**
	 * Method initWithRandom.
	 * <p>
	 * Initialize the simulation calling the initWithRandomData method of the Controller class.
	 * 
	 * @param number - Number of Body taken from the BodyDialog.
	 */
	public void initWithRandom(int number, boolean sun){

		contr.initWithRandomData(number, sun);
		
		GalaxyFrame gframe = new GalaxyFrame("N-Body Simulation: GALAXY", contr);
		gframe.setVisible(true);
	}
	
	/**
     * Private method log.
     * <p>
     * Prints to the console a log of the activity of the MainPanel.
     * 
     * @param msg The message to be printed
     */
	private void log(String msg){
        System.out.println("[MAIN PANEL] "+msg);
    }
}



/**
 * Inner class TxtFileFilter.
 * <p>
 * Represent the filename extension filter of the input file. Only .txt files are accepted.
 */
class TxtFileFilter extends FileFilter {
    
	public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
    }
    
    public String getDescription() {
        return ".txt files";
    }

}


