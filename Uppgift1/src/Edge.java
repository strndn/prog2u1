import java.util.Objects;
import java.io.Serializable;

public class Edge<T> implements Serializable{

    private final Object destination;
    private final String name;
    private int weight;

    public Edge(Object destination, String name, int weight) {
        this.destination = Objects.requireNonNull(destination);
        this.name = Objects.requireNonNull(name);

        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    public String getDestination() {
        return destination.toString();
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int a){
        if (a < 0) {
            throw new IllegalArgumentException();
        } else {
            this.weight = a;
        }
    }

    @Override
    public String toString() {
        return String.format("till %s med %s tar %d", destination, name, weight);
    }
}

