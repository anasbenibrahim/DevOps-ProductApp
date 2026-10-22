package com.example.order.dto;
import com.example.order.entity.*;
import java.math.BigDecimal;
public record OrderResponse(Long id, Long productId, Integer quantity, BigDecimal totalPrice, OrderStatus status) {
    public static OrderResponse from(CustomerOrder o) { return new OrderResponse(o.getId(), o.getProductId(), o.getQuantity(), o.getTotalPrice(), o.getStatus()); }
}
