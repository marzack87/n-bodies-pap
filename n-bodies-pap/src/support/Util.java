package support;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import entity.Body;

public class Util {
	
	public static final double SUN_MASS = 500000;
	
	public static final double RANGE_BODIES_VELOCITY = 50;
	public static final double RANGE_BODIES_MASS = 500;
	
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

	
	private Util(){
		
	}
	
	public static Dimension displayAvailableSpace() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = (int) screenSize.getWidth() - 50;
		int h = (int) screenSize.getHeight() - 50;
		return new Dimension(w, h);	
	}
	
	public static Dimension VisualiserAvailableSpace() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = (int) screenSize.getWidth() - 50;
		int h = (int) screenSize.getHeight() - 50;
		return new Dimension(w - 180, h - 30);	
	}
	
	public static double[] massLevels(){
		double[] levels = new double[4];
		levels[0] = RANGE_BODIES_MASS / 10;
		levels[1] = RANGE_BODIES_MASS / 4;
		levels[2] = RANGE_BODIES_MASS / 2;
		levels[3] = RANGE_BODIES_MASS;
		return levels;
		
	}
	
}
