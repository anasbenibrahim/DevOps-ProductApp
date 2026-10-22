package com.example.order.service;
import com.example.order.client.ProductClient;
import com.example.order.dto.*;
import com.example.order.entity.*;
import com.example.order.exception.ResourceNotFoundException;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service public class OrderService {
    private final OrderRepository repository; private final ProductClient products;
    public OrderService(OrderRepository repository, ProductClient products) { this.repository = repository; this.products = products; }
    public List<OrderResponse> findAll() { return repository.findAll().stream().map(OrderResponse::from).toList(); }
    public OrderResponse findById(Long id) { return OrderResponse.from(get(id)); }
    public OrderResponse create(OrderRequest request) {
        var product = products.getProduct(request.productId());
        return OrderResponse.from(repository.save(new CustomerOrder(request.productId(), request.quantity(),
                product.price().multiply(java.math.BigDecimal.valueOf(request.quantity())))));
    }
    public OrderResponse updateStatus(Long id, StatusRequest request) {
        CustomerOrder order = get(id); order.setStatus(request.status()); return OrderResponse.from(repository.save(order));
    }
    public void delete(Long id) { repository.delete(get(id)); }
    private CustomerOrder get(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order " + id + " was not found")); }
}
