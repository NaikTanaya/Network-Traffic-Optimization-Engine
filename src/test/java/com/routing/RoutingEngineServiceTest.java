package com.routing;

import com.routing.model.RouteRequest;
import com.routing.model.RouteResponse;
import com.routing.service.RoutingEngineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoutingEngineServiceTest {

    private RoutingEngineService routingService;

    @BeforeEach
    void setUp() {
        routingService = new RoutingEngineService();
    }

    @Test
    void testDijkstraRouteComputation() {
        RouteRequest request = new RouteRequest("A", "F", "DIJKSTRA");
        RouteResponse response = routingService.computeOptimalPath(request);

        assertNotNull(response);
        assertEquals("DIJKSTRA", response.getAlgorithmUsed());
        assertEquals(23.0, response.getTotalLatencyMs());
        assertEquals(4, response.getOptimalPath().size());
        assertEquals("A", response.getOptimalPath().get(0));
        assertEquals("F", response.getOptimalPath().get(3));
    }

    @Test
    void testAStarRouteComputation() {
        RouteRequest request = new RouteRequest("A", "F", "ASTAR");
        RouteResponse response = routingService.computeOptimalPath(request);

        assertNotNull(response);
        assertEquals("ASTAR", response.getAlgorithmUsed());
        assertEquals(23.0, response.getTotalLatencyMs());
        assertFalse(response.getOptimalPath().isEmpty());
    }
}
