package gui;

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
            
            
            // DA A.RICCI
            /*synchronized (this){
	            if (positions!=null){
	                for (int i=0; i<positions.length; i++){
		                P2d p = positions[i];
		                int x0 = (int)(180+p.x*180);
		                int y0 = (int)(180-p.y*180);
		                g.drawOval(x0,y0,20,20);
		            }
	            }
            }
        }
        
        public void updatePositions(P2d[] pos){
            synchronized(this){
                positions = pos;
            }
            repaint();
        }
    }
*/}
}