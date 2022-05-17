// PROG2 VT2022, Inlämningsuppgift, del 1
// Grupp 088
// Lukas Strand lust9442
// Morgan Sohl moso0848

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

    public String getDestinationString() {
        return destination.toString();
    }

    public Object getDestination()  {
        return destination;
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
        return String.format("to %s by %s takes %d", destination, name, weight);
    }
}

