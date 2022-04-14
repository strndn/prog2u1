import java.util.*;

/**
 * Oriktad graf
 */
public class ListGraph implements Graph{

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

    }

    @Override
    public void connect(Object node1, Object node2, String name, int weight) {

        Set<Edge> aEdges = nodes.get(a);
        Set<Edge> bEdges = nodes.get(b);

        aEdges.add(new Edge(b, name, weight));
        bEdges.add(new Edge(a, name, weight));
    }

    @Override
    public void setConnectionWeight(java.lang.Object node1, java.lang.Object node2, int weight) {

    }

    @Override
    public Set getNodes() {
        return null;
    }

    @Override
    public Collection<Edge> getEdgesFrom(java.lang.Object node) {
        return null;
    }

    @Override
    public Edge getEdgeBetween(java.lang.Object node1, java.lang.Object node2) {
        return null;
    }

    @Override
    public void disconnect(java.lang.Object node1, java.lang.Object node2) {

    }

    @Override
    public void remove(java.lang.Object node) {

    }

    @Override
    public boolean pathExists(java.lang.Object from, java.lang.Object to) {
        return false;
    }

    @Override
    public List<Edge> getPath(java.lang.Object from, java.lang.Object to) {
        return null;
    }
}
