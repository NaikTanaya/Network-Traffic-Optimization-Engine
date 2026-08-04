package com.routing.controller;

import com.routing.model.*;
import com.routing.service.RoutingEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/routing")
public class RoutingController {

    private final RoutingEngineService routingService;

    public RoutingController(RoutingEngineService routingService) {
        this.routingService = routingService;
    }

    @PostMapping("/optimize")
    public ResponseEntity<RouteResponse> getOptimalRoute(@RequestBody RouteRequest request) {
        return ResponseEntity.ok(routingService.computeOptimalPath(request));
    }

    @PutMapping("/topology")
    public ResponseEntity<String> updateTopology(@RequestBody TopologyUpdateRequest request) {
        routingService.updateTopology(request);
        return ResponseEntity.ok("Topology updated successfully. Redis cache evicted.");
    }
}
