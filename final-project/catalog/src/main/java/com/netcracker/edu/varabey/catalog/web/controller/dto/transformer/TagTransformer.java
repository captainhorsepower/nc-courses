package com.netcracker.edu.varabey.catalog.web.controller.dto.transformer;

import com.netcracker.edu.varabey.catalog.web.controller.dto.TagDTO;
import com.netcracker.edu.varabey.catalog.data.entity.Offer;
import com.netcracker.edu.varabey.catalog.data.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagTransformer implements Transformer<Tag, TagDTO> {
    @Override
    public Tag toEntity(TagDTO dto) {
        String name = (dto.getName() == null) ? null : dto.getName().trim();
        return new Tag(name);
    }

    @Override
    public TagDTO toDto(Tag tag) {
        TagDTO dto = new TagDTO(tag.getId(), tag.getName());
        tag.getOffers().stream()
                .map(Offer::getId)
                .forEach(dto::addOffer);
        return dto;
    }
}