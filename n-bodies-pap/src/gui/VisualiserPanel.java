package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.*;

import support.Util;
import support.V2d;
import entity.Body;
import entity.Controller;

/**
 * Class VisualiserPanel.
 * <p>
 * Component where all the bodies and their interactions are visualized.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
   
public class VisualiserPanel extends JPanel {
		
		private Controller controller;
		private Body[] allbodies;
		private ArrayList<V2d[]> history = new ArrayList<V2d[]>();
		private GalaxyPanel gpanel; 
		
		/**
		 * Class VisualiserPanel constructor.
		 * 
		 * @param contr Controller entity
		 **/
        public VisualiserPanel(Controller contr, GalaxyPanel gp){
            setSize(Util.VisualiserAvailableSpace());
            controller = contr;
            allbodies = controller.getAllBodiesFromContext();
            V2d[] pos = new V2d[allbodies.length];
            for (Body b : allbodies) pos[b.getIndex()] = new V2d(b.getPosition_X(), b.getPosition_Y());
            history.add(pos);
            gpanel = gp;
        }

        public void paint(Graphics g){
        	
        	//Util.last_paint_time = System.currentTimeMillis();
        	
        	//System.out.println("Paint! " + Util.last_paint_time);
        	
        	gpanel.updatePerformanceData();
        	
        	Graphics2D g2 = (Graphics2D) g;
        	Dimension d = getSize();
        	g2.setBackground(Color.black);
        	g2.clearRect(0,0,d.width,d.height);
            
        	if (controller.tracks) {
        	
	            ArrayList<V2d[]> h = new ArrayList<V2d[]>();
	            h.addAll(history);
	            
	            int j = 0;
	            V2d[] before = new V2d[allbodies.length];
	            ListIterator<V2d[]> it = h.listIterator();
	            
	            while (it.hasNext()) {
	            	
	            	V2d[] all = null;
					all = it.next();
					if (all != null){
		            	for(int i=0; i<all.length; i++){
		            		
		            		double x_1, y_1, x_2, y_2;
		                	if (j == 0){
		                		before[i] = all[i];
		            		} else {
		            			x_1 = Math.round(before[i].x*Util.scaleFact);
		            			y_1 = Math.round(before[i].y*Util.scaleFact);
		            			
		            			x_2 = Math.round(all[i].x*Util.scaleFact);
		            			y_2 = Math.round(all[i].y*Util.scaleFact);
		            			
		            			double mass = allbodies[i].getMassValue();
		            			Color c = null;
		            			
		            			if (Math.abs(mass-Util.SMALL_MASS) < 1e-13) {
		                    		c = Util.dark_one;
		                    	} else if (Math.abs(mass-Util.MIDSMALL_MASS) < 1e-13) {
		                    		c = Util.dark_two;
		                    	} else if (Math.abs(mass-Util.MIDBIG_MASS) < 1e-13) {
		                    		c = Util.dark_three;
		                    	} else if (Math.abs(mass-Util.BIG_MASS) < 1e-13) {
		                    		c = Util.dark_five;
		                    	}
		            			
		            			g.setColor(c);
		            			g.drawLine((int)x_1, (int)y_1, (int)x_2, (int)y_2);
		            			
		            			before[i] = all[i];
		                	
		            		}
		                }
					}
	            	j++;
	            }
        	}
            
            for(int i=0; i<allbodies.length; i++){
            	double x,y,m,v_x,v_y;
            	Color c = null;
            	x = Math.round(allbodies[i].getPosition_X()*Util.scaleFact);
            	y = Math.round(allbodies[i].getPosition_Y()*Util.scaleFact);
            	m = allbodies[i].getMassValue();
            	
            	v_x = Math.round(allbodies[i].getVelocity_X()*Util.scaleFact);
            	v_y = Math.round(allbodies[i].getVelocity_Y()*Util.scaleFact);
            	
            	// We divide to the range of the masses in four bands and give them a color,  
            	if (Math.abs(m-Util.SMALL_MASS) < 1e13) {
            		c = Util.light_one;
            	} else if (Math.abs(m-Util.MIDSMALL_MASS) < 1e-13) {
            		c = Util.light_two;
            	} else if (Math.abs(m-Util.MIDBIG_MASS) < 1e-13) {
            		c = Util.light_three;
            	} else if (Math.abs(m-Util.BIG_MASS) < 1e-13) {
            		c = Util.light_five;
            	}
            	
            	int r;
            
            	if (Math.abs(m-Util.SUN_MASS) < 1e-3) {
            		if (Util.star_wars_mode){
            			r = Util.DEATHSTAR_RADIUS;
            			java.net.URL imgURL = getClass().getResource("images/DeathStar.png");
            			ImageIcon img = new ImageIcon(imgURL, "DeathStar");
            			g.drawImage(img.getImage(), (int)x-r, (int)y-r, r*2, r*2, this);
            		} else {
            			c = Util.sun;
	            		r = Util.SUN_RADIUS;
	            		g.setColor(c);
	            		g.fillOval((int)x-r,(int)y-r, r*2, r*2);
	                	g.setColor(Color.red);
	                	g.drawOval((int)x-r,(int)y-r, r*2, r*2);
            		}
            	} else {
            		r = Util.BODY_RADIUS;
            		g.setColor(c);
                	g.fillOval((int)x-r,(int)y-r, r*2, r*2);
                	g.setColor(c);
                	g.drawOval((int)x-r,(int)y-r, r*2, r*2);
                	//draw velocity
                	if (controller.velocity) g.drawLine((int)x, (int)y, (int)x+(int)v_x, (int)y+(int)v_y);
            	}
            }
            
        }
        
        /**
         * Method reset.
         * <p>
         * It reset the array of the older position of the body.<br>
         * That array permits to paint the tracks of each body.
         */
        public void reset(){
        	history.clear();
        }
        
        /**
         * Method updatePositions
         * <p>
         * Called by the Visualiser thread to update the body position array stored and to repaint the VisualiserPanel
         * 
         * @param pos
         */
        public void updatePositions(Body[] pos){
                allbodies = pos;
                V2d[] positions = new V2d[pos.length];
                for (Body b : allbodies) positions[b.getIndex()] = new V2d(b.getPosition_X(), b.getPosition_Y());
                if (history.size() >= 50) history.remove(0);
                history.add(positions);
                //if ((System.currentTimeMillis() - Util.last_paint_time) > 33) repaint();
                repaint();
        }
        
}