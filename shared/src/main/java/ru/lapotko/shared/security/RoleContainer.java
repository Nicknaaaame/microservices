package ru.lapotko.shared.security;

import org.springframework.stereotype.Component;

@Component("Roles")
public class RoleContainer {
    public final String INVENTORY_USER = "INVENTORY_USER";
    public final String PRODUCT_USER = "PRODUCT_USER";
    public final String ORDER_USER = "ORDER_USER";
}
