import java.util.Objects;

public class Object {
    private final String name;

    public Object(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean equals(java.lang.Object other) {
        if (other instanceof Object nod) {
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

