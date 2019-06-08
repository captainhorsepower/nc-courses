package com.netcracker.edu.varabey.inventory.validation;

import com.netcracker.edu.varabey.inventory.data.entity.Tag;
import com.netcracker.edu.varabey.inventory.exceptions.TagException;
import com.netcracker.edu.varabey.inventory.util.aop.beanannotation.Logged;
import com.netcracker.edu.varabey.inventory.util.aop.beanannotation.Validator;
import com.netcracker.edu.varabey.inventory.validation.fragments.NameValidator;
import lombok.Getter;

@Getter
@Validator
public class DefaultTagValidator implements TagValidator {
    private final NameValidator nameValidator;

    public DefaultTagValidator(NameValidator tagNameValidator) {
        this.nameValidator = tagNameValidator;
    }

    @Override
    public void checkNotNull(Tag resource) {
        if (resource == null) {
            throw new TagException("Tag must be NOT null, but is.");
        }
    }

    @Override
    public void checkIdIsNull(Long id) {
        if (id != null) {
            throw new TagException("Tag's id must be null");
        }
    }

    @Override
    public void checkIdIsNotNull(Long id) {
        if (id == null) {
            throw new TagException("Tag's id must be NOT null");
        }
    }

    @Override
    public void checkName(String name) {
        nameValidator.check(name);
    }

    @Logged(messageBefore = "Verifying tag is found...")
    @Override
    public Tag checkFound(Tag tag, String notFoundMessage) {
        if (tag == null) {
            throw new TagException(notFoundMessage);
        }
        return tag;
    }

    @Logged(messageBefore = "Verifying tag is found by name...")
    @Override
    public Tag checkFoundByName(Tag tag, String name) {
        return checkFound(tag, "Tag with name=\'" + name + "\' was not found");
    }

    @Logged(messageBefore = "Verifying tag is found by id...")
    @Override
    public Tag checkFoundById(Tag tag, Long id) {
        return checkFound(tag, "Tag with id=" + id + " was not found");
    }

    @Logged(messageBefore = "Verifying tag is correct...")
    @Override
    public void checkAllProperties(Tag tag) {
        nameValidator.check(tag.getName());
    }

    @Logged(messageBefore = "Verifying tag is fine for persist...")
    @Override
    public void checkForPersist(Tag tag) {
        checkNotNull(tag);
        checkIdIsNull(tag.getId());
        checkAllProperties(tag);
    }
}