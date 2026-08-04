package com.routing.model;

import java.io.Serializable;

public class RouteRequest implements Serializable {
    private String sourceNodeId;
    private String destinationNodeId;
    private String algorithm; // "DIJKSTRA" or "ASTAR"

    public RouteRequest() {}

    public RouteRequest(String sourceNodeId, String destinationNodeId, String algorithm) {
        this.sourceNodeId = sourceNodeId;
        this.destinationNodeId = destinationNodeId;
        this.algorithm = algorithm;
    }

    public String getSourceNodeId() { return sourceNodeId; }
    public void setSourceNodeId(String sourceNodeId) { this.sourceNodeId = sourceNodeId; }

    public String getDestinationNodeId() { return destinationNodeId; }
    public void setDestinationNodeId(String destinationNodeId) { this.destinationNodeId = destinationNodeId; }

    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
}
