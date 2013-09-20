package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class MainPanel extends JPanel implements ActionListener{
	
	private JButton crea, apri;
	private Box BOX, BOX_button;
	
	public MainPanel(){
		
		crea 	= new JButton("Random Yo!");
		apri 	= new JButton("Read a fuckin' file!");
		
		apri.addActionListener(this);
		crea.addActionListener(this);
		
		BOX = new Box(BoxLayout.Y_AXIS);
		BOX_button = new Box(BoxLayout.X_AXIS);
		BOX_button.add(crea);
		BOX_button.add(apri);
		BOX.add(BOX_button);
		add(BOX);
	}
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == apri){	
			JFileChooser choose = new JFileChooser(System.getProperty("user.dir"));
			choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
			choose.setFileFilter(new TxtFileFilter());
			choose.setApproveButtonText("Apri");
			choose.setPreferredSize(new Dimension(400,400));
			int value=choose.showOpenDialog(this);
			if (value != JFileChooser.APPROVE_OPTION){
				JOptionPane.showMessageDialog(this, "Nessun file aperto", "Errore", JOptionPane.ERROR_MESSAGE, null);
			}else{
				File f = choose.getSelectedFile();
				/*
				exams_frame = new ExamFrame(f);
				exams_frame.setVisible(true);
				*/
			}
		}else if (source == crea){
			System.out.println("pulsante crea");
			/*
			exams_frame = new ExamFrame();
			exams_frame.setVisible(true);
			*/
		}
	}
}
class TxtFileFilter extends javax.swing.filechooser.FileFilter {
    
	public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
    }
    
    public String getDescription() {
        return ".txt files";
    }

}
