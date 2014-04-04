package support;

/**
 * Class Vector. 
 * Represent the vector carrier as the geometrical element.
 * 
 * @author Richiard Casadei, Marco Zaccheroni
 */
public class Vector { 

    private final int N;         // length of the vector
    private double[] data;       // array of vector's components

    /**
     * Class Vector constructor.
     * 
     * @param N - Length of the vector 
     */
    public Vector(int N) {
        this.N = N;
        this.data = new double[N];
    }
    
    /**
     * Class Vector constructor.
     * It creates a vector from an array of data and it also create a copy of data[]
     * 
     * @param data -  Array of data 
     */
    public Vector(double[] data) {
        N = data.length;

        // defensive copy so that client can't alter our copy of data[]
        this.data = new double[N];
        for (int i = 0; i < N; i++)
            this.data[i] = data[i];
    }

    /**
     * Method lenght.
     * It returns the length of a vector.
     * 
     * @return N - length of the vector
     */
    public int length() {
        return N;
    }

    /**
     * Method dot.
     * It calculates the inner product between my vector and the vector passed as parameter. 
     * 
     * @param that - Second vector
     * @return sum - Inner product
     */
    public double dot(Vector that) {
        if (this.N != that.N) throw new RuntimeException("Dimensions don't agree");
        double sum = 0.0;
        for (int i = 0; i < N; i++)
            sum = sum + (this.data[i] * that.data[i]);
        return sum;
    }

    /**
     * Method magnitude.
     * It returns the Euclidean norm of a vector.
     * So it assigns to a vector of a vectorial space, except the zero, a positive length.
     * 
     * @return norm - The vector length
     */
    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }
    
    /**
     * Method magnitude.
     * It returns the Euclidean distance between two vectors.
     * 
     * @return dist - The distance
     */
    public double distanceTo(Vector that) {
        if (this.N != that.N) throw new RuntimeException("Dimensions don't agree");
        return this.minus(that).magnitude();
    }

    /**
     * Method plus.
     * It calculate and returns an object Vector who represent the sum of the vector and the vector passed as parameter.
     * 
     * @return c - The Vector sum
     */
    public Vector plus(Vector that) {
        if (this.N != that.N) throw new RuntimeException("Dimensions don't agree");
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] + that.data[i];
        return c;
    }
    
    /**
     * Method minus.
     * It calculate and returns an object Vector who represent the removal of the vector and the vector passed as parameter.
     * 
     * @return c - The Vector difference
     */
    public Vector minus(Vector that) {
        if (this.N != that.N) throw new RuntimeException("Dimensions don't agree");
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] - that.data[i];
        return c;
    }
    
    /**
     * Method cartesian.
     * Passing an integer as parameter it returns the corresponding coordinate of a vector.
     * 
     * @param i - Integer value corresponding the index of the vector in the array.
     * @return data[i] - The Vector difference
     */
    public double cartesian(int i) {
        return data[i];
    }
    
    /**
     * Method times.
     * It returns a new Vector object whose value is (vector * factor).
     * 
     * @param factor - Double number 
     * @return data[i] - The Vector difference
     */
    public Vector times(double factor) {
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = factor * data[i];
        return c;
    }

    /**
     * Method direction.
     * It returns the direction of a vector corrisponding to the unit vector.
     * 
     * @return dir - The direction
     */
    public Vector direction() {
        if (this.magnitude() == 0.0) throw new RuntimeException("Zero-vector has no direction");
        return this.times(1.0 / this.magnitude());
    }
    
    /**
     * Method toString.
     * It returns the direction of a vector corrisponding to the unit vector.
     * 
     * @return s - The string represantation of the vector
     */
    public String toString() {
        String s = "(";
        for (int i = 0; i < N; i++) {
            s += data[i];
            if (i < N-1) s+= ", "; 
        }
        return s + ")";
    }

}
