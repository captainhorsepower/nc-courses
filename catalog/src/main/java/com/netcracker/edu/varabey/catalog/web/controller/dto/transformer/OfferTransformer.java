package com.netcracker.edu.varabey.catalog.web.controller.dto.transformer;

import com.netcracker.edu.varabey.catalog.data.entity.Category;
import com.netcracker.edu.varabey.catalog.data.entity.Offer;
import com.netcracker.edu.varabey.catalog.data.entity.Price;
import com.netcracker.edu.varabey.catalog.data.entity.Tag;
import com.netcracker.edu.varabey.catalog.web.controller.dto.OfferDTO;
import org.springframework.stereotype.Component;

@Component
public class OfferTransformer implements Transformer<Offer, OfferDTO> {
    @Override
    public Offer toEntity(OfferDTO dto) {
        Offer offer = new Offer();
        offer.setName(dto.getName());
        offer.setPrice( (dto.getPrice() == null) ? null : new Price(Double.valueOf(String.format("%.2f", dto.getPrice()))) );
        offer.setCategory( new Category((dto.getCategory() == null) ? "" : dto.getCategory().trim()) );
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
