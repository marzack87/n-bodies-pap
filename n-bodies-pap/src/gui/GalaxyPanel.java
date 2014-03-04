package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Class GalaxyPanel.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class GalaxyPanel extends JPanel implements ActionListener {

	private JButton add,remove,start,pause,stop,step;
	private VisualiserPanel visualise;
	private Box BOX, BOX_button, BOX_visualiser;
	
	public GalaxyPanel(){
		
		add = new JButton("Add Body");
		remove = new JButton("Remove Body");
		start = new JButton("Start");
		pause = new JButton("Pause");
		stop = new JButton("Stop");
		step = new JButton("Next step");
		
		add.addActionListener(this);
		remove.addActionListener(this);
		start.addActionListener(this);
		pause.addActionListener(this);
		stop.addActionListener(this);
		step.addActionListener(this);
		
		visualise = new VisualiserPanel();
		//Inserisco il visualise nel posto giusto
		//Creo thread visualizzatore che avra' il compito di disegnare per la prima volta e di ridisegnare poi i body nel visualise
		
		/*BOX = new Box(BoxLayout.X_AXIS);
		BOX_button = new Box(BoxLayout.Y_AXIS);
		BOX_button.add(add);
		BOX_button.add(remove);
		BOX_button.add(start);
		BOX_button.add(pause);
		BOX_button.add(stop);
		BOX_button.add(step);
		BOX_visualizer = new Box(BoxLayout.Y_AXIS);
		//qui ci vorrei mettere il visualizzatore dei corpi ma ho scazzato usando il box. Devo controllare meglio.
		BOX.add(BOX_button);
		add(BOX);
		*/ //DA SOSTITUIRE CON IL LAYOUT GIUSTO
	}

	
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
