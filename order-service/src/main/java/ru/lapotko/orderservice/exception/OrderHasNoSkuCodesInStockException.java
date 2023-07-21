package ru.lapotko.orderservice.exception;

import ru.lapotko.orderservice.model.Order;

import java.util.List;

public class OrderHasNoSkuCodesInStockException extends OrderApiException {
    private final List<String> scuCodes;

    public OrderHasNoSkuCodesInStockException(Order order, List<String> scuCodes) {
        super(order);
        this.scuCodes = scuCodes;
    }

    @Override
    public String getMessage() {
        return String.format("Has no in stock scuCodes %s for new Order", scuCodes.toString());
    }
}
