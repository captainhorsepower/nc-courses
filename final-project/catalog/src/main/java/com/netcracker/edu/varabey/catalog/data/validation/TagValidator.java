package com.netcracker.edu.varabey.catalog.data.validation;

import com.netcracker.edu.varabey.catalog.data.entity.Tag;

public interface TagValidator extends ServiceValidator<Tag, Long> {
    void checkName(String name);
    void checkForPersist(Tag tag);
    Tag checkFoundByName(Tag tag, String name);
}
