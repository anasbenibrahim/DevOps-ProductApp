package com.example.order.client;
import com.example.order.exception.ProductUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.math.BigDecimal;
@Component
public class ProductClient {
    private final RestClient client;
    public ProductClient(@Value("${product-service.url:http://localhost:5001}") String url, RestClient.Builder builder) {
        this.client = builder.baseUrl(url).build();
    }
    public ProductDetails getProduct(Long id) {
        try {
            ProductDetails result = client.get().uri("/api/products/{id}", id).retrieve().body(ProductDetails.class);
            if (result == null) throw new ProductUnavailableException("Product " + id + " was not found");
            return result;
        } catch (RuntimeException ex) {
            throw new ProductUnavailableException("Product " + id + " is unavailable");
        }
    }
    public record ProductDetails(Long id, String name, String description, BigDecimal price, Integer quantity) {}
}
