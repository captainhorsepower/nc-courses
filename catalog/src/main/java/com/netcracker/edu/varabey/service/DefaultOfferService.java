package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.OfferDAO;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.validation.NameValidator;
import com.netcracker.edu.varabey.service.validation.PriceRangeValidator;
import com.netcracker.edu.varabey.service.validation.PriceValidator;
import com.netcracker.edu.varabey.service.validation.ServiceValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultOfferService implements OfferService {
    private final OfferDAO offerDAO;
    private final TagService tagService;
    private final CategoryService categoryService;
    private final ServiceValidator<Offer, Long> offerValidator;
    private final NameValidator offerNameValidator;
    private final PriceValidator offerPriceValidator;
    private final ServiceValidator<Category, Long> categoryValidator;
    private final ServiceValidator<Tag, Long> tagValidator;
    private final PriceRangeValidator priceRangeValidator;

    public DefaultOfferService(OfferDAO offerDAO, TagService tagService, CategoryService categoryService, ServiceValidator<Offer, Long> offerValidator, NameValidator offerNameValidator, PriceValidator offerPriceValidator, ServiceValidator<Category, Long> categoryValidator, ServiceValidator<Tag, Long> tagValidator, PriceRangeValidator priceRangeValidator) {
        this.offerDAO = offerDAO;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.offerValidator = offerValidator;
        this.offerNameValidator = offerNameValidator;
        this.offerPriceValidator = offerPriceValidator;
        this.categoryValidator = categoryValidator;
        this.tagValidator = tagValidator;
        this.priceRangeValidator = priceRangeValidator;
    }

    @Override
    public Offer create(Offer offer) {
        offerValidator.checkForPersist(offer);
        offer.setCategory(categoryService.save(offer.getCategory()));
        offer.setTags( offer.getTags().stream()
                .map(tagService::create)
                .collect(Collectors.toSet())
        );
        return offerDAO.save(offer);
    }

    @Override
    public Offer findById(Long id) {
        offerValidator.checkIdIsNotNull(id);
        return offerDAO.findById(id);
    }

    @Override
    public List<Offer> findAll() {
        return offerDAO.findAll();
    }

    @Override
    public Offer update(Offer offer) {
        offerValidator.checkForUpdate(offer);
        Offer source = findById(offer.getId());
        offerValidator.checkNotNull(offer);
        offer.setCategory( categoryService.getByName(offer.getCategory().getName()) );
        offer.setTags( offer.getTags().stream()
                .map(Tag::getName)
                .map(tagService::getByName)
                .collect(Collectors.toSet())
        );
        return source;
    }

    @Override
    public Offer addTags(Long id, List<String> tagNames) {
        Offer offer = findById(id);
        offerValidator.checkFoundById(offer, id);
        tagNames.stream()
                .map(tagService::getByName)
                .forEach(offer::addTag);
        return offer;
    }

    @Override
    public Offer removeTags(Long id, List<String> tagNames) {
        Offer offer = findById(id);
        offerValidator.checkFoundById(offer, id);
        tagNames.stream()
                .map(Tag::new)
                .forEach(offer::removeTag);
        return offer;
    }

    @Override
    public Offer changeCategory(Long id, Category category) {
        Offer offer = findById(id);
        offerValidator.checkFoundById(offer, id);
        categoryValidator.checkNotNull(category);
        offer.setCategory(categoryService.save(category));
        return offer;
    }

    @Override
    public Offer updateNameAndPrice(Offer updateBearer) {
        offerValidator.checkNotNull(updateBearer);
        Offer existingOffer = findById(updateBearer.getId());
        offerValidator.checkFoundById(existingOffer, updateBearer.getId());

        if (updateBearer.getName() != null) {
            offerNameValidator.check(updateBearer.getName());
            existingOffer.setName(updateBearer.getName());
        }
        if (updateBearer.getPrice() != null) {
            offerPriceValidator.check(updateBearer.getPrice());
            existingOffer.setPrice(updateBearer.getPrice());
        }

        return existingOffer;
    }

    @Override
    public void delete(Long id) {
        offerValidator.checkIdIsNotNull(id);
        offerDAO.deleteById(id);
    }

    @Override
    public List<Offer> findAllOffersByCategory(Category category) {
        categoryValidator.checkProperties(category);
        return offerDAO.findAllByCategory(category);
    }

    @Override
    public List<Offer> findAllOffersWithTags(List<Tag> tags) {
        tags.forEach(tagValidator::checkProperties);
        return offerDAO.findAllWithTags(tags);
    }

    @Override
    public List<Offer> findAllWithPriceInRange(Double left, Double right) {
        priceRangeValidator.check(left, right);
        return offerDAO.findAllWithPriceInRange(left, right);
    }
}
