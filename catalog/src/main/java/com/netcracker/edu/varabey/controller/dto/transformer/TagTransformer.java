package com.netcracker.edu.varabey.controller.dto.transformer;

import com.netcracker.edu.varabey.controller.dto.OfferDTO;
import com.netcracker.edu.varabey.controller.dto.TagDTO;
import com.netcracker.edu.varabey.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagTransformer implements Transformer<Tag, TagDTO> {
    @Override
    public Tag toEntity(TagDTO dto) {
        return new Tag(dto.getName());
    }

    @Override
    public TagDTO toDto(Tag tag) {
        TagDTO dto = new TagDTO(tag.getId(), tag.getName());
        tag.getOffers().stream()
                .map( offer -> new OfferDTO(offer.getId(), offer.getName(), offer.getPrice().getValue(), offer.getCategory().getName()))
                .forEach(dto::addOffer);
        return dto;
    }
}
