package entity;

import support.*;

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
	private P2d p; //position
	private V2d v; //velocity
	private double mass; //mass
	private int index;
	
	/**
	 * Class Body default constructor.
	 * Initialize the properties of the body with the object passed as parameter.
	 * 
	 * @param p Position vector
	 * @param v Velocity vector
	 * @param mass Value of the mass of the body
	 * 
	 * @see support.Vector
	 */
	public Body(P2d p, V2d v, double mass, int index) {
		this.p = p;
		this.v = v;
		this.mass = mass;
		this.index = index;
	}
	
	/**
	 * Method move.
	 * Permit to update the position of the body based on the force applied to the body by the others
	 * and the discrete time.
	 * 
	 * @param f Force vector
	 * @param dt Time
	 * 
	 * @see support.Vector
	 */
	public void move(V2d f) { 
		V2d a = f.mul(1/mass);
		v = v.sum(a);
		p = p.sum(v.sum(a.mul(1/2)));
	}
	
	/**
	 * Method forceFrom.
	 * It calculates the force between the body and an another body passed as param 
	 * 
	 * @param that Body passed
	 * @return Vector object The force vector requested
	 * 
	 * @see entity.Body
	 * @see support.Vector
	 */
	public V2d forceFrom(Body that) {
		double G = 6.67/*e-11*/;
		V2d p_this = new V2d(this.p.x, this.p.y);
		V2d p_that = new V2d(that.p.x, that.p.y);
		V2d delta = p_that.min(p_this);
		double dist = that.p.dist(this.p) /*/ 1e10*/;
		double F = (G * (this.mass * 1/*e20*/) * (that.mass * 1/*e20*/)) / (dist * dist);
		
		V2d delta_normalized = delta.getNormalized();
		V2d Force = delta_normalized.mul(F);
		
		return Force;
	} 
	 
	/**
	 * Method getPosition.
	 * It returns a String representation of the Vector position of the body calling the method toString()
	 * of the Vector Class.
	 * 
	 * @return String
	 * 
	 * @see String 
	 */
	public String getPosition(){
		return p.toString();
	}
	
	/**
	 * Method getPosition_X.
	 * It returns a double with the value of the coordinate X of the Position of the Body.
	 * 
	 * @return double Value of the position coordinate X
	 */
	public double getPosition_X(){
		return p.x;
	}
	
	/**
	 * Method getPosition_Y.
	 * It returns a double with the value of the coordinate Y of the Position of the Body.
	 * 
	 * @return double Value of the position coordinate Y
	 */
	public double getPosition_Y(){
		return p.y;
	}
	
	/**
	 * Method getVelocity.
	 * It returns a String representation of the Vector velocity of the body calling the method toString()
	 * of the Vector Class.
	 * 
	 * @return String
	 * 
	 * @see String
	 */
	public String getVelocity(){
		return v.toString();
	}
	
	/**
	 * Method getVelocity_X.
	 * It returns a double with the value of the coordinate X of the Velocity of the Body.
	 * 
	 * @return double Value of the velocity coordinate X
	 */
	public double getVelocity_X(){
		return v.x;
	}
	
	/**
	 * Method getVelocity_Y.
	 * It returns a double with the value of the coordinate Y of the Velocity of the Body.
	 * 
	 * @return double Value of the velocity coordinate Y
	 */
	public double getVelocity_Y(){
		return v.y;
	}
	
	/**
	 * Method getMass.
	 * It returns a String representation of the mass of the body.
	 * 
	 * @return String
	 * 
	 * @see String
	 */
	public String getMass(){
		return "" + mass;
	}
	
	/**
	 * Method getMassValue.
	 * It returns the double value of the mass of the body.
	 * 
	 * @return double Value of the mass
	 */
	public double getMassValue(){
		return mass;
	}
	
	
	public int getIndex(){
		return index;
	}
	
	public String toString(){
        return "----\nBody\nposition("+p.x+","+p.y+")\nvelocity("+v.x+","+v.y+")\nmass " + mass+"\nindex " +index +"\n-----";
    }
}