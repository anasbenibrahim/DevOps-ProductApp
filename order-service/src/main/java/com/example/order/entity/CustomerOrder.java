package com.example.order.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name = "orders")
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private Long productId;
    @Column(nullable = false) private Integer quantity;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private OrderStatus status;
    protected CustomerOrder() {}
    public CustomerOrder(Long productId, Integer quantity, BigDecimal totalPrice) {
        this.productId = productId; this.quantity = quantity; this.totalPrice = totalPrice; this.status = OrderStatus.CREATED;
    }
    public Long getId() { return id; } public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; } public BigDecimal getTotalPrice() { return totalPrice; }
    public OrderStatus getStatus() { return status; } public void setQuantity(Integer q) { quantity = q; }
    public void setTotalPrice(BigDecimal p) { totalPrice = p; } public void setStatus(OrderStatus s) { status = s; }
}
