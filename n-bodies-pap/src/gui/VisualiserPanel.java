package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
            P2d[] pos = new P2d[allbodies.length];
            for (Body b : allbodies) pos[b.getIndex()] = new P2d(b.getPosition_X(), b.getPosition_Y());
            history.add(pos);
            gpanel = gp;
        }

        public void paint(Graphics g){
        	
        	//Util.last_paint_time = System.currentTimeMillis();
        	
        	//System.out.println("Paint! " + Util.last_paint_time);
        	
        	//gpanel.updatePerformanceData();
        	/*
        	System.out.println("Tot iteration: " + Util.total_iteration);
        	System.out.println("Last iter time: " + Util.last_iter_time);
        	System.out.println("FPS: " + (1e9 / Util.last_iter_time));
        	*/
        	Graphics2D g2 = (Graphics2D) g;
        	Dimension d = getSize();
        	g2.setBackground(Color.black);
        	g2.clearRect(0,0,d.width,d.height);
            
        	if (controller.tracks) {
        	
	            ArrayList<P2d[]> h = new ArrayList<P2d[]>();
	            h.addAll(history);
	            
	            int j = 0;
	            P2d[] before = new P2d[allbodies.length];
	            ListIterator<P2d[]> it = h.listIterator();
	            
	            while (it.hasNext()) {
	            	
	            	P2d[] all = null;
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
            	
            	// We divide to the range of the masses in four bands and assegnamo a color, 
            	// white indicates the smaller masses, cyan mid-small masses, blue mid-big masses and gray bigger masses. 
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
            		c = Util.sun;
            		r = Util.SUN_RADIUS;
            		g.setColor(c);
            		g.fillOval((int)x-r,(int)y-r, r*2, r*2);
                	g.setColor(Color.red);
                	g.drawOval((int)x-r,(int)y-r, r*2, r*2);
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
        
        public void updatePositions(Body[] pos){
                allbodies = pos;
                P2d[] positions = new P2d[pos.length];
                for (Body b : allbodies) positions[b.getIndex()] = new P2d(b.getPosition_X(), b.getPosition_Y());
                if (history.size() >= 50) history.remove(0);
                history.add(positions);
                //if ((System.currentTimeMillis() - Util.last_paint_time) > 33) repaint();
                repaint();
        }
        
        public void reset(){
        	history.clear();
        }
}