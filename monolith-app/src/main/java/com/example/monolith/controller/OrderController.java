package com.example.monolith.controller;

import com.example.common.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Map<Long, OrderDto> store = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto dto) {
        dto.setStatus("COMPLETED"); // monolith does everything locally
        store.put(dto.getId(), dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        return ResponseEntity.of(java.util.Optional.ofNullable(store.get(id)));
    }
}
