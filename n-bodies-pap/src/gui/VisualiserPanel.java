package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

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
		private ArrayList<Body[]> history = new ArrayList<Body[]>();
		/**
		 * Class VisualiserPanel constructor.
		 * 
		 * @param contr Controller entity
		 **/
        public VisualiserPanel(Controller contr){
            setSize(800,600);
            controller = contr;
            allbodies = controller.getAllBodiesFromContext();
            history.add(allbodies);
        }

        public void paint(Graphics g){
            g.clearRect(0,0,this.getWidth(),this.getHeight());
            // sara poi il thread visualizer che avra in pasto il visualiser panel a richiamare il repaint ogni volta che finisce il ciclo di computazione
            
            for (Body[] all : history) {
            	
            	System.out.println("culo");
            	
            	for(int i=0; i<all.length; i++){
                	int x,y;
                	x = (int)all[i].getPosition_X();
                	y = (int)all[i].getPosition_Y();
                	
                	g.setColor(Color.black);
                    g.drawOval(x-1, y-1, 2, 2);
                	
                }
            }
            
            System.out.println("----");
            
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
            	} else if (m <= 50) {
            		c = Color.cyan;
            	} else if (m <= 100) {
            		c = Color.blue;
            	} else if (m <= 200) {
            		c = Color.gray;
            	}
            	
            	
            	
            	if (m > 300) {
            		c = Color.yellow;
            		g.setColor(c);
                	g.fillOval(x-5,y-5,10,10);
                	g.setColor(Color.red);
                	g.drawOval(x-5,y-5,10,10);
            	} else {
            		g.setColor(c);
                	g.fillOval(x-2,y-2,4,4);
                	g.setColor(Color.black);
                	g.drawOval(x-2,y-2,4,4);
            	}
            }
            
        }
        
        public void updatePositions(Body[] pos){
            synchronized(this){
                allbodies = pos;
                history.add(pos);
            }
            repaint();
        }
}