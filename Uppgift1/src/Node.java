import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Node extends Canvas {
    private final String name;
    private final double firstCoordinate;
    private final double secondCoordinate;
    private boolean selected;


    public Node(String name, double firstCoordinate, double secondCoordinate) {
        super(25, 25);
        this.name = name;
        this.firstCoordinate = firstCoordinate;
        this.secondCoordinate = secondCoordinate;

        relocate(firstCoordinate - (getWidth()/2), secondCoordinate - (getHeight()/2));
        paintUnSelected();
    }

    public double getFirstCoordinate() {
        return firstCoordinate;
    }

    public double getSecondCoordinate() {
        return secondCoordinate;
    }

    public void paintUnSelected(){
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillOval(0, 0, getWidth()-1, getHeight()-1);
        selected = false;
    }

    public void paintSelected(){
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillOval(0, 0, getWidth()-1, getHeight()-1);
        selected = true;
    }

    public boolean isSelected(){
        return selected;
    }

    public String getName() {
        return name;
    }

    public boolean equals(java.lang.Object other) {
        if (other instanceof Node nod) {
            return name.equals(nod.name);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        return name;
    }
}

