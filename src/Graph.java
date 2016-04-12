
import java.util.*;

public class Graph<T> implements GraphInterface<T> {
    private HashSet<T> vertices;
    private HashSet<Edge> edges;

    public Graph(){
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    public HashSet<T> getVertices(){
        return vertices;
    }

    public Edge<T>[] getEdges(){
        return (Edge<T>[])edges.toArray();
    }

    @Override
    public boolean addVertex(T vertexLabel) {
       return vertices.add(vertexLabel);
    }

    @Override
    public boolean addEdge(T begin, T end, double edgeWeight) {
        Edge e = new Edge(begin,end, edgeWeight);
        return edges.add(e);
    }

    @Override
    public boolean addEdge(T begin, T end) {
        Edge e = new Edge(begin,end);
        return edges.add(e);
    }

    @Override
    public boolean hasEdge(T begin, T end) {
        Edge e = new Edge(begin,end);
        return edges.contains(e);
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public int getNumberOfVertices() {
        return vertices.size();
    }

    @Override
    public int getNumberOfEdges() {
        return edges.size();
    }

    @Override
    public void clear() {
        vertices.clear();
        edges.clear();
    }

    @Override
    public Queue<T> getBreadthFirstTraversal(T origin) {
        return null;
    }

    @Override
    public Stack<T> getDepthFirstTraversal(T origin) {
        HashSet<T> visited = new HashSet<>();
        visited.add(origin);
        Stack<T> stack = new Stack<>();
        Stack<T> results = new Stack<>();
        System.out.print(origin);
        T unvisted = getUnvisited(origin, visited);
        stack.add(origin);
        results.add(origin);
        while (true){
            System.out.print(unvisted);
            stack.add(unvisted);
            results.add(unvisted);
            unvisted = getUnvisited(stack.peek(), visited);
            while (unvisted == null && !stack.isEmpty()){
                    T remove = stack.pop();
                   System.out.print(remove);
                results.add(remove);
                if (!stack.isEmpty()){
                    unvisted = getUnvisited(stack.peek(), visited);
                }
            }
            if (stack.isEmpty()){
                System.out.println("Done!");
                break;
            }
        }
        return results;
    }


    private T getUnvisited(T node, HashSet<T> visited){
        for (Edge edge: edges){
            if (edge.getStartPoint().equals(node)){
               T endP = (T)edge.getEndPoint();
                if (!visited.contains(endP)){
                    visited.add(endP);
                    return endP;
                }
            }
        }
        return  null;
    }


    @Override
    public Stack<T> getTopologicalOrder() {
        return null;
    }

    //path that has the fewest edges
    @Override
    public int getShortestPath(T begin, T end, Stack<T> path) {
        return 0;
    }

   // the shortest path between two given vertices has the smallest edge-weight sum
    @Override
    public double getCheapestPath(T begin, T end, Stack<T> path) {
        return 0;
    }


    @Override
    public String toString(){
        String str = "Vertices: ";
       for (T obj : vertices){
           str += (obj + " ");
       }
        return str;
    }


}



