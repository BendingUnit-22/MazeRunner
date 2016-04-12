
/**
 * Created by wu on 4/10/16.
 */

public class Point{
    private int x;
    private int y;
    private int value;

    Point(int x, int y, int data){
        this.x = x;
        this.y = y;
        this.value = data;
    }

    Point(int x, int y){
        this.x = x;
        this.y = y;
        value = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int v){
        this.value = v;
    }
    @Override
    public String toString(){
        return "("+  x + "," + y + ")";
    }

    @Override
    public boolean equals(Object other){
        Point p = (Point)other;
        return x == p.getX() && y == p.getY();
    }
}



