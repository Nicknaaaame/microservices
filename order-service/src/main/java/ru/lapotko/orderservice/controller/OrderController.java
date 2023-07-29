package ru.lapotko.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lapotko.orderservice.dto.OrderRequest;
import ru.lapotko.orderservice.dto.OrderResponse;
import ru.lapotko.orderservice.mapper.OrderMapper;
import ru.lapotko.orderservice.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper = new OrderMapper();

    @PostMapping
    @PreAuthorize("hasAuthority(@Roles.ORDER_USER)")
    public ResponseEntity<Void> placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderMapper.map(orderRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority(@Roles.ORDER_USER)")
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders().stream()
                .map(orderMapper::map)
                .collect(Collectors.toList()));
    }
}
