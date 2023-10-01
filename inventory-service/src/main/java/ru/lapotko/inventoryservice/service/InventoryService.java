package ru.lapotko.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lapotko.inventoryservice.dto.InventoryResponse;
import ru.lapotko.inventoryservice.model.Inventory;
import ru.lapotko.inventoryservice.repository.InventoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Checking Inventory");
        List<Inventory> found = inventoryRepository.findBySkuCodeIn(skuCode);
        return skuCode.stream()
                .map(code -> InventoryResponse.builder()
                        .skuCode(code)
                        .isInStock(found.stream()
                                .anyMatch(inventory ->
                                        inventory.getSkuCode().equals(code) && inventory.getQuantity() > 0))
                        .build())
                .toList();
    }
}
