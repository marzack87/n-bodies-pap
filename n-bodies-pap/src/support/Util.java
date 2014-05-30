package support;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *  Class Util.
 *  <p>
 *  Helper class that stores some of the data of the simulation
 *  
 *  @author Richiard Casadei, Marco Zaccheroni
 */

public class Util {
	
	public static int n_bodies = 0;
	public static boolean one_sun = false;
	
	public static boolean star_wars_theme = true;
	public static boolean soft_param_mode = false;
	
	public static double t_start, t_stop;
	
	// System info
	public static final String nameOS = System.getProperty("os.name");  
	public static final String versionOS = System.getProperty("os.version");
	public static final int ncores = Runtime.getRuntime().availableProcessors()/2;
	public static final String architectureOS = System.getProperty("os.arch");
	
	public static final double SUN_MASS = 5*Math.pow(10, 30);
	
	public static final double EPS = Math.pow(10, 15);
	
	public static final int SUN_RADIUS = 6;
	public static final int DEATHSTAR_RADIUS = 16;
	public static final int BODY_RADIUS = 2;
	
	// Masses range
	// Jupiter more or less is 2e27
	// Mercury more or less is 3e23
	public static final double SMALL_MASS = Math.pow(10, 25);
	public static final double MIDSMALL_MASS = 3*Math.pow(10, 26);
	public static final double MIDBIG_MASS = 6*Math.pow(10, 27);
	public static final double BIG_MASS = Math.pow(10, 28);
	
	public static final double[] MASSES = {SMALL_MASS, MIDSMALL_MASS, MIDBIG_MASS, BIG_MASS};
	
	public static final double RANGE_BODIES_VELOCITY = 2*Math.pow(10, 3);
	
	// emerald
	public static final Color one = new Color(0x00CD7B);
	public static final Color dark_one = new Color(0x009D63);
	public static final Color light_one = new Color(0x00FCA2);
	
	// peter river
	public static final Color two = new Color(0x48A3DA);
	public static final Color dark_two = new Color(0x20719D);
	public static final Color light_two = new Color(0x39B5FA);
	
	// amethyst
	public static final Color three = new Color(0x9F57B1);
	public static final Color dark_three = new Color(0x7D3F84);
	public static final Color light_three = new Color(0xEC7CF8);
	
	// wet asphalt
	public static final Color four = new Color(0x34495C);
	public static final Color dark_four = new Color(0x253542);
	public static final Color light_four = new Color(0x6A91B1);
	
	// alizarin
	public static final Color five = new Color(0xEB4942);
	public static final Color dark_five = new Color(0xA72C2E);
	public static final Color light_five = new Color(0xFF8982);
	
	// sun flower
	public static final Color sun = new Color(0xF1C442);

	public static final int MAX_SCALE = 7;
	public static final int MIN_SCALE = 1;
	public static final int MID_SCALE = (MAX_SCALE%2 == 0) ? (MAX_SCALE/2) : (MAX_SCALE/2) + 1;
	
	public static final double GALAXY_RADIUS = 5*Math.pow(10, 8);
	
	public static final double DEFAULT_DT = 0.1;
	
	public static double last_iter_time = 0;
	public static int total_iteration = 0;
	
	public static double last_paint_time = 0;
	public static double last_paint_interval = 0;
	
	public static final double scaleFact = (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 50)/Util.GALAXY_RADIUS;
	
	/**
     * Method displayAvailableSpace.
     * <p>
     * It return the dimension of the screen.
     * 
     * @return Dimension(w,h)	New Dimension object initialized with the screen width and height
     */
	public static final Dimension displayAvailableSpace() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = (int) screenSize.getWidth() - 50;
		int h = (int) screenSize.getHeight() - 50;
		return new Dimension(w, h);	
	}
	
	/**
     * Method VisualiserAvailableSpace.
     * <p>
     * It return the dimension of the screen avaible for the VisualiserPanel.
     * 
     * @return Dimension(w,h)	New Dimension object initialized with the screen width and height without the space occupied by the GalaxyPanel
     */
	public static final Dimension VisualiserAvailableSpace() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = (int) screenSize.getWidth() - 50;
		int h = (int) screenSize.getHeight() - 50;
		return new Dimension(w - 180, h - 30);	
	}
	
}
