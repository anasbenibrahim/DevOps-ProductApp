# Product and Order Management Platform

A beginner-friendly Java 17 / Spring Boot 3 Maven multi-module microservices example.

## Architecture

```text
Client -> API Gateway :5000 -> product-service :5001
                           -> order-service :5002 -> product-service
                           -> user-service :5003
                           -> reporting-service :5004 -> product/order APIs
                 notification-service :5005
                 discovery-service (Eureka) :8761
```

Product and Order use Spring Data JPA with in-memory H2 databases. The gateway currently uses explicit local URLs so the project is easy to run; Eureka is provided as the discovery foundation and can be adopted by replacing those URLs with `lb://SERVICE-NAME` routes after adding Eureka clients.

## Prerequisites and startup

* JDK 17 and Maven 3.9+
* Build everything: `mvn clean verify`
* Start services in separate terminals:

```bash
mvn -pl discovery-service spring-boot:run
mvn -pl product-service spring-boot:run
mvn -pl order-service spring-boot:run
mvn -pl user-service spring-boot:run
mvn -pl notification-service spring-boot:run
mvn -pl reporting-service spring-boot:run
mvn -pl api-gateway spring-boot:run
```

Use the gateway (`http://localhost:5000`) for product, order, user, and report endpoints. Direct service ports are useful for troubleshooting.

## REST examples

```bash
# Create a product (201)
curl -X POST http://localhost:5000/api/products -H 'Content-Type: application/json' \
  -d '{"name":"Keyboard","description":"USB keyboard","price":25.00,"quantity":10}'
# List and retrieve products (200)
curl http://localhost:5000/api/products
curl http://localhost:5000/api/products/1
# Update and delete a product (200 / 204)
curl -X PUT http://localhost:5000/api/products/1 -H 'Content-Type: application/json' \
  -d '{"name":"Mechanical Keyboard","description":"RGB","price":55.00,"quantity":8}'
curl -i -X DELETE http://localhost:5000/api/products/1
# Validation error (400)
curl -i -X POST http://localhost:5000/api/products -H 'Content-Type: application/json' -d '{"name":"","price":0,"quantity":-1}'
# Create an order; its total is product price * quantity (201)
curl -X POST http://localhost:5000/api/orders -H 'Content-Type: application/json' \
  -d '{"productId":1,"quantity":2}'
curl http://localhost:5000/api/orders
curl http://localhost:5000/api/orders/1
curl -X PATCH http://localhost:5000/api/orders/1/status -H 'Content-Type: application/json' \
  -d '{"status":"SHIPPED"}'
curl -i -X DELETE http://localhost:5000/api/orders/1
# A missing product while creating an order returns 400; missing order/product retrieval returns 404
curl -i -X POST http://localhost:5000/api/orders -H 'Content-Type: application/json' \
  -d '{"productId":999,"quantity":1}'
curl http://localhost:5000/api/reports/summary
curl -X POST http://localhost:5000/api/users -H 'Content-Type: application/json' \
  -d '{"name":"Ada","email":"ada@example.com"}'
curl -X POST http://localhost:5005/api/notifications -H 'Content-Type: application/json' \
  -d '{"recipient":"ada@example.com","message":"Order created"}'
```

## Testing

`mvn test` runs JUnit 5 and Mockito unit tests. `mvn clean verify` compiles and runs all module tests. Product and Order business logic is separated into services so additional MockMvc integration tests can be added as practice exercises.
