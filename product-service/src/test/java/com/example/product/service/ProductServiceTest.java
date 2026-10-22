package com.example.product.service;

import com.example.product.dto.ProductRequest;
import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock ProductRepository repository;
    @InjectMocks ProductService service;

    @Test void findsProduct() {
        Product product = new Product("Keyboard", "USB", new BigDecimal("25.00"), 4);
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        assertEquals("Keyboard", service.findById(1L).name());
    }
    @Test void missingProductThrows() {
        when(repository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(9L));
    }
}
