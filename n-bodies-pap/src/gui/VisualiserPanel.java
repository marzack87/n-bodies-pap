package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

import entity.Body;
import support.Context;
   
public class VisualiserPanel extends JPanel {
		
		private Context context;
	
        public VisualiserPanel(Context cont){
            setSize(800,600);
            context = cont;
            
        }

        public void paint(Graphics g){
            g.clearRect(0,0,800,600);
            // dovro leggere dal context tutti i body con le rispettive posizioni e disegnarli
            // questa sara la rappresentazione dello stato iniziale dei corpi.
            
            // sara poi il thread visualizer che avra in pasto il visualiser panel a richiamare il repaint ogni volta che finisce il ciclo di computazione
            
            for(int i=0; i<context.allbodies.length; i++){
            	int x,y,m;
            	Color c = null;
            	x = (int)context.allbodies[i].getPosition_X();
            	y = (int)context.allbodies[i].getPosition_Y();
            	m = (int)context.allbodies[i].getMassValue();
            	
            	if (m <= 10) {
            		c = Color.WHITE;
            	} else if (m <= 15) {
            		c = Color.CYAN;
            	} else if (m <= 20) {
            		c = Color.BLUE;
            	} else if (m <= 25) {
            		c = Color.BLACK;
            	}
            	
            	g.setColor(c);
            	g.fillOval(x,y,5,5);
            	g.setColor(Color.BLACK);
            	g.drawOval(x,y,5,5);
            }
            
        }
        
        /*public void updatePositions(P2d[] pos){
            synchronized(this){
                positions = pos;
            }
            repaint();
        }*/
}