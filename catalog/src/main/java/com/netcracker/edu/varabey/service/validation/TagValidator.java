package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Tag;

public interface TagValidator extends ServiceValidator<Tag, Long> {
    void checkName(String name);
    void checkForPersist(Tag tag);
    Tag checkFoundByName(Tag tag, String name);
}
