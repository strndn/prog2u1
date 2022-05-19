import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class Node extends Circle {
    private final String name;
    private final double firstCoordinate;
    private final double secondCoordinate;
    private boolean selected;


    public Node(String name, double firstCoordinate, double secondCoordinate) {
        super(firstCoordinate, secondCoordinate, 13, Color.BLUE);
        this.name = name;
        this.firstCoordinate = firstCoordinate;
        this.secondCoordinate = secondCoordinate;

    }

    public double getFirstCoordinate() {
        return firstCoordinate;
    }

    public double getSecondCoordinate() {
        return secondCoordinate;
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

    public boolean isSelected() {
        return selected;
    }

    public void select(){
        selected =! selected;
    }

    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        return name;
    }
}

