package ru.lapotko.orderservice.exception;

import lombok.RequiredArgsConstructor;
import ru.lapotko.orderservice.model.Order;

@RequiredArgsConstructor
public class OrderApiException extends RuntimeException {
    protected final Order order;

    @Override
    public String getMessage() {
        return String.format("Occurred error with Order [%s]", order.getId());
    }
}
