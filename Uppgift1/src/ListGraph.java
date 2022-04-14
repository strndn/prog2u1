import java.util.*;

/**
 * Oriktad graf
 */
public class ListGraph {

    private final Map<Nod, Set<Edge>> nodes = new HashMap<>();

    public void add(Nod nod) {
        nodes.putIfAbsent(nod, new HashSet<>());
    }

    public void connect(Nod a, Nod b, String name, double weight) {
        add(a);
        add(b);

        Set<Edge> aEdges = nodes.get(a);
        Set<Edge> bEdges = nodes.get(b);

        aEdges.add(new Edge(b, name, weight));
        bEdges.add(new Edge(a, name, weight));

    }

    public boolean pathExists(Nod a, Nod b) {
        Set<Nod> visited = new HashSet<>();
        depthFirstVisitAll(a, visited);
        return visited.contains(b);
    }

    public List<Edge> getAnyPath(Nod from, Nod to) {
        Map<Nod, Nod> connection = new HashMap<>();
        depthFirstConnection(from, null, connection);
        if (!connection.containsKey(to)) {
            return Collections.emptyList();
        }
        return gatherPath(from, to, connection);
    }

    public List<Edge> getShortestPath(Nod from, Nod to) {
        Map<Nod, Nod> connections = new HashMap<>();
        connections.put(from, null);

        LinkedList<Nod> queue = new LinkedList<>();
        queue.add(from);
        while (!queue.isEmpty()) {
            Nod nod = queue.pollFirst();
            for (Edge edge : nodes.get(nod)) {
                Nod destination = edge.getDestination();
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

    }

    private List<Edge> gatherPath(Nod from, Nod to, Map<Nod, Nod> connection) {
        LinkedList<Edge> path = new LinkedList<>();
        Nod current = to;
        while (!current.equals(from)) {
            Nod next = connection.get(current);
            Edge edge = getEdgeBetween(next, current);
            path.addFirst(edge);
            current = next;
        }
        return Collections.unmodifiableList(path);
    }

    private Edge getEdgeBetween(Nod next, Nod current) {
        for (Edge edge : nodes.get(next)) {
            if (edge.getDestination().equals(current)) {
                return edge;
            }
        }

        return null;
    }

    private void depthFirstConnection(Nod to, Nod from, Map<Nod, Nod> connection) {
        connection.put(to, from);
        for (Edge edge : nodes.get(to)) {
            if (!connection.containsKey(edge.getDestination())) {
                depthFirstConnection(edge.getDestination(), to, connection);
            }
        }

    }

    private void depthFirstVisitAll(Nod current, Set<Nod> visited) {
        visited.add(current);
        for (Edge edge : nodes.get(current)) {
            if (!visited.contains(edge.getDestination())) {
                depthFirstVisitAll(edge.getDestination(), visited);
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Nod nod : nodes.keySet()) {
            sb.append(nod).append(": ").append(nodes.get(nod)).append("\n");
        }
        return sb.toString();
    }


}
