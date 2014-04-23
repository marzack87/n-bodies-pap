package support;

/**
 *
 * 2-dimensional point
 * objects are completely state-less
 *
 */
public class P2d implements java.io.Serializable {

    public double x,y;

    public P2d(double x,double y){
        this.x=x;
        this.y=y;
    }

    public P2d sum(V2d v){
    	double x1 = x+v.x;
    	/*
    	if(x+v.x > 800){
    		x1 = x+v.x-800;
    	}
    	*/
    	double y1 = y+v.y;
    	/*
    	if(y+v.y > 600){
    		y1 = y+v.y-600; 
    	}
    	*/
        return new P2d(x1,y1);
    }

    public V2d sub(P2d p){
        return new V2d(x-p.x,y-p.y);
    }
    
    public double dist(P2d p){
    	return Math.sqrt(( x - p.x )*( x - p.x )+( y - p.y )*( y - p.y ));
    	
    }

    public String toString(){
        return "P2d("+x+","+y+")";
    }

}
