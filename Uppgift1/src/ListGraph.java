import java.util.*;

/**
 * Oriktad graf
 */
public class ListGraph {

    private final Map<City, Set<Edge>> nodes = new HashMap<>();

    public void add(City city) {
        nodes.putIfAbsent(city, new HashSet<>());
    }

    public void connect(City a, City b, String name, double weight) {
        add(a);
        add(b);

        Set<Edge> aEdges = nodes.get(a);
        Set<Edge> bEdges = nodes.get(b);

        aEdges.add(new Edge(b, name, weight));
        bEdges.add(new Edge(a, name, weight));

    }

    public boolean pathExists(City a, City b) {
        Set<City> visited = new HashSet<>();
        depthFirstVisitAll(a, visited);
        return visited.contains(b);
    }

    public List<Edge> getAnyPath(City from, City to) {
        Map<City, City> connection = new HashMap<>();
        depthFirstConnection(from, null, connection);
        if (!connection.containsKey(to)) {
            return Collections.emptyList();
        }
        return gatherPath(from, to, connection);
    }

    public List<Edge> getShortestPath(City from, City to) {
        Map<City, City> connections = new HashMap<>();
        connections.put(from, null);

        LinkedList<City> queue = new LinkedList<>();
        queue.add(from);
        while (!queue.isEmpty()) {
            City city = queue.pollFirst();
            for (Edge edge : nodes.get(city)) {
                City destination = edge.getDestination();
                if (!connections.containsKey(destination)) {
                    connections.put(destination, city);
                    queue.add(destination);
                }
            }
        }

        if (!connections.containsKey(to)) {
            throw new IllegalStateException("no connection");
        }

        return gatherPath(from, to, connections);

    }

    private List<Edge> gatherPath(City from, City to, Map<City, City> connection) {
        LinkedList<Edge> path = new LinkedList<>();
        City current = to;
        while (!current.equals(from)) {
            City next = connection.get(current);
            Edge edge = getEdgeBetween(next, current);
            path.addFirst(edge);
            current = next;
        }
        return Collections.unmodifiableList(path);
    }

    private Edge getEdgeBetween(City next, City current) {
        for (Edge edge : nodes.get(next)) {
            if (edge.getDestination().equals(current)) {
                return edge;
            }
        }

        return null;
    }

    private void depthFirstConnection(City to, City from, Map<City, City> connection) {
        connection.put(to, from);
        for (Edge edge : nodes.get(to)) {
            if (!connection.containsKey(edge.getDestination())) {
                depthFirstConnection(edge.getDestination(), to, connection);
            }
        }

    }

    private void depthFirstVisitAll(City current, Set<City> visited) {
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
        for (City city : nodes.keySet()) {
            sb.append(city).append(": ").append(nodes.get(city)).append("\n");
        }
        return sb.toString();
    }


}
