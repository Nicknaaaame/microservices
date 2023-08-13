package ru.lapotko.orderservice.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lapotko.orderservice.dto.InventoryResponse;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/inventory")
    List<InventoryResponse> isInStock(@RequestParam List<String> skuCode);
}
