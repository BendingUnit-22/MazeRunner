import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Supplier;


public class MazeRunner extends Application{
    // 14 x 11
   private int[][] points =
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


    Stage window;
    Graph graph;
    private final double WIDTH = 40.0;
    ObservableList<StackPane> stackList =  FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;


        VBox layout = new VBox(10);
        GridPane grid = new GridPane();
        for (int row = 0; row < points.length; row++) {
            for (int col = 0; col < points[0].length; col++) {
                int val = points[row][col];
                String url = (val == 0) ? "tree.png" : "stone.jpg";
                ImageView imgV = new ImageView(url);
                StackPane stackPane = new StackPane();
                imgV.setFitHeight(WIDTH);
                imgV.setFitWidth(WIDTH);
                stackPane.getChildren().add(imgV);
                GridPane.setConstraints(stackPane, col, row);
                stackPane.setId(Integer.toString(row * row) + Integer.toString(col));
                stackList.add(stackPane);
            }
        }

        grid.getChildren().addAll(stackList);

        HBox buttonLayout = new HBox(20);
        Button vertices_show = new Button("Show Vertices");
        Button breath_first = new Button("Breadth First Traversal");
        Button depth_first = new Button("Depth First Traversal");
        buttonLayout.setAlignment(Pos.BASELINE_CENTER);
        buttonLayout.getChildren().addAll(vertices_show, breath_first, depth_first);


        HBox horizonal_span = new HBox(20);
        horizonal_span.getChildren().add(grid);

        VBox vertical_span = new VBox(10);
        Label ver_label = new Label("Vertex");
        ImageView ver_image = new ImageView("pin.png");
        ver_image.setFitHeight(20);
        ver_image.setPreserveRatio(true);
        vertical_span.setAlignment(Pos.CENTER);
        HBox ver_layout = new HBox(10);
        ver_layout.getChildren().addAll(ver_label, ver_image);
        vertical_span.getChildren().add(ver_layout);
        horizonal_span.getChildren().add(vertical_span);

        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(horizonal_span, buttonLayout);


        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        Maze maze = new Maze(points);
        graph = maze.graphFromMaze();


        vertices_show.setOnAction(e -> {
            HashSet<Point> vertices = graph.getVertices();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    for (Point p : vertices) {
                        ImageView pin = new ImageView("pin.png");
                        pin.setFitHeight(WIDTH - 10);
                        pin.setFitWidth(WIDTH - 10);
                        StackPane stackPane = stackPaneAtPosition(p.getX(), p.getY());
                        stackPane.getChildren().add(pin);
                    }
                }
            });
        });

        breath_first.setOnAction(e -> {
            hideVertices();
         //   Stack<Point> depthFirstQ = graph.getDepthFirstTraversal(new Point(0, 12, 2));
//            Point p1 = depthFirstQ.pop();
//            while (!depthFirstQ.isEmpty()){
//                Point p2 = depthFirstQ.pop();
//                paint(p1,p2);
//                p1 = p2;
//           }


            Point p3 = new Point(12,0);
            Point p4 = new Point(12,4);

            paint(p3,p4);

        });


        depth_first.setOnAction(e -> {

        });
    }


    private void paint(Point p1, Point p2){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Point p : pointsBetween(p1,p2)){
                    StackPane sp = stackPaneAtPosition(p.getX(),p.getY());
                    sp.getChildren().get(0).setBlendMode(BlendMode.GREEN);
                    System.out.print(p);
                }
                System.out.println();
            }
        });
    }


    private void hideVertices(){
          Platform.runLater(new Runnable(){
              @Override
              public void run() {
                  for (StackPane sp: stackList){
                     if(sp.getChildren().size() > 1){
                         sp.getChildren().remove(1);
                     }
                  }
              }
          });
    }


    private ArrayList<Point> pointsBetween(Point p1, Point p2){
        ArrayList<Point> pointsToDraw = new ArrayList<>();
        int dx = p2.getX() - p1.getX();
        int dy = p2.getY() - p1.getY();
        boolean horiz = (dx == 0);
        int moves = horiz ? dy : dx;
     //   System.out.println("Moves:" + moves);
    //    System.out.println("Horiz:" + horiz);
        int uX = (moves < 0) ? -1 : 1;
        for (int i = 0; i <= Math.abs(moves); i++){
            int x = horiz ?  0 : i * uX;
            int y = horiz ? i * uX :  0;
            Point pt = new Point(p1.getX() + x, p1.getY() + y);
            pointsToDraw.add(pt);
        }
        return pointsToDraw;
    }

    private StackPane stackPaneAtPosition(int x, int y){
        for (StackPane sp: stackList){
//            if (sp.getId().equals(Integer.toString(y**y) + Integer.toString())){
//               return sp;
//         }
        }
        return null;
    }

}
