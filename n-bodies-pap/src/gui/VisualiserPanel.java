package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

import entity.Body;
import entity.Controller;

/**
 * Class VisualiserPanel
 * Component where all the bodies and their interactions are visualized.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 * 
 * @see java.awt
 * @see javax.swing
 */
   
public class VisualiserPanel extends JPanel {
		
		private Controller controller;
		private Body[] allbodies;
		/**
		 * Class VisualiserPanel constructor.
		 * 
		 * @param contr Controller entity
		 **/
        public VisualiserPanel(Controller contr){
            setSize(800,600);
            controller = contr;
            allbodies = controller.getAllBodiesFromContext();
        }

        public void paint(Graphics g){
            g.clearRect(0,0,800,600);
            // sara poi il thread visualizer che avra in pasto il visualiser panel a richiamare il repaint ogni volta che finisce il ciclo di computazione
            
            for(int i=0; i<allbodies.length; i++){
            	int x,y,m;
            	Color c = null;
            	x = (int)allbodies[i].getPosition_X();
            	y = (int)allbodies[i].getPosition_Y();
            	m = (int)allbodies[i].getMassValue();
            	
            	// We divide to the range of the masses in four bands and assegnamo a color, 
            	// white indicates the smaller masses, cyan mid-small masses, blue mid-big masses and gray bigger masses. 
            	if (m <= 10) {
            		c = Color.white;
            	} else if (m <= 15) {
            		c = Color.cyan;
            	} else if (m <= 20) {
            		c = Color.blue;
            	} else if (m <= 25) {
            		c = Color.gray;
            	}
            	
            	g.setColor(c);
            	g.fillOval(x,y,4,4);
            	g.setColor(Color.black);
            	g.drawOval(x,y,4,4);
            }
            
        }
        
        public void updatePositions(Body[] pos){
            synchronized(this){
                allbodies = pos;
            }
            repaint();
        }
}