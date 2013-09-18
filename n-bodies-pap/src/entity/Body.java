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
	 * @param p - Vector Object
	 * @param v - Vector Object
	 * @param mass - double value of the mass
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
	 * @param f - Vector Object represent the vector force
	 * @param dt
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
	 * @param that - Body Object
	 * @return Vector Object - the force vector requested
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
	 * Method getMass.
	 * It returns a String representation of the mass of the body.
	 * 
	 * @return String
	 */
	public String getMass(){
		return "" + mass;
	}
}