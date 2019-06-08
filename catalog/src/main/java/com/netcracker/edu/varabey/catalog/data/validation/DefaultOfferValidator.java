package com.netcracker.edu.varabey.catalog.data.validation;

import com.netcracker.edu.varabey.catalog.data.entity.Category;
import com.netcracker.edu.varabey.catalog.data.entity.Offer;
import com.netcracker.edu.varabey.catalog.data.entity.Price;
import com.netcracker.edu.varabey.catalog.data.entity.Tag;
import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.OfferException;
import com.netcracker.edu.varabey.catalog.data.validation.util.NameValidator;
import com.netcracker.edu.varabey.catalog.data.validation.util.PriceValidator;
import lombok.Getter;

@Getter
@Validator
public class DefaultOfferValidator implements OfferValidator {
    private final NameValidator nameValidator;
    private final PriceValidator priceValidator;
    private final ServiceValidator<Category, Long> categoryValidator;
    private final ServiceValidator<Tag, Long> tagValidator;

    public DefaultOfferValidator(NameValidator offerNameValidator, PriceValidator offerPriceValidator, ServiceValidator<Category, Long> categoryValidator, ServiceValidator<Tag, Long> tagValidator) {
        this.nameValidator = offerNameValidator;
        this.priceValidator = offerPriceValidator;
        this.categoryValidator = categoryValidator;
        this.tagValidator = tagValidator;
    }

    @Override
    public void checkNotNull(Offer resource) {
        if (resource == null) {
            throw new OfferException("Offer must be NOT null, but is.");
        }
    }

    @Override
    public void checkIdIsNull(Long id) {
        if (id != null) {
            throw new OfferException("Offer's id must be null");
        }
    }

    @Override
    public void checkIdIsNotNull(Long id) {
        if (id == null) {
            throw new OfferException("Offer's id must be NOT null");
        }
    }

    @Logged(messageBefore = "Verifying offer's name...", messageAfter = "done.")
    @Override
    public void checkName(String name) {
        nameValidator.check(name);
    }

    @Logged(messageBefore = "Verifying offer's price...", messageAfter = "done.")
    @Override
    public void checkPrice(Price price) {
        priceValidator.check(price);
    }

    @Logged(messageBefore = "Verifying offer is found...", messageAfter = "done.")
    @Override
    public Offer checkFound(Offer offer, String notFoundMessage) {
        if (offer == null) {
            throw new OfferException(notFoundMessage);
        }
        return offer;
    }

    @Logged(messageBefore = "Making sure offer is found by id...", messageAfter = "done.")
    @Override
    public Offer checkFoundById(Offer offer, Long id) {
        return checkFound(offer, "Offer with id=" + id + " was not found");
    }

    @Logged(messageBefore = "Verifying all offer properties...", messageAfter = "done.")
    @Override
    public void checkAllProperties(Offer offer) {
        checkNotNull(offer);
        nameValidator.check(offer.getName());
        priceValidator.check(offer.getPrice());
        categoryValidator.checkAllProperties(offer.getCategory());
        offer.getTags().forEach(tagValidator::checkAllProperties);
    }

    @Logged(messageBefore = "Verifying offer for persist...", messageAfter = "done.")
    @Override
    public void checkForPersist(Offer offer) {
        checkNotNull(offer);
        checkIdIsNull(offer.getId());
        checkAllProperties(offer);
    }
}
