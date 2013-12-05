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

	private JButton add,remove,pause,stop,step;
	private Box BOX, BOX_button, BOX_visualizer;
	
	public GalaxyPanel(){
		
		add = new JButton("Add Body");
		remove = new JButton("Remove Body");
		pause = new JButton("Pause");
		stop = new JButton("Stop");
		step = new JButton("Next step");
		
		add.addActionListener(this);
		remove.addActionListener(this);
		pause.addActionListener(this);
		stop.addActionListener(this);
		step.addActionListener(this);
		
		BOX = new Box(BoxLayout.X_AXIS);
		BOX_button = new Box(BoxLayout.Y_AXIS);
		BOX_button.add(add);
		BOX_button.add(remove);
		BOX_button.add(pause);
		BOX_button.add(stop);
		BOX_button.add(step);
		
		/*
		BOX_visualizer = new Box(BoxLayout.Y_AXIS);
		//qui ci vorrei mettere il visualizzatore dei corpi ma ho scazzato usando il box. Devo controllare meglio.
		*/
		BOX.add(BOX_button);
		add(BOX);
	}

	
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
