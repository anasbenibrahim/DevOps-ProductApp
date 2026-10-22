package com.example.order.controller;
import com.example.order.dto.*;
import com.example.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/orders") public class OrderController {
    private final OrderService service; public OrderController(OrderService service) { this.service = service; }
    @GetMapping public List<OrderResponse> all() { return service.findAll(); }
    @GetMapping("/{id}") public OrderResponse one(@PathVariable Long id) { return service.findById(id); }
    @PostMapping public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }
    @PatchMapping("/{id}/status") public OrderResponse status(@PathVariable Long id, @Valid @RequestBody StatusRequest request) {
        return service.updateStatus(id, request);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
