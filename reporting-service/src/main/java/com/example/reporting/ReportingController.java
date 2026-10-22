package com.example.reporting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import java.math.BigDecimal;
import java.util.*;
@RestController @RequestMapping("/api/reports") public class ReportingController {
    private final RestClient products; private final RestClient orders;
    public ReportingController(RestClient.Builder builder,
            @Value("${product-service.url:http://localhost:5001}") String productUrl,
            @Value("${order-service.url:http://localhost:5002}") String orderUrl) {
        products = builder.baseUrl(productUrl).build(); orders = builder.baseUrl(orderUrl).build();
    }
    @GetMapping("/summary") public Summary summary() {
        Product[] productList = Optional.ofNullable(products.get().uri("/api/products").retrieve().body(Product[].class)).orElse(new Product[0]);
        Order[] orderList = Optional.ofNullable(orders.get().uri("/api/orders").retrieve().body(Order[].class)).orElse(new Order[0]);
        int inventory = Arrays.stream(productList).mapToInt(Product::quantity).sum();
        BigDecimal revenue = Arrays.stream(orderList).map(Order::totalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Summary(productList.length, orderList.length, inventory, revenue);
    }
    record Product(Long id, String name, String description, BigDecimal price, Integer quantity) {}
    record Order(Long id, Long productId, Integer quantity, BigDecimal totalPrice, String status) {}
    public record Summary(int productCount, int orderCount, int inventoryUnits, BigDecimal orderValue) {}
}
