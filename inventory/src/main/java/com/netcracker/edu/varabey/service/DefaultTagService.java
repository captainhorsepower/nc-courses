package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.TagDAO;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.validation.NameValidator;
import com.netcracker.edu.varabey.service.validation.ServiceValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultTagService implements TagService {
    private final TagDAO tagDAO;
    private final ServiceValidator<Tag, Long> tagValidator;
    private final NameValidator tagNameValidator;

    public DefaultTagService(TagDAO tagDAO, ServiceValidator<Tag, Long> tagValidator, NameValidator tagNameValidator) {
        this.tagDAO = tagDAO;
        this.tagValidator = tagValidator;
        this.tagNameValidator = tagNameValidator;
    }

    @Override
    public Tag createTag(Tag tag) {
        Tag existingTag = findByName(tag.getName());
        if (existingTag != null) {
            return existingTag;
        }
        tagValidator.checkForPersist(tag);
        return tagDAO.save(tag);
    }

    @Override
    public Tag findTag(Long id) {
        tagValidator.checkIdIsNotNull(id);
        return tagDAO.findById(id).orElse(null);
    }

    @Override
    public Tag findByName(String name) {
        tagNameValidator.check(name);
        return tagDAO.findByName(name);
    }

    @Override
    public Tag getByName(String name) {
        Tag existing = findByName(name);
        return (existing == null) ? new Tag(name) : existing;
    }
}
