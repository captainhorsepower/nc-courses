package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.TagDAO;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.validation.NameValidator;
import com.netcracker.edu.varabey.service.validation.ServiceValidator;
import com.netcracker.edu.varabey.service.validation.exceptions.TagNotFoundException;
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
    public Tag create(Tag tag) {
        tagValidator.checkForPersist(tag);
        Tag existingTag = findTagByName(tag.getName());
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
    public Tag findTagByName(String name) {
        tagNameValidator.check(name);
        return tagDAO.findByName(name);
    }

    @Override
    public Tag getByName(String name) {
        Tag existing = findTagByName(name);
        return (existing == null) ? new Tag(name) : existing;
    }

    @Override
    public Tag updateName(Tag tagWithUpdate) {
        tagValidator.checkForUpdate(tagWithUpdate);
        Tag sourceTag = tagDAO.findById(tagWithUpdate.getId());
        tagValidator.checkFoundById(sourceTag, tagWithUpdate.getId());

        sourceTag.setName(tagWithUpdate.getName());
        return sourceTag;
    }

    @Override
    public void deleteTag(Long id) {
        tagValidator.checkIdIsNotNull(id);
        tagDAO.delete(id);
    }
}
