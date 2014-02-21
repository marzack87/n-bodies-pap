package gui;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import javax.swing.*;
   
public class VisualiserPanel extends JPanel {
	
	
        public VisualiserPanel(){
            setSize(400,400);
        }

        public void paint(Graphics g){
            g.clearRect(0,0,400,400);
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