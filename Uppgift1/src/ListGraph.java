// PROG2 VT2022, Inlämningsuppgift, del 1
// Grupp 088
// Lukas Strand lust9442
// Morgan Sohl moso0848

import java.util.*;
import java.io.Serializable;


public class ListGraph<T> implements Graph, Serializable {

    private final Map<Object, Set<Edge>> nodes = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object nod : nodes.keySet()) {
            sb.append(nod).append(": ").append(nodes.get(nod)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void add(java.lang.Object node) {
        nodes.putIfAbsent(node, new HashSet<>());
    }

    @Override
    public void connect(Object a, Object b, String name, int weight) {
        if (!nodes.containsKey(a) || !nodes.containsKey(b)) throw new NoSuchElementException();

        if (weight < 0) throw new IllegalArgumentException();

        if (getEdgeBetween(a, b) != null) throw new IllegalStateException();

        Set<Edge> firstEdges = nodes.get(a);
        Set<Edge> secondEdges = nodes.get(b);

        firstEdges.add(new Edge(b, name, weight));
        secondEdges.add(new Edge(a, name, weight));
    }

    @Override
    public void setConnectionWeight(Object a, Object b, int weight) {
        if (!nodes.containsKey(a) || !nodes.containsKey(b)) throw new NoSuchElementException();
        if (getEdgeBetween(a, b) == null) throw new NoSuchElementException();
        for (Edge edge : nodes.get(a)) {
            if (edge.getDestinationString().equals(b.toString())) edge.setWeight(weight);
        }

        for (Edge edge : nodes.get(b)) {
            if (edge.getDestinationString().equals(a.toString())) edge.setWeight(weight);
        }
    }

    @Override
    public Set getNodes() {
        Set<Object> nodes = new HashSet<>(this.nodes.keySet());
        return nodes;
    }

    @Override
    public Collection<Edge> getEdgesFrom(Object node) {
        if (!nodes.containsKey(node)) throw new NoSuchElementException();

        Collection<Edge> edges = nodes.get(node).stream().toList();
        return edges;
    }

    @Override
    public Edge getEdgeBetween(Object node1, Object node2) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) throw new NoSuchElementException();

        for (Edge edge : nodes.get(node1)) {
            if (edge.getDestinationString().equals(node2.toString())) {
                return edge;
            }
        }

        return null;
    }

    @Override
    public void disconnect(Object a, Object b) {
        if (!nodes.containsKey(a) || !nodes.containsKey(b)) throw new NoSuchElementException();

        if (getEdgeBetween(a, b) == null) throw new IllegalStateException();

        nodes.get(a).remove(getEdgeBetween(a, b));
        nodes.get(b).remove(getEdgeBetween(b, a));
    }

    @Override
    public void remove(Object node) {
        if (!nodes.containsKey(node)) throw new NoSuchElementException();

        Set<Edge> edgesToRemove = new HashSet<>();

        for (Edge edge : nodes.get(node)) {
            nodes.get(edge.getDestinationString()).removeIf(edge1 -> Objects.equals(edge1.getDestinationString(), node.toString()));
            edgesToRemove.add(edge);
        }
        nodes.get(node).removeAll(edgesToRemove);

        nodes.remove(node);
    }


    @Override
    public boolean pathExists(Object from, Object to) {
        if (!nodes.containsKey(from) || !nodes.containsKey(to) || getPath(from, to) == null) return false;
        return true;
    }

    private List<Edge> gatherPath(Object from, Object to, Map<Object, Object> connection) {
        LinkedList<Edge> path = new LinkedList<>();
        Object current = to;
        while (!current.equals(from)) {
            Object next = connection.get(current);
            Edge edge = getEdgeBetween(next, current);
            path.addFirst(edge);
            current = next;
        }
        return Collections.unmodifiableList(path);
    }
    private void depthFirstConnection(Object to, Object from, Map<Object, Object> connection) {
        connection.put(to, from);
        for (Edge edge : nodes.get(to)) {
            if (!connection.containsKey(edge.getDestination())) {
                depthFirstConnection(edge.getDestination(), to, connection);
            }
        }

    }
    @Override
    public List<Edge> getPath(Object from, Object to) {
        Map<Object, Object> connection = new HashMap<>();
        depthFirstConnection(from, null, connection);
        if (!connection.containsKey(to)) {
            return null;
        }
        return gatherPath(from, to, connection);
    }
}
