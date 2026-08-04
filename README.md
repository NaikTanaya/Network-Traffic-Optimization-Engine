# 🚀 Network Traffic Optimization Engine

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot)
![Redis](https://img.shields.io/badge/Redis-Cache-red?style=for-the-badge&logo=redis)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker)
![Kubernetes](https://img.shields.io/badge/Kubernetes-HPA-326CE5?style=for-the-badge&logo=kubernetes)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

A **high-performance network routing engine** built using **Spring Boot** that computes the lowest-latency path between virtual network nodes using **Dijkstra** and **A\*** graph algorithms.

To improve throughput and reduce repeated computations, the application integrates **Redis caching** with **automatic cache invalidation** whenever the network topology changes. The service is fully containerized with **Docker** and supports **Kubernetes Horizontal Pod Autoscaling (HPA)** for scalable cloud deployments on **Google Cloud Platform (GCP)**.

---

# 📌 Features

- ⚡ Compute shortest network routes using **Dijkstra** or **A\*** algorithms
- 🚀 Redis-backed caching for sub-millisecond repeated requests
- 🔄 Automatic cache eviction on topology updates
- 📈 Kubernetes Horizontal Pod Autoscaler (HPA)
- 🐳 Docker multi-stage container build
- ☁️ Ready for deployment on Google Cloud Compute Engine
- 🧩 RESTful API built with Spring Boot 3
- 📊 Low-latency route optimization for dynamic network environments

---

# 🛠 Tech Stack

| Category | Technology |
|-----------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Cache | Redis |
| Algorithms | Dijkstra, A* Search |
| Build Tool | Maven |
| Containerization | Docker |
| Orchestration | Kubernetes (HPA) |
| Cloud | Google Cloud Platform (Compute Engine) |

---

# 🏗 Architecture

```
                   +----------------------+
                   |      REST Client     |
                   +----------+-----------+
                              |
                              |
                    Spring Boot REST API
                              |
              +---------------+----------------+
              |                                |
      Routing Engine                  Redis Cache
   (Dijkstra / A*)             (Cached Shortest Paths)
              |
              |
       Network Graph
              |
      Topology Updates
              |
      Cache Eviction (@CacheEvict)
```

---

# 📂 Project Structure

```text
network-routing-engine/
│
├── src/
│   ├── main/
│   │   ├── java/com/routing/
│   │   │   ├── NetworkRoutingApplication.java
│   │   │   ├── controller/
│   │   │   │      RoutingController.java
│   │   │   ├── service/
│   │   │   │      RoutingEngineService.java
│   │   │   ├── model/
│   │   │   │      Node.java
│   │   │   │      Edge.java
│   │   │   │      RouteRequest.java
│   │   │   │      RouteResponse.java
│   │   │   │      TopologyUpdateRequest.java
│   │   │
│   │   └── resources/
│   │          application.properties
│   │
│   └── test/
│       └── RoutingEngineServiceTest.java
│
├── Dockerfile
├── k8s-deployment.yaml
├── pom.xml
└── README.md
```

---

# ⚙️ How It Works

## Route Optimization

1. Client sends a routing request.
2. The service first checks Redis for an existing route.
3. If found, the cached route is returned immediately.
4. Otherwise:
   - Dijkstra or A* computes the optimal path.
   - Result is stored in Redis.
   - Response is returned to the client.

---

## Cache Strategy

Routes are cached using composite keys:

```
algorithm:source:destination
```

Example:

```
ASTAR:A:F
```

Whenever the network topology changes, all cached routes are automatically invalidated using:

```java
@CacheEvict(value = "routes", allEntries = true)
```

This ensures clients never receive stale routing information.

---

# 🚀 Getting Started

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker Desktop
- Redis
- Kubernetes (optional)

---

## 1. Clone Repository

```bash
git clone https://github.com/yourusername/network-routing-engine.git

cd network-routing-engine
```

---

## 2. Start Redis

```bash
docker run -d \
--name redis-cache \
-p 6379:6379 \
redis:alpine
```

Verify Redis is running:

```bash
docker ps
```

---

## 3. Build Project

```bash
mvn clean install
```

---

## 4. Run Application

```bash
mvn spring-boot:run
```

Application starts at:

```
http://localhost:8080
```

---

# 📡 REST API

## 1️⃣ Calculate Optimal Route

**POST**

```
/api/v1/routing/optimize
```

### Request

```json
{
  "sourceNodeId": "A",
  "destinationNodeId": "F",
  "algorithm": "ASTAR"
}
```

### Response

```json
{
  "optimalPath": [
    "A",
    "B",
    "D",
    "E",
    "F"
  ],
  "totalLatencyMs": 29.0,
  "algorithmUsed": "ASTAR"
}
```

---

## 2️⃣ Update Network Topology

Updates edge latency and clears cached routes.

**PUT**

```
/api/v1/routing/topology
```

### Request

```json
{
  "sourceNodeId": "A",
  "targetNodeId": "B",
  "newLatencyWeight": 5.0
}
```

### Response

```text
Topology updated successfully.
Redis cache evicted.
```

---

# 🐳 Docker

## Build Image

```bash
docker build -t routing-engine:latest .
```

Run the container:

```bash
docker run -p 8080:8080 routing-engine:latest
```

---

# ☸ Kubernetes Deployment

Deploy the application:

```bash
kubectl apply -f k8s-deployment.yaml
```

Verify deployment:

```bash
kubectl get pods
```

Check services:

```bash
kubectl get svc
```

---

## Horizontal Pod Autoscaler

The project includes an HPA configured to:

- Minimum Pods: **2**
- Maximum Pods: **10**
- CPU Utilization Threshold: **70%**

View autoscaling:

```bash
kubectl get hpa routing-engine-hpa --watch
```

---

# 📈 Performance Optimizations

- Redis route caching dramatically reduces repeated graph traversals.
- Composite cache keys prevent duplicate computations.
- Automatic cache eviction guarantees consistency after topology updates.
- A* search improves traversal speed using heuristic guidance.
- Kubernetes HPA automatically scales during traffic spikes.

---

# 🔮 Future Enhancements

- Persistent graph storage (PostgreSQL / Neo4j)
- Distributed cache using Redis Cluster
- Real-time topology updates with Kafka
- Prometheus & Grafana monitoring
- OpenAPI / Swagger documentation
- JWT Authentication
- Rate limiting
- Multi-region deployment

---

# 👨‍💻 Author

**Your Name**

GitHub: [Tanaya Naik](https://github.com/NaikTanaya)

---

# 📄 License

This project is licensed under the **MIT License**.

Feel free to use, modify, and distribute this project.
