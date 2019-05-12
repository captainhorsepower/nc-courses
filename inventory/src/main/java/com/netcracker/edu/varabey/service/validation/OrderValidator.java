package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.entity.Order;
import com.netcracker.edu.varabey.entity.OrderItem;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidOrderException;
import com.netcracker.edu.varabey.spring.Validator;

@Validator
public class OrderValidator implements ServiceValidator<Order, Long> {
    private final DateTimeValidator dateTimeValidator;
    private final ServiceValidator<Customer, Long> customerValidator;
    private final ServiceValidator<OrderItem, Long> orderItemValidator;

    public OrderValidator(DateTimeValidator dateTimeValidator, ServiceValidator<Customer, Long> customerValidator, ServiceValidator<OrderItem, Long> orderItemValidator) {
        this.dateTimeValidator = dateTimeValidator;
        this.customerValidator = customerValidator;
        this.orderItemValidator = orderItemValidator;
    }

    @Override
    public Long extractId(Order resource) {
        return resource.getId();
    }

    @Override
    public void checkProperties(Order resource) {
        if (resource.isPaid() == null) {
            throw new InvalidOrderException("isPaid must not be null");
        }
        if (resource.getStatus() == null) {
            throw new InvalidOrderException("OrderStatus must not be null");
        }
        dateTimeValidator.check(resource.getCreatedOnDate());
        customerValidator.checkProperties(resource.getCustomer());
        resource.getItems().forEach(orderItemValidator::checkForPersist);
    }
}
