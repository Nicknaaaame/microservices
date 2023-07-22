package ru.lapotko.orderservice.exception;

import lombok.RequiredArgsConstructor;
import ru.lapotko.orderservice.model.Order;

public class OrderApiException extends RuntimeException {
    protected final Order order;
    protected final String message;

    public OrderApiException(Order order) {
        this.order = order;
        this.message = String.format("Occurred error with Order [%s]", order.getId());
    }

    public OrderApiException(String message) {
        super(message);
        this.order = null;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
