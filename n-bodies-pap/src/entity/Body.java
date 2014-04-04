package entity;

import support.Vector;

/**
 * Class Body. 
 * Represent the body single entity. Each body has three private fields that stand for his properties:
 * - Vector p, position vector given by the coordination x,y
 * - Vector v, velocity vector
 * - double mass, the value of the mass of the body(planet)  
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class Body { 
	private Vector p; //position
	private Vector v; //velocity
	private double mass; //mass
	
	/**
	 * Class Body constructor.
	 * Initialize the properties of the body with the object passed as parameter.
	 * 
	 * @param p - Position vector
	 * @param v - Velocity vector
	 * @param mass - Value of the mass of the body
	 */
	public Body(Vector p, Vector v, double mass) {
		this.p = p;
		this.v = v;
		this.mass = mass; 
	}
	
	/**
	 * Method move.
	 * Permit to update the position of the body based on the force applied to the body by the others
	 * and the discrete time.
	 * 
	 * @param f - Force vector
	 * @param dt - Time
	 */
	public void move(Vector f, double dt) { 
		Vector a = f.times(1/mass);
		v = v.plus(a.times(dt));
		p = p.plus(v.times(dt));
	}
	
	/**
	 * Method forceFrom.
	 * It calculates the force between the body and an another body passed as param 
	 * 
	 * @param that - Body
	 * @return Vector object - The force vector requested
	 */
	public Vector forceFrom(Body that) {
		double G = 6.67e-11;
		Vector delta = that.p.minus(this.p);
		double dist = delta.magnitude();
		double F = (G * this.mass * that.mass) / (dist * dist); 
		return delta.direction().times(F);
	} 
	
	/**
	 * Method getPosition.
	 * It returns a String representation of the Vector position of the body calling the method toString()
	 * of the Vector Class.
	 * 
	 * @return String
	 */
	public String getPosition(){
		return p.toString();
	}
	
	/**
	 * Method getPosition_X.
	 * It returns a double with the value of the coordinate X of the Position of the Body.
	 * 
	 * @return double
	 */
	public double getPosition_X(){
		return p.cartesian(0);
	}
	
	/**
	 * Method getPosition_Y.
	 * It returns a double with the value of the coordinate Y of the Position of the Body.
	 * 
	 * @return double
	 */
	public double getPosition_Y(){
		return p.cartesian(1);
	}
	
	/**
	 * Method getVelocity.
	 * It returns a String representation of the Vector velocity of the body calling the method toString()
	 * of the Vector Class.
	 * 
	 * @return String
	 */
	public String getVelocity(){
		return v.toString();
	}
	
	/**
	 * Method getVelocity_X.
	 * It returns a double with the value of the coordinate X of the Velocity of the Body.
	 * 
	 * @return double
	 */
	public double getVelocity_X(){
		return v.cartesian(0);
	}
	
	/**
	 * Method getVelocity_Y.
	 * It returns a double with the value of the coordinate Y of the Velocity of the Body.
	 * 
	 * @return double
	 */
	public double getVelocity_Y(){
		return v.cartesian(1);
	}
	
	/**
	 * Method getMass.
	 * It returns a String representation of the mass of the body.
	 * 
	 * @return String
	 */
	public String getMass(){
		return "" + mass;
	}
	
	/**
	 * Method getMassValue.
	 * It returns the double value of the mass of the body.
	 * 
	 * @return double
	 */
	public double getMassValue(){
		return mass;
	}
}