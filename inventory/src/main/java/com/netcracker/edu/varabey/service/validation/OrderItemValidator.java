package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.OrderItem;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.spring.Validator;
import lombok.Getter;

@Getter
@Validator
public class OrderItemValidator implements ServiceValidator<OrderItem, Long> {
    private final NameValidator orderItemNameValidator;
    private final PriceValidator orderItemPriceValidator;
    private final ServiceValidator<Category, Long> categoryValidator;
    private final ServiceValidator<Tag, Long> tagValidator;

    public OrderItemValidator(NameValidator orderItemNameValidator, PriceValidator orderItemPriceValidator, ServiceValidator<Category, Long> categoryValidator, ServiceValidator<Tag, Long> tagValidator) {
        this.orderItemNameValidator = orderItemNameValidator;
        this.orderItemPriceValidator = orderItemPriceValidator;
        this.categoryValidator = categoryValidator;
        this.tagValidator = tagValidator;
    }

    @Override
    public Long extractId(OrderItem resource) {
        return resource.getId();
    }

    @Override
    public void checkProperties(OrderItem resource) {
        orderItemNameValidator.check(resource.getName());
        orderItemPriceValidator.check(resource.getPrice());
        categoryValidator.checkProperties(resource.getCategory());
        resource.getTags().forEach(tagValidator::checkProperties);
    }
}
