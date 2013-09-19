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
    // create the zero vector of length N
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
    // create a vector from an array
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
    // return the length of the vector
    public int length() {
        return N;
    }

    /**
     * Method dot.
     * It calculates the inner product between my vector and the vector passed as param 
     * 
     * @param that - Second vector
     * @return sum - Inner product
     */
    // return the inner product of this Vector a and b
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
    // return the Euclidean norm of this Vector
    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }
    
    /**
     * Method magnitude.
     * It returns the Euclidean distance between two vectors.
     * 
     * @return dist - The distance
     */
    // return the Euclidean distance between this and that
    public double distanceTo(Vector that) {
        if (this.N != that.N) throw new RuntimeException("Dimensions don't agree");
        return this.minus(that).magnitude();
    }

    // return this + that
    public Vector plus(Vector that) {
        if (this.N != that.N) throw new RuntimeException("Dimensions don't agree");
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] + that.data[i];
        return c;
    }

    // return this - that
    public Vector minus(Vector that) {
        if (this.N != that.N) throw new RuntimeException("Dimensions don't agree");
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] - that.data[i];
        return c;
    }

    // return the corresponding coordinate
    public double cartesian(int i) {
        return data[i];
    }

    // create and return a new object whose value is (this * factor)
    public Vector times(double factor) {
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = factor * data[i];
        return c;
    }


    // return the corresponding unit vector
    public Vector direction() {
        if (this.magnitude() == 0.0) throw new RuntimeException("Zero-vector has no direction");
        return this.times(1.0 / this.magnitude());
    }

    // return a string representation of the vector
    public String toString() {
        String s = "(";
        for (int i = 0; i < N; i++) {
            s += data[i];
            if (i < N-1) s+= ", "; 
        }
        return s + ")";
    }

}
