package com.routing.model;

import java.io.Serializable;

public class Edge implements Serializable {
    private String targetNodeId;
    private double latencyWeight;

    public Edge() {}

    public Edge(String targetNodeId, double latencyWeight) {
        this.targetNodeId = targetNodeId;
        this.latencyWeight = latencyWeight;
    }

    public String getTargetNodeId() { return targetNodeId; }
    public void setTargetNodeId(String targetNodeId) { this.targetNodeId = targetNodeId; }

    public double getLatencyWeight() { return latencyWeight; }
    public void setLatencyWeight(double latencyWeight) { this.latencyWeight = latencyWeight; }
}
