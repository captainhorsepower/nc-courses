package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.spring.Validator;
import lombok.Getter;

@Getter
@Validator
public class TagValidator implements ServiceValidator<Tag, Long> {
    private final NameValidator tagNameValidator;

    public TagValidator(NameValidator tagNameValidator) {
        this.tagNameValidator = tagNameValidator;
    }

    @Override
    public Long extractId(Tag resource) {
        return resource.getId();
    }

    @Override
    public void checkProperties(Tag resource) {
        tagNameValidator.check(resource.getName());
    }
}
