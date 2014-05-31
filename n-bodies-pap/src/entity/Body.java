package entity;

import support.*;

/**
 * Class Body. 
 *<p>
 * Represent the body single entity. Each body has three private fields that stand for his properties:
 * </p>
 * <ul>
 * 	<li>V2d p, position vector given by the coordination x,y</li>
 * 	<li>V2d v, velocity vector</li>
 * 	<li>double mass, the value of the mass of the body(planet)</li> 
 * 	<li>int index, the body index in the allbodies array</li>
 * 	<li>boolean collision</li>
 * 	<li>V2d vel_after_collision, velocity after a collision</li> 
 * </ul>
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */

public class Body { 
	public V2d p; //position
	public V2d v; //velocity
	public double mass; //mass
	public int index;
	
	public boolean collision;
	public V2d vel_after_collision;
	
	
	/**
	 * Class Body default constructor.
	 * <p>
	 * Initialize the properties of the body with the object passed as parameter.
	 * 
	 * @param p Position vector
	 * @param v Velocity vector
	 * @param mass Value of the mass of the body
	 */
	public Body(V2d p, V2d v, double mass, int index) {
		this.p = p;
		this.v = v;
		this.mass = mass;
		this.index = index;
		
		collision = false;
		vel_after_collision = new V2d(0,0);
	}
	
	/**
	 * Method copy.
	 * <p>
	 * It returns a copy of the body.
	 * 
	 * @return Body A new Body object
	 */
	public Body copy(){
		return new Body(this.p, this.v, this.mass, this.index);
		
	}
	
	/**
	 * Method forceFrom.
	 * <p>
	 * It calculates the force between the body and an another body passed as param 
	 * 
	 * @param that Body passed
	 * @return Vector object The force vector requested
	 */
	public V2d forceFrom(Body that) {
		
		double G = 6.67*Math.pow(10,-11);
		V2d p_this = this.p; 
		V2d p_that = that.p; 
		V2d delta = p_that.sub(p_this);
		double dist = that.p.dist(this.p);
		double F;
		
		if (Util.soft_param_mode){
			F = (G * (this.mass) * (that.mass)) / ((dist * dist) + Util.EPS);
		}else{
			if (that.getMassValue() == Util.SUN_MASS){
				if (Util.star_wars_theme){
					if (dist*Util.scaleFact <= (Util.DEATHSTAR_RADIUS + Util.BODY_RADIUS)) collision(that);
				} else {
					if (dist*Util.scaleFact <= (Util.SUN_RADIUS + Util.BODY_RADIUS)) collision(that);
				}
			} else {
				if (dist*Util.scaleFact <= (Util.BODY_RADIUS * 2)) collision(that);
			}
			F = (G * (this.mass) * (that.mass)) / (dist * dist);
		}
			
		V2d delta_normalized = delta.getNormalized();
		V2d Force = delta_normalized.mul(F);
		
		//System.out.println(Force);
		return Force;
	}
	
	/**
	 * Method getIndex.
	 * <p>
	 * It returns the index of the body.
	 * 
	 * @return index Index of the body
	 */
	public int getIndex(){
		return index;
	}
	
	/**
	 * Method getMass.
	 * <p>
	 * It returns a String representation of the mass of the body.
	 * 
	 * @return String
	 */
	public String getMass(){
		return "" + mass;
	}
	
	/**
	 * Method getMassValue.
	 * <p>
	 * It returns the double value of the mass of the body.
	 * 
	 * @return double Value of the mass
	 */
	public double getMassValue(){
		return mass;
	}
	
	/**
	 * Method getPosition.
	 * <p>
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
	 * <p>
	 * It returns a double with the value of the coordinate X of the Position of the Body.
	 * 
	 * @return double Value of the position coordinate X
	 */
	public double getPosition_X(){
		return p.x;
	}
	
	/**
	 * Method getPosition_Y.
	 * <p>
	 * It returns a double with the value of the coordinate Y of the Position of the Body.
	 * 
	 * @return double Value of the position coordinate Y
	 */
	public double getPosition_Y(){
		return p.y;
	}
	
	/**
	 * Method getVelocity.
	 * <p>
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
	 * <p>
	 * It returns a double with the value of the coordinate X of the Velocity of the Body.
	 * 
	 * @return double Value of the velocity coordinate X
	 */
	public double getVelocity_X(){
		return v.x;
	}
	
	/**
	 * Method getVelocity_Y.
	 * <p>
	 * It returns a double with the value of the coordinate Y of the Velocity of the Body.
	 * 
	 * @return double Value of the velocity coordinate Y
	 */
	public double getVelocity_Y(){
		return v.y;
	}
	
	/**
	 * Method move.
	 * <p>
	 * Permit to update the position of the body based on the force applied to the body by the others
	 * and the discrete time.
	 * 
	 * @param f Force vector
	 * @param dt Time
	 */
	public synchronized void move(V2d f, double dt) { 
		//System.out.println("Body " + index);
		if (collision){
			this.v = vel_after_collision;
			V2d dp = this.v.mul(dt);
			p = p.sum(dp);
		} else {
			V2d a = f.mul(1/mass);
			V2d dv = a.mul(dt);
			V2d dp = (v.sum(dv.mul(1/2))).mul(dt);
			p = p.sum(dp);
			v = v.sum(dv);
		}
		
		collision = false;
		vel_after_collision = new V2d(0,0);
	}
	
	 
	/**
	 * Private method collision.
	 * <p>
	 * Represents the management of collisions.<br>
	 * It uses the formula of impact elastic in a one-dimensional collision event:
	 * </p>
	 * <p>
	 * v1f = ((m1-m2)*v1i + 2*m2*v2i)/(m1+m2)
	 *  
	 *  
	 * @param that
	 */
	private void collision(Body that){
		collision = true;
		
		V2d final_v = this.v.mul(this.mass - that.mass).sum(that.v.mul(2 * that.mass)).mul(1 / (this.mass + that.mass));
		vel_after_collision = vel_after_collision.sum(final_v);
		
		//System.out.println("COLLISION! FINAL VELOCITY: " + final_v + " - " + vel_after_collision);
	}
	
	/**
	 * Method reset.
	 * <p>
	 * It resets the body data as the info of the body taken as param
	 * 
	 * @param b  
	 */
	public void reset(Body b){
		this.p = b.p;
		this.v = b.v;
		this.index = b.index;
		this.mass = b.mass;
	}
	
	/**
	 * Method toString.
	 * <p>
	 * It returns a String representation of the body.
	 * 
	 * @return String
	 */
	public String toString(){
        return "----\nBody\nposition("+p.x+","+p.y+")\nvelocity("+v.x+","+v.y+")\nmass " + mass+"\nindex " +index +"\n-----";
    }

}
