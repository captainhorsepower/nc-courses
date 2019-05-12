package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.spring.Validator;

@Validator
public class CustomerValidator implements ServiceValidator<Customer, Long> {
    private final EmailValidator emailValidator;

    public CustomerValidator(EmailValidator emailValidator) {
        this.emailValidator = emailValidator;
    }

    @Override
    public Long extractId(Customer resource) {
        return resource.getId();
    }

    @Override
    public void checkProperties(Customer resource) {
        emailValidator.check(resource.getEmail());
    }
}
