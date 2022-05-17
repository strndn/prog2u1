import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Node extends Canvas {
    private final String name;
    private final double xPos, yPos;
    private boolean selected;


    public Node(String name, double xPos, double yPos) {
        super(25, 25);
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;

        relocate(xPos - (getWidth()/2), yPos - (getHeight()/2));
        paintUnSelected();
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
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

