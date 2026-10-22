package com.example.order.dto;
import com.example.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
public record StatusRequest(@NotNull OrderStatus status) {}
