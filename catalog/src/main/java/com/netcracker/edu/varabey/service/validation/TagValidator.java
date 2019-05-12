package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.spring.Validator;
import lombok.Getter;

@Getter
@Validator
public class TagValidator implements ServiceValidator<Tag, Long> {
    private final NameValidator nameValidator;

    public TagValidator(NameValidator tagNameValidator) {
        this.nameValidator = tagNameValidator;
    }

    @Override
    public Long extractId(Tag resource) {
        return resource.getId();
    }

    @Override
    public void checkProperties(Tag resource) {
        nameValidator.check(resource.getName());
    }
}
