package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.filechooser.FileFilter;
import javax.swing.*;

import entity.Controller;
import entity.Generator;
import support.Context;


/**
 * Class MainPanel.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class MainPanel extends JPanel implements ActionListener{
	
	private JButton create, open;
	private Box BOX, BOX_button;
	private Context context;
	
	/**
	 * Class MainPanel default constructor.
	 *
	 **/
	public MainPanel(Context cont){
		create = new JButton("Create Randomly");
		open = new JButton("Read datas from file");
		
		context = cont;
		
		open.addActionListener(this);
		create.addActionListener(this);
		
		BOX = new Box(BoxLayout.LINE_AXIS);
		BOX_button = new Box(BoxLayout.X_AXIS);
		BOX_button.add(create);
		BOX_button.add(open);
		BOX.add(BOX_button);
		add(BOX);
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
				System.out.println("Open button");
				Generator gen = new Generator(context);
				gen.initFromFile(f);
				
				// Open GALAXY GUI
				GalaxyFrame gframe = new GalaxyFrame("N-Body Simulation: GALAXY", context);
				gframe.setVisible(true);
				
			}
		}
		// Initialize the body randomly.
		else if (source == create){
			System.out.println("Create button");
			Generator gen = new Generator(context);
			gen.initWithRandomData(100);
			
			GalaxyFrame gframe = new GalaxyFrame("N-Body Simulation: GALAXY", context);
			gframe.setVisible(true);
			
		}
	}
}

/**
 * Inner class TxtFileFilter.
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
