package com.example.order.dto;
import jakarta.validation.constraints.*;
public record OrderRequest(@NotNull Long productId, @NotNull @Min(1) Integer quantity) {}
