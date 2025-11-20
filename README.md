# Microservices Modernization Demo

This demo contains a minimal multi-module Spring Boot project showing a monolith vs microservices split.

## Build
Run from project root:
```
mvn -T 1C clean package
```

Or build individual modules:
```
cd order-service
mvn clean package
```

## Run with Docker Compose
Requires Docker.
```
docker-compose up --build
```

Services:
- Eureka: http://localhost:8761
- API Gateway: http://localhost:8080
- Monolith: http://localhost:8081
- Order service: http://localhost:8101
- Payment service: http://localhost:8102
- Inventory service: http://localhost:8103

## Test
Create an order via gateway:
```
curl -X POST http://localhost:8080/orders -H "Content-Type: application/json" -d '{"id":1,"productId":"P1","quantity":1}'
```

Get order:
```
curl http://localhost:8080/orders/1
```

Notes:
- For simplicity, services use in-memory storage.
- Config-server is configured to look at a local directory (~/.config-repo) for config files for demo purposes.
