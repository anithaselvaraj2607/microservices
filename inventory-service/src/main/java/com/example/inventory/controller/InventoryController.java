package com.example.inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @PostMapping("/reserve/{productId}/{qty}")
    public ResponseEntity<String> reserve(@PathVariable String productId, @PathVariable int qty) {
        // For demo: always succeed
        return ResponseEntity.ok("RESERVED");
    }
}
