package com.routing.model;

import java.io.Serializable;
import java.util.List;

public class RouteResponse implements Serializable {
    private List<String> optimalPath;
    private double totalLatencyMs;
    private String algorithmUsed;

    public RouteResponse() {}

    public RouteResponse(List<String> optimalPath, double totalLatencyMs, String algorithmUsed) {
        this.optimalPath = optimalPath;
        this.totalLatencyMs = totalLatencyMs;
        this.algorithmUsed = algorithmUsed;
    }

    public List<String> getOptimalPath() { return optimalPath; }
    public void setOptimalPath(List<String> optimalPath) { this.optimalPath = optimalPath; }

    public double getTotalLatencyMs() { return totalLatencyMs; }
    public void setTotalLatencyMs(double totalLatencyMs) { this.totalLatencyMs = totalLatencyMs; }

    public String getAlgorithmUsed() { return algorithmUsed; }
    public void setAlgorithmUsed(String algorithmUsed) { this.algorithmUsed = algorithmUsed; }
}
