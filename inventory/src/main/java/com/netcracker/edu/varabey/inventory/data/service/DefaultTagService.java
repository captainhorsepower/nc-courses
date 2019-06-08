package com.netcracker.edu.varabey.inventory.data.service;

import com.netcracker.edu.varabey.inventory.data.dao.TagDAO;
import com.netcracker.edu.varabey.inventory.data.entity.Tag;
import com.netcracker.edu.varabey.inventory.data.validation.TagValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultTagService implements TagService {
    private final TagDAO tagDAO;
    private final TagValidator tagValidator;

    public DefaultTagService(TagDAO tagDAO, TagValidator tagValidator) {
        this.tagDAO = tagDAO;
        this.tagValidator = tagValidator;
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
        tagValidator.checkName(name);
        return tagDAO.findByName(name);
    }

    @Override
    public Tag getByName(String name) {
        Tag existing = findByName(name);
        return (existing == null) ? new Tag(name) : existing;
    }
}
