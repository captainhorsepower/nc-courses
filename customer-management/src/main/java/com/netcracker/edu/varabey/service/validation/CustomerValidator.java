package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.spring.Validator;

@Validator
public class CustomerValidator implements ServiceValidator<Customer, Long> {
    private final NameValidator customerNameValidator;
    private final AgeValidator ageValidator;
    private final EmailValidator emailValidator;

    public CustomerValidator(NameValidator customerNameValidator, AgeValidator ageValidator, EmailValidator emailValidator) {
        this.customerNameValidator = customerNameValidator;
        this.ageValidator = ageValidator;
        this.emailValidator = emailValidator;
    }

    @Override
    public Long extractId(Customer resource) {
        return resource.getId();
    }

    @Override
    public void checkProperties(Customer resource) {
        customerNameValidator.check(resource.getFio());
        ageValidator.check(resource.getAge());
        emailValidator.check(resource.getEmail());
    }
}
