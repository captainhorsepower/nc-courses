package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.TagDAO;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.validation.TagValidator;
import com.netcracker.edu.varabey.service.validation.exceptions.TagException;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public Tag create(Tag tag) {
        tagValidator.checkForPersist(tag);
        Tag existingTag = findByName(tag.getName());
        if (existingTag != null) {
            return existingTag;
        }
        return tagDAO.save(tag);
    }

    @Override
    public Tag findById(Long id) {
        tagValidator.checkIdIsNotNull(id);
        return tagDAO.findById(id);
    }

    @Override
    public Tag findByName(String name) {
        tagValidator.checkName(name);
        return tagDAO.findByName(name);
    }

    @Override
    public Tag find(String input) {
        if (input.matches("\\d+")) {
            Long id = Long.parseLong(input);
            return findById(id);
        } else {
            String name = input.replaceAll("%20", " ");
            return findByName(name);
        }
    }

    @Override
    public Tag getByName(String name) {
        Tag existing = findByName(name);
        return (existing == null) ? new Tag(name) : existing;
    }

    @Override
    public Tag updateName(Tag tagWithUpdate) {
        tagValidator.checkAllProperties(tagWithUpdate);
        tagValidator.checkIdIsNotNull(tagWithUpdate.getId());

        Tag sourceTag = tagValidator.checkFoundById(tagDAO.findById(tagWithUpdate.getId()), tagWithUpdate.getId());

        sourceTag.setName(tagWithUpdate.getName());
        return sourceTag;
    }

    @Override
    public void deleteTag(Long id) {
        tagValidator.checkIdIsNotNull(id);
        try {
            tagDAO.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new TagException("Tag with id=" + id + " was not found. Unable to delete.");
        }
    }
}
