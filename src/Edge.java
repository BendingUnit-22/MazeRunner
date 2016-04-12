




public class Edge<T> {
    public final T v1;
    public final T v2;
    public final double weight;

    public Edge(T v1, T v2, double w) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = w;
    }

    public T getEndPoint(){
        return v2;
    }

    public T getStartPoint(){
        return v1;
    }



    public Edge(T v1, T v2) {
        this(v1,v2, 1.0);
    }

    @Override
    public boolean equals(Object other){
        Edge a = (Edge)other;
        return v1.equals(a.v1) && v2.equals(a.v2);
    }


    @Override
    public String toString(){
        String str = "Edge(" + v1 + "," + v2 + ") w = " + weight;
        return str;
    }

}