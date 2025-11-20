package com.example.order.controller;

import com.example.common.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final RestTemplate restTemplate;
    private final Map<Long, OrderDto> store = new ConcurrentHashMap<>();

    public OrderController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto dto) {
        dto.setStatus("CREATED");
        store.put(dto.getId(), dto);

        try {
            String invUrl = "http://INVENTORY-SERVICE/inventory/reserve/" + dto.getProductId() + "/" + dto.getQuantity();
            ResponseEntity<String> invResp = restTemplate.postForEntity(invUrl, null, String.class);

            if (invResp.getStatusCode().is2xxSuccessful()) {
                String payUrl = "http://PAYMENT-SERVICE/payments/process";
                ResponseEntity<String> payResp = restTemplate.postForEntity(payUrl, dto, String.class);
                if (payResp.getStatusCode().is2xxSuccessful()) {
                    dto.setStatus("COMPLETED");
                } else {
                    dto.setStatus("PAYMENT_FAILED");
                }
            } else {
                dto.setStatus("OUT_OF_STOCK");
            }
        } catch (Exception e) {
            dto.setStatus("FAILED");
        }

        store.put(dto.getId(), dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        return ResponseEntity.of(java.util.Optional.ofNullable(store.get(id)));
    }
}
