package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import javax.swing.*;

import support.P2d;
import support.Util;
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
		private ArrayList<P2d[]> history = new ArrayList<P2d[]>();
		
		/**
		 * Class VisualiserPanel constructor.
		 * 
		 * @param contr Controller entity
		 **/
        public VisualiserPanel(Controller contr){
            setSize(Util.VisualiserAvailableSpace());
            controller = contr;
            allbodies = controller.getAllBodiesFromContext();
            P2d[] pos = new P2d[allbodies.length];
            for (Body b : allbodies) pos[b.getIndex()] = new P2d(b.getPosition_X(), b.getPosition_Y());
            history.add(pos);
        }

        public void paint(Graphics g){
        	
        	Graphics2D g2 = (Graphics2D) g;
        	Dimension d = getSize();
        	g2.setBackground(Color.black);
        	g2.clearRect(0,0,d.width,d.height);
        	
        	double[] levels = Util.massLevels();
            
        	//g.clearRect(0,0,this.getWidth(),this.getHeight());
            // sara poi il thread visualizer che avra' in pasto il visualiser panel a richiamare il repaint ogni volta che finisce il ciclo di computazione
            
        	if (controller.tracks) {
        	
	            ArrayList<P2d[]> h = new ArrayList<P2d[]>();
	            
	            h.addAll(history);
	            
	            int j = 0;
	            P2d[] before = new P2d[allbodies.length];
	            
	            ListIterator<P2d[]> it = h.listIterator();
	            
	            while (it.hasNext()) {
	            	
	            	P2d[] all = null;
					all = it.next();
	            	
	            	for(int i=0; i<all.length; i++){
	            		
	            		int x_1, y_1, x_2, y_2;
	            		
	                	if (j == 0){
	            			
	                		before[i] = all[i];
	                		
	            		} else {
	            			
	            			x_1 = (int)before[i].x;
	            			y_1 = (int)before[i].y;
	            			
	            			x_2 = (int)all[i].x;
	            			y_2 = (int)all[i].y;
	            			
	            			double mass = allbodies[i].getMassValue();
	            			Color c = null;
	            			
	            			
	            			if (mass <= levels[0]) {
	                    		c = Util.dark_one;
	                    	} else if (mass <= levels[1]) {
	                    		c = Util.dark_two;
	                    	} else if (mass <= levels[2]) {
	                    		c = Util.dark_three;
	                    	} else if (mass <= levels[3]) {
	                    		c = Util.dark_five;
	                    	}
	            			
	            			g.setColor(c);
	            			g.drawLine(x_1, y_1, x_2, y_2);
	            			
	            			before[i] = all[i];
	                	
	            		}
	                }
	            	
	            	j++;
	            }
        	}
            
            for(int i=0; i<allbodies.length; i++){
            	int x,y,m,v_x,v_y;
            	Color c = null;
            	x = (int)allbodies[i].getPosition_X();
            	y = (int)allbodies[i].getPosition_Y();
            	m = (int)allbodies[i].getMassValue();
            	
            	v_x = (int)allbodies[i].getVelocity_X();
            	v_y = (int)allbodies[i].getVelocity_Y();
            	
            	// We divide to the range of the masses in four bands and assegnamo a color, 
            	// white indicates the smaller masses, cyan mid-small masses, blue mid-big masses and gray bigger masses. 
            	if (m <= levels[0]) {
            		c = Util.light_one;
            	} else if (m <= levels[1]) {
            		c = Util.light_two;
            	} else if (m <= levels[2]) {
            		c = Util.light_three;
            	} else if (m <= levels[3]) {
            		c = Util.light_five;
            	}
            	
            	
            	
            	if (m == Util.SUN_MASS) {
            		c = Util.sun;
            		g.setColor(c);
                	g.fillOval(x-10,y-10,20,20);
                	g.setColor(Color.red);
                	g.drawOval(x-10,y-10,20,20);
            	} else {
            		g.setColor(c);
                	g.fillOval(x-2,y-2,4,4);
                	g.setColor(c);
                	g.drawOval(x-2,y-2,4,4);
                	//g.drawLine(x, y, x+v_x, y+v_y);
            	}
            }
            
        }
        
        public void updatePositions(Body[] pos){
            synchronized(this){
                allbodies = pos;
                P2d[] positions = new P2d[pos.length];
                for (Body b : allbodies) positions[b.getIndex()] = new P2d(b.getPosition_X(), b.getPosition_Y());
                if (history.size() >= 100) history.remove(0);
                history.add(positions);
                
                repaint();
            }
        }
}