package com.example.order.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.stream.Collectors;
@RestControllerAdvice public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiError notFound(ResourceNotFoundException e) { return new ApiError(Instant.now(), 404, "Not Found", e.getMessage()); }
    @ExceptionHandler(ProductUnavailableException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError product(ProductUnavailableException e) { return new ApiError(Instant.now(), 400, "Product Unavailable", e.getMessage()); }
    @ExceptionHandler(MethodArgumentNotValidException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError invalid(MethodArgumentNotValidException e) {
        return new ApiError(Instant.now(), 400, "Bad Request", e.getBindingResult().getFieldErrors().stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage()).collect(Collectors.joining(", ")));
    }
}
