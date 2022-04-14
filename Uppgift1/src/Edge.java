import java.util.Objects;

public class Edge {

    private final Nod destination;
    private final String name;
    private int weight;

    public Edge(Nod destination, String name, int weight) {
        this.destination = Objects.requireNonNull(destination);
        this.name = Objects.requireNonNull(name);

        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    public Nod getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int a){
        if (a < 0) {
            throw new IllegalArgumentException();
        } else {
            this.weight = a;
        }
    }

    /*public boolean equals(Object other) {
        if (other instanceof Edge edge) {
            return Objects.equals(name, edge.name) &&
                    Objects.equals(destination, edge.destination);
        }
        return false;
    }*/

    /*public int hashCode() { hej
        return Objects.hash(name, destination);
    }*/

    @Override
    public String toString() {
        return "Edge{" +
                "destination=" + destination +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }
}

