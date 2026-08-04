# Network Traffic Optimization Engine

A microservice-based traffic routing engine that calculates optimal latency paths across virtual network nodes using graph algorithms (Dijkstra and A*). 

## Tech Stack
- **Backend:** Java 17, Spring Boot 3
- **Caching:** Redis
- **Containerization & Orchestration:** Docker, Kubernetes (HPA enabled)
- **Cloud Infrastructure:** GCP Compute Engine

## How to Run Locally

### 1. Run Redis Container
```bash
docker run -d --name redis-cache -p 6379:6379 redis:alpine
