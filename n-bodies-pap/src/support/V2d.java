package support;

/**
 *
 * 2-dimensional vector
 * objects are completely state-less
 *
 */
public class V2d implements java.io.Serializable {

    public double x,y;

    public V2d(double x,double y){
        this.x=x;
        this.y=y;
    }
    
    public double abs(){
        return (double)Math.sqrt(x*x+y*y);
    }
    
    public double dist(V2d v){
    	return Math.sqrt(( x - v.x )*( x - v.x )+( y - v.y )*( y - v.y ));
    	
    }
    
    public V2d getNormalized(){
        double module=(double)Math.sqrt(x*x+y*y);
        return new V2d(x/module,y/module);
    }
    
    public V2d mul(double fact){
        return new V2d(x*fact,y*fact);
    }

    public V2d sum(V2d v){
		return new V2d(x+v.x,y+v.y);
    }
    
    public V2d sub(V2d v){
        return new V2d(x-v.x,y-v.y);
    }

    public String toString(){
        return "V2d("+x+","+y+")";
    }
    
}
