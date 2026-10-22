package com.example.notification;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
@RestController @RequestMapping("/api/notifications") public class NotificationController {
    private final List<Notification> notifications = new CopyOnWriteArrayList<>();
    @GetMapping public List<Notification> all() { return notifications; }
    @PostMapping public ResponseEntity<Notification> send(@RequestBody NotificationRequest request) {
        Notification notification = new Notification(UUID.randomUUID(), request.recipient(), request.message(), Instant.now());
        notifications.add(notification); return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }
    public record Notification(UUID id, String recipient, String message, Instant sentAt) {}
    public record NotificationRequest(@NotBlank String recipient, @NotBlank String message) {}
}
