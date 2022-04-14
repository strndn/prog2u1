import java.util.Objects;

public class Node {
    private final String name;

    public Node(String name) {
        this.name = name;
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

