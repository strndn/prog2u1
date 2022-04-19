import java.util.*;
import java.io.Serializable;

/**
 * Oriktad graf
 */
public class ListGraph<T> implements Graph{

    private final Map<Object, Set<Edge>> nodes = new HashMap<>();


    /*public void add(Object nod) {
        nodes.putIfAbsent(nod, new HashSet<>());
    }*/


    /*public void connect(Object a, Object b, String name, int weight) {
        add(a);
        add(b);

        Set<Edge> aEdges = nodes.get(a);
        Set<Edge> bEdges = nodes.get(b);

        aEdges.add(new Edge(b, name, weight));
        bEdges.add(new Edge(a, name, weight));

    }*/


    /*public boolean pathExists(Object a, Object b) {
        Set<Object> visited = new HashSet<>();
        depthFirstVisitAll(a, visited);
        return visited.contains(b);
    }*/

    /*public List<Edge> getAnyPath(Object from, Object to) {
        Map<Object, Object> connection = new HashMap<>();
        depthFirstConnection(from, null, connection);
        if (!connection.containsKey(to)) {
            return Collections.emptyList();
        }
        return gatherPath(from, to, connection);
    }*/

    /*public List<Edge> getShortestPath(Object from, Object to) {
        Map<Object, Object> connections = new HashMap<>();
        connections.put(from, null);

        LinkedList<Object> queue = new LinkedList<>();
        queue.add(from);
        while (!queue.isEmpty()) {
            Object nod = queue.pollFirst();
            for (Edge edge : nodes.get(nod)) {
                Object destination = edge.getDestination();
                if (!connections.containsKey(destination)) {
                    connections.put(destination, nod);
                    queue.add(destination);
                }
            }
        }

        if (!connections.containsKey(to)) {
            throw new IllegalStateException("no connection");
        }

        return gatherPath(from, to, connections);

    }*/

    /*private List<Edge> gatherPath(Object from, Object to, Map<Object, Object> connection) {
        LinkedList<Edge> path = new LinkedList<>();
        Object current = to;
        while (!current.equals(from)) {
            Object next = connection.get(current);
            Edge edge = getEdgeBetween(next, current);
            path.addFirst(edge);
            current = next;
        }
        return Collections.unmodifiableList(path);
    }*/


    /*public Edge getEdgeBetween(Object next, Object current) {
        for (Edge edge : nodes.get(next)) {
            if (edge.getDestination().equals(current)) {
                return edge;
            }
        }

        return null;
    }*/

    /*private void depthFirstConnection(Object to, Object from, Map<Object, Object> connection) {
        connection.put(to, from);
        for (Edge edge : nodes.get(to)) {
            if (!connection.containsKey(edge.getDestination())) {
                depthFirstConnection(edge.getDestination(), to, connection);
            }
        }

    }*/

    /*private void depthFirstVisitAll(Object current, Set<Object> visited) {
        visited.add(current);
        for (Edge edge : nodes.get(current)) {
            if (!visited.contains(edge.getDestination())) {
                depthFirstVisitAll(edge.getDestination(), visited);
            }
        }
    }*/


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
        Set<Edge> aEdges = nodes.get(a);
        Set<Edge> bEdges = nodes.get(b);

        aEdges.add(new Edge(b, name, weight));
        bEdges.add(new Edge(a, name, weight));
    }

    @Override
    public void setConnectionWeight(Object a, Object b, int weight) {
        for (Edge edge : nodes.get(a)) {
            if (edge.getDestination() == b) edge.setWeight(weight);
        }

        for (Edge edge : nodes.get(b)) {
            if (edge.getDestination() == a) edge.setWeight(weight);
        }
    }

    @Override
    public Set getNodes() {
        Set<Object> nodes = new HashSet<>();
        nodes.addAll(this.nodes.keySet());
        return nodes;
    }

    @Override
    public Collection<Edge> getEdgesFrom(Object node) {
        if (!nodes.containsKey(node)) throw new NoSuchElementException();

        Collection<Edge> edges = nodes.get(node);
        return edges;
    }

    @Override
    public Edge getEdgeBetween(java.lang.Object node1, java.lang.Object node2) {
        for (Edge edge : nodes.get(node1)) {
            if (edge.getDestination().equals(node2)) {
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
            nodes.get(edge.getDestination()).remove(edge);
            edgesToRemove.add(edge);
        }
        nodes.get(node).removeAll(edgesToRemove);

        nodes.remove(node);
    }

    @Override
    public boolean pathExists(Object from, Object to) {
        return getPath(from,to).equals(Collections.emptyList()) ? false : true;
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
            return Collections.emptyList();
        }
        return gatherPath(from, to, connection);
    }
}
