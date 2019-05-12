package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.spring.Validator;
import lombok.Getter;

@Getter
@Validator
public class OfferValidator implements ServiceValidator<Offer, Long> {
    private final NameValidator offerNameValidator;
    private final PriceValidator offerPriceValidator;
    private final ServiceValidator<Category, Long> categoryValidator;
    private final ServiceValidator<Tag, Long> tagValidator;

    public OfferValidator(NameValidator offerNameValidator, PriceValidator offerPriceValidator, ServiceValidator<Category, Long> categoryValidator, ServiceValidator<Tag, Long> tagValidator) {
        this.offerNameValidator = offerNameValidator;
        this.offerPriceValidator = offerPriceValidator;
        this.categoryValidator = categoryValidator;
        this.tagValidator = tagValidator;
    }

    @Override
    public Long extractId(Offer resource) {
        return resource.getId();
    }

    @Override
    public void checkProperties(Offer resource) {
        offerNameValidator.check(resource.getName());
        offerPriceValidator.check(resource.getPrice());
        categoryValidator.checkProperties(resource.getCategory());
        resource.getTags().forEach(tagValidator::checkProperties);
    }
}
