package ru.lapotko.inventoryservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lapotko.inventoryservice.dto.InventoryResponse;
import ru.lapotko.inventoryservice.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @PreAuthorize("hasAuthority(@Roles.INVENTORY_USER)")
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> skuCode) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return ResponseEntity.ok(inventoryService.isInStock(skuCode));
    }
}
