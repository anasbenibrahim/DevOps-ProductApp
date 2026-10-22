package com.example.user;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
@RestController @RequestMapping("/api/users") public class UserController {
    private final Map<Long, User> users = new HashMap<>(); private final AtomicLong ids = new AtomicLong();
    @GetMapping public Collection<User> all() { return users.values(); }
    @GetMapping("/{id}") public User one(@PathVariable Long id) { User user = users.get(id); if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"); return user; }
    @PostMapping public ResponseEntity<User> create(@Valid @RequestBody UserRequest request) {
        long id = ids.incrementAndGet(); User user = new User(id, request.name(), request.email()); users.put(id, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    public record User(Long id, String name, String email) {}
    public record UserRequest(@NotBlank String name, @NotBlank @Email String email) {}
}
