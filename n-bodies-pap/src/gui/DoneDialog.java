package gui;

import java.awt.*;

import javax.swing.*;

import support.Util;

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
		if(Util.star_wars_theme){
			setSize(new Dimension(350, 300));
			setLayout(new BorderLayout());
			JLabel s = new JLabel("Your simulation stats was successfully saved.");
			add(s,BorderLayout.NORTH);
			s.setHorizontalAlignment(JLabel.CENTER);
			
			ImageIcon logo = createImageIcon("images/darth.gif", "Dance");
			JLabel logosw = new JLabel(logo);
			add(logosw,BorderLayout.CENTER);
			
			JButton well = new JButton("Well done!");
			well.addActionListener(this);
			add(well,BorderLayout.SOUTH);
			setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
		}else{
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
	}
	
	public void actionPerformed(ActionEvent e){
		System.exit(0);
	}
	
	/** 
	 * Private method createImageIcon.
	 * <p>
	 * Returns an ImageIcon, or null if the path was invalid.
	 * 
	 * @param path The path of the image file 
	 * @param description Description of the image
	 */
	private ImageIcon createImageIcon(String path,String description) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}

}
