package com.routing.service;

import com.routing.model.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoutingEngineService {

    private final Map<String, Node> nodeRegistry = new ConcurrentHashMap<>();
    private final Map<String, List<Edge>> adjacencyList = new ConcurrentHashMap<>();

    public RoutingEngineService() {
        // Node Registry with (X, Y) Coordinates for Euclidean distance calculation (A*)
        nodeRegistry.put("A", new Node("A", 0.0, 0.0));
        nodeRegistry.put("B", new Node("B", 10.0, 5.0));
        nodeRegistry.put("C", new Node("C", 5.0, 15.0));
        nodeRegistry.put("D", new Node("D", 20.0, 10.0));
        nodeRegistry.put("E", new Node("E", 15.0, 25.0));
        nodeRegistry.put("F", new Node("F", 30.0, 30.0));

        // Network Topology Adjacency List
        adjacencyList.put("A", new ArrayList<>(List.of(new Edge("B", 10.0), new Edge("C", 15.0))));
        adjacencyList.put("B", new ArrayList<>(List.of(new Edge("D", 12.0), new Edge("E", 15.0))));
        adjacencyList.put("C", new ArrayList<>(List.of(new Edge("F", 30.0))));
        adjacencyList.put("D", new ArrayList<>(List.of(new Edge("E", 2.0), new Edge("F", 10.0))));
        adjacencyList.put("E", new ArrayList<>(List.of(new Edge("F", 5.0))));
        adjacencyList.put("F", new ArrayList<>());
    }

    @Cacheable(value = "routes", key = "#request.algorithm + ':' + #request.sourceNodeId + ':' + #request.destinationNodeId")
    public RouteResponse computeOptimalPath(RouteRequest request) {
        String algo = request.getAlgorithm() != null ? request.getAlgorithm().toUpperCase() : "DIJKSTRA";

        if ("ASTAR".equals(algo)) {
            return runAStar(request.getSourceNodeId(), request.getDestinationNodeId());
        }
        return runDijkstra(request.getSourceNodeId(), request.getDestinationNodeId());
    }

    @CacheEvict(value = "routes", allEntries = true)
    public void updateTopology(TopologyUpdateRequest request) {
        List<Edge> edges = adjacencyList.get(request.getSourceNodeId());
        if (edges != null) {
            for (Edge edge : edges) {
                if (edge.getTargetNodeId().equals(request.getTargetNodeId())) {
                    edge.setLatencyWeight(request.getNewLatencyWeight());
                    break;
                }
            }
        }
    }

    private RouteResponse runDijkstra(String start, String target) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<NodeDistancePair> pq = new PriorityQueue<>(Comparator.comparingDouble(NodeDistancePair::distance));

        nodeRegistry.keySet().forEach(node -> distances.put(node, Double.MAX_VALUE));
        distances.put(start, 0.0);
        pq.add(new NodeDistancePair(start, 0.0));

        while (!pq.isEmpty()) {
            NodeDistancePair current = pq.poll();
            String u = current.nodeId();

            if (u.equals(target)) break;

            for (Edge edge : adjacencyList.getOrDefault(u, Collections.emptyList())) {
                String v = edge.getTargetNodeId();
                double newDist = distances.get(u) + edge.getLatencyWeight();

                if (newDist < distances.getOrDefault(v, Double.MAX_VALUE)) {
                    distances.put(v, newDist);
                    previousNodes.put(v, u);
                    pq.add(new NodeDistancePair(v, newDist));
                }
            }
        }
        return buildResponse(start, target, distances.get(target), previousNodes, "DIJKSTRA");
    }

    private RouteResponse runAStar(String start, String target) {
        Map<String, Double> gScore = new HashMap<>();
        Map<String, Double> fScore = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<NodeDistancePair> pq = new PriorityQueue<>(Comparator.comparingDouble(NodeDistancePair::distance));

        nodeRegistry.keySet().forEach(node -> {
            gScore.put(node, Double.MAX_VALUE);
            fScore.put(node, Double.MAX_VALUE);
        });

        gScore.put(start, 0.0);
        fScore.put(start, calculateEuclideanHeuristic(start, target));
        pq.add(new NodeDistancePair(start, fScore.get(start)));

        while (!pq.isEmpty()) {
            NodeDistancePair current = pq.poll();
            String u = current.nodeId();

            if (u.equals(target)) break;

            for (Edge edge : adjacencyList.getOrDefault(u, Collections.emptyList())) {
                String v = edge.getTargetNodeId();
                double tentativeGScore = gScore.get(u) + edge.getLatencyWeight();

                if (tentativeGScore < gScore.getOrDefault(v, Double.MAX_VALUE)) {
                    previousNodes.put(v, u);
                    gScore.put(v, tentativeGScore);
                    double f = tentativeGScore + calculateEuclideanHeuristic(v, target);
                    fScore.put(v, f);
                    pq.add(new NodeDistancePair(v, f));
                }
            }
        }
        return buildResponse(start, target, gScore.get(target), previousNodes, "ASTAR");
    }

    private double calculateEuclideanHeuristic(String nodeA, String nodeB) {
        Node a = nodeRegistry.get(nodeA);
        Node b = nodeRegistry.get(nodeB);
        if (a == null || b == null) return 0.0;
        return Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
    }

    private RouteResponse buildResponse(String start, String target, double totalLatency, Map<String, String> previousNodes, String algo) {
        List<String> path = new LinkedList<>();
        for (String at = target; at != null; at = previousNodes.get(at)) {
            path.add(0, at);
        }
        if (path.isEmpty() || !path.get(0).equals(start)) {
            throw new NoSuchElementException("No route exists between " + start + " and " + target);
        }
        return new RouteResponse(path, totalLatency, algo);
    }

    private record NodeDistancePair(String nodeId, double distance) {}
}
