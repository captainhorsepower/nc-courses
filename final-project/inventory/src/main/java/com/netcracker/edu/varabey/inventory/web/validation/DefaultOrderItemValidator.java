package com.netcracker.edu.varabey.inventory.web.validation;

import com.netcracker.edu.varabey.inventory.data.entity.OrderItem;
import com.netcracker.edu.varabey.inventory.data.entity.Price;
import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.OrderItemException;
import com.netcracker.edu.varabey.inventory.web.validation.fragments.NameValidator;
import com.netcracker.edu.varabey.inventory.web.validation.fragments.PriceValidator;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Validator
public class DefaultOrderItemValidator implements OrderItemValidator {
    private final NameValidator nameValidator;
    private final PriceValidator priceValidator;
    private final CategoryValidator categoryValidator;
    private final TagValidator tagValidator;

    public DefaultOrderItemValidator(NameValidator orderItemNameValidator, PriceValidator priceValidator, CategoryValidator categoryValidator, TagValidator tagValidator) {
        this.nameValidator = orderItemNameValidator;
        this.priceValidator = priceValidator;
        this.categoryValidator = categoryValidator;
        this.tagValidator = tagValidator;
    }

    @Override
    public void checkNotNull(OrderItem resource) {
        if (resource == null) {
            throw new OrderItemException("OrderItem must be NOT null, but is.");
        }
    }

    @Override
    public void checkIdIsNull(Long id) {
        if (id != null) {
            throw new OrderItemException("OrderItem's id must be null");
        }
    }

    @Override
    public void checkIdIsNotNull(Long id) {
        if (id == null) {
            throw new OrderItemException("OrderItem's id must be NOT null");
        }
    }

    @Override
    public void checkName(String name) {
        nameValidator.check(name);
    }

    @Override
    public void checkPrice(Price price) {
        priceValidator.check(price);
    }

    @Override
    public OrderItem checkFound(OrderItem orderItem, String notFoundMessage) {
        if (orderItem == null) {
            throw new OrderItemException(notFoundMessage, HttpStatus.NOT_FOUND);
        }
        return orderItem;
    }

    @Logged(messageBefore = "Verifying orderItem is found by id...")
    @Override
    public OrderItem checkFoundById(OrderItem orderItem, Long id) {
        return checkFound(orderItem, "OrderItem with id=" + id + " was not found");
    }

    @Logged(messageBefore = "Verifying all orderItem properties...")
    @Override
    public void checkAllProperties(OrderItem orderItem) {
        checkNotNull(orderItem);
        nameValidator.check(orderItem.getName());
        priceValidator.check(orderItem.getPrice());
        categoryValidator.checkAllProperties(orderItem.getCategory());
        orderItem.getTags().forEach(tagValidator::checkAllProperties);
    }

    @Logged(messageBefore = "Verifying orderItem for persist...")
    @Override
    public void checkForPersist(OrderItem orderItem) {
        checkNotNull(orderItem);
        checkIdIsNull(orderItem.getId());
        checkAllProperties(orderItem);
    }
}
