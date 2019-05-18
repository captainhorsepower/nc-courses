package com.netcracker.edu.varabey.controller.dto.transformer;

import com.netcracker.edu.varabey.controller.dto.OfferDTO;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Price;
import com.netcracker.edu.varabey.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class OfferTransformer implements Transformer<Offer, OfferDTO> {
    @Override
    public Offer toEntity(OfferDTO dto) {
        Offer offer = new Offer();
        offer.setName(dto.getName());
        offer.setPrice( new Price(dto.getPrice()) );
        offer.setCategory( new Category(dto.getCategory().trim()) );
        dto.getTags().forEach(tagName -> offer.addTag( new Tag(tagName.trim())) );
        return offer;
    }

    @Override
    public OfferDTO toDto(Offer offer) {
        OfferDTO dto = new OfferDTO(offer.getId(), offer.getName(), offer.getPrice().getValue(), offer.getCategory().getName());
        offer.getTags().forEach( tag -> dto.addTag( tag.getName() ));
        return dto;
    }
}
