package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import javax.swing.*;

import entity.Controller;


/**
 * Class MainPanel.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class MainPanel extends JPanel implements ActionListener{
	
	private JButton crea, apri;
	private Box BOX, BOX_button;
	
	public MainPanel(){
		
		crea = new JButton("Create Randomly");
		apri = new JButton("Read datas from file");
		
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
		// Scegliamo di inizializzare i body utilizzando un file.
		if (source == apri){	
			JFileChooser choose = new JFileChooser(System.getProperty("user.dir"));
			choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
			choose.setFileFilter(new TxtFileFilter());
			choose.setApproveButtonText("Apri");
			choose.setPreferredSize(new Dimension(400,400));
			int value=choose.showOpenDialog(this);
			// Il file e' stato aperto?! No quindi..
			if (value != JFileChooser.APPROVE_OPTION){
				JOptionPane.showMessageDialog(this, "Nessun file aperto", "Errore", JOptionPane.ERROR_MESSAGE, null);
			}else{
				// Il file e' stato aperto
				File f = choose.getSelectedFile();
				System.out.println("pulsante apri");
				Controller contr = new Controller();
				contr.initAllWithFile(f);
				contr.startSimulation();
				
				/* Una volta creati i corpi chiudiamo il main panel e dovremmo visualizzare la nuova schermata 
				di visualizzazione della galassia con i vari pulsanti di star ecc ecc.	
				*/
			}
		}
		// Scegliamo di inizializzare i body in modo random.
		else if (source == crea){
			System.out.println("pulsante crea");
			Controller contr = new Controller();
			contr.initAll();
			contr.startSimulation();
			
			/* Una volta creati i corpi chiudiamo il main panel e dovremmo visualizzare la nuova schermata 
			di visualizzazione della galassia con i vari pulsanti di star ecc ecc.	
			*/
		}
	}
}

/**
 * Inner class TxtFileFilter.
 * Represent the extention filter of the input file. Only .txt files are accepted. 
 */
class TxtFileFilter extends FileFilter {
    
	public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
    }
    
    public String getDescription() {
        return ".txt files";
    }

}
