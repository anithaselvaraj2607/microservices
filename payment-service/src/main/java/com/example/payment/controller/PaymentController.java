package com.example.payment.controller;

import com.example.common.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestBody OrderDto req) {
        // Simulate success
        return ResponseEntity.ok("PAID");
    }
}
