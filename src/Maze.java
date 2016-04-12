
import java.util.*;

public class Maze {
    private final int numCol;
    private final int numRow;
    private final Point[] matrix;

    public Maze(int[][] m){
        numCol = m.length;
        numRow = (m[0] == null) ? 0 : m[0].length;
        matrix = new Point[numCol * numRow];
        for (int i = 0; i < numCol; i++){
            for (int j = 0; j < numRow; j++){
                int value = m[i][j];
                matrix[indexOf(i,j)] = new Point(i,j, value);
            }
        }
    }


    private int indexOf(int x, int y){
        return x * numRow + y;
    }


    private ArrayList<Point> getVertices(){
        ArrayList<Point> vertices = new ArrayList<>();
        for (Point p : matrix){
            if (p.getValue() == 1){
                if (isVertex(p)){
                    vertices.add(p);
                }
            }
        }
        return vertices;
    }


    public Graph graphFromMaze(){
        Graph<Point> graph = new Graph();
        ArrayList<Point> vertices = getVertices();
        for (Point p: vertices){
            graph.addVertex(p);
        }
        for (Point pt : vertices){
            Point hNext = pointInAligment(pt, vertices, false);
            if (hNext != null){
                int conn = numberOfConnection(pt, hNext, false);
               if (conn != 0){
                  // System.out.println(pt + " -> " + hNext + " Connection: " + conn);
                   //System.out.println();
                   graph.addEdge(pt,hNext, (double)conn);
                   graph.addEdge(hNext,pt, (double)conn);
               }
            }

            Point vNext = pointInAligment(pt, vertices, true);
            if ( vNext != null){
                int conn = numberOfConnection(pt, vNext, true);
                if (conn != 0){
                  //  System.out.println(pt + " -> " + vNext + " Connection: " + conn);
                   // System.out.println();
                    graph.addEdge(pt,vNext, (double)conn);
                   graph.addEdge(vNext,pt, (double)conn);
                }
            }

        }
        return graph;
    }

    private Point pointInAligment(Point p, ArrayList<Point> points, boolean vertically){
        ArrayList<Point> array = new ArrayList<>();
        int z = vertically ? p.getX() : p.getY();
        int zp =  !vertically ? p.getX() : p.getY();
        for (Point pt : points){
            int g =  vertically ? pt.getX() : pt.getY();
            int gp =  !vertically ? pt.getX() : pt.getY();
            if (g > z && zp == gp){
                return pt;
            }
        }
        return null;
    }


    private int numberOfConnection(Point p1, Point p2, boolean vertically){
        int connections = 1;
        int  dx = vertically ? 1 : 0;
        int dy =  vertically ? 0 : 1;
        Point curr = matrix[indexOf(p1.getX()+dx, p1.getY()+dy)];
        while (!curr.equals(p2)){
            if (curr.getValue() == 0){
                connections = 0; break;
            }
            connections++;
            curr = matrix[indexOf(curr.getX() + dx, curr.getY() + dy)];
        }
        return connections;
    }



    private boolean isVertex(Point p){
        if (atri(p)) { return true; }
        return !isConnector(p);
    }


    private boolean isConnector(Point pt){
        Point[] p = getNeighbors(pt);
        Point top = p[0];
        Point bottom = p[1];
        Point left = p[2];
        Point right  = p[3];
        if (top.getValue() == bottom.getValue() && top.getValue() == 0){
            return true;
        }else if (left.getValue() == right.getValue() && left.getValue() == 0){
            return true;
        }else{
            return false;
        }
    }

    // Order of top, bottom, left, right
    private Point[] getNeighbors(Point p){
        Point[] nb = new Point[4];
        nb[0] = matrix[indexOf(p.getX(), p.getY()+1)];
        nb[1] = matrix[indexOf(p.getX(), p.getY()-1)];
        nb[2] = matrix[indexOf(p.getX()+1, p.getY())];
        nb[3] = matrix[indexOf(p.getX()-1, p.getY())];
        return nb;
    }



    private boolean atri(Point p){
        int x = p.getX(),y = p.getY();
        if (x == numCol-1 || y == numRow-1 || x == 0 || y == 0) {
            return true;
        }else{
           Point[] nb = getNeighbors(p);
            int count = 0;
            for (Point pt: nb){
                if (pt.getValue() == 0){
                    count++;
                }
            }
            return count == 3;
        }
    }



    private ArrayList<Point> pointsBetween(Point p1, Point p2){
        ArrayList<Point> pointsToDraw = new ArrayList<>();
        int dx = p2.getX() - p1.getX();
        int dy = p2.getY() - p1.getY();
        boolean horiz = (dx == 0);
        int moves = horiz ? dy : dx;
        int uX = (moves < 0) ? -1 : 1;
        for (int i = 0; i <= Math.abs(moves); i++){
            int x = horiz ?  0 : i * uX;
            int y = horiz ? i * uX :  0;
            Point pt = new Point(p1.getX() + x, p1.getY() + y);
            pointsToDraw.add(pt);
        }
        return pointsToDraw;
    }


    public void display(){
        for (int i = 0; i < numCol; i++){
            for (int j = 0; j < numRow; j++){
                Point p = matrix[indexOf(i,j)];
                int val = p.getValue();
                if (val == 0){
                    System.out.print("# ");
                }else if (val == 1){
                    System.out.print("  ");
                }else if (val == 2){
                    System.out.print("V ");
                }else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    private static int[][] points =
            {
                    {0,0,0,0,0,0,0,0,0,0,0,0,1,0},
                    {0,1,1,1,1,1,1,1,1,1,1,0,1,0},
                    {0,1,0,0,0,0,0,0,0,0,0,0,1,0},
                    {0,1,0,1,1,1,1,1,1,1,1,1,1,0},
                    {0,1,0,1,0,0,0,0,0,0,0,0,1,0},
                    {0,1,1,1,1,1,1,1,1,1,1,0,0,0},
                    {0,1,0,0,0,0,0,0,0,0,1,0,1,0},
                    {0,1,0,1,1,1,1,1,1,0,1,1,1,0},
                    {0,0,0,1,0,0,1,0,0,0,1,0,1,0},
                    {0,1,1,1,1,1,1,1,1,1,1,0,1,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,1,0}
            };


    private void mapPointsToMatrix(Collection<Point> pts, int val){
        for (Point p : pts){
            matrix[indexOf(p.getX(),p.getY())].setValue(val);
        }
    }


    public static void main(String[] args){
        Maze maze = new Maze(points);
        maze.display();
        Graph graph = maze.graphFromMaze();
        HashSet<Point> vertices = graph.getVertices();

        System.out.println("\n \tAdded vetices");
        maze.mapPointsToMatrix(vertices,2);
        maze.display();

        System.out.println("\n \tAdded Edges");

           Stack<Point> depthFirstQ = graph.getDepthFirstTraversal(new Point(0, 12, 2));
            Point p1 = depthFirstQ.pop();
            while (!depthFirstQ.isEmpty())
            {
                Point p2 = depthFirstQ.pop();
                ArrayList<Point> paints = maze.pointsBetween(p1,p2);
                maze.mapPointsToMatrix(paints, 3);
                p1 = p2;
           }
            maze.display();

    }

}


