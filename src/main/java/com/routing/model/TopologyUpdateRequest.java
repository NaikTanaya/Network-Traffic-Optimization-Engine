package com.routing.model;

import java.io.Serializable;

public class TopologyUpdateRequest implements Serializable {
    private String sourceNodeId;
    private String targetNodeId;
    private double newLatencyWeight;

    public TopologyUpdateRequest() {}

    public TopologyUpdateRequest(String sourceNodeId, String targetNodeId, double newLatencyWeight) {
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
        this.newLatencyWeight = newLatencyWeight;
    }

    public String getSourceNodeId() { return sourceNodeId; }
    public void setSourceNodeId(String sourceNodeId) { this.sourceNodeId = sourceNodeId; }

    public String getTargetNodeId() { return targetNodeId; }
    public void setTargetNodeId(String targetNodeId) { this.targetNodeId = targetNodeId; }

    public double getNewLatencyWeight() { return newLatencyWeight; }
    public void setNewLatencyWeight(double newLatencyWeight) { this.newLatencyWeight = newLatencyWeight; }
}
