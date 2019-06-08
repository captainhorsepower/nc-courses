package com.netcracker.edu.varabey.catalog.service;

import com.netcracker.edu.varabey.catalog.dao.OfferDAO;
import com.netcracker.edu.varabey.catalog.entity.Category;
import com.netcracker.edu.varabey.catalog.entity.Offer;
import com.netcracker.edu.varabey.catalog.entity.Tag;
import com.netcracker.edu.varabey.catalog.validation.CategoryValidator;
import com.netcracker.edu.varabey.catalog.validation.OfferValidator;
import com.netcracker.edu.varabey.catalog.validation.TagValidator;
import com.netcracker.edu.varabey.catalog.validation.util.PriceRangeValidator;
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
    private final OfferValidator offerValidator;
    private final CategoryValidator categoryValidator;
    private final TagValidator tagValidator;
    private final PriceRangeValidator priceRangeValidator;

    public DefaultOfferService(OfferDAO offerDAO, TagService tagService, CategoryService categoryService, OfferValidator offerValidator, CategoryValidator categoryValidator, TagValidator tagValidator, PriceRangeValidator priceRangeValidator) {
        this.offerDAO = offerDAO;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.offerValidator = offerValidator;
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
    public Offer addTags(Long id, List<String> tagNames) {
        Offer offer = offerValidator.checkFoundById(findById(id), id);
        tagNames.stream()
                .map(tagService::getByName)
                .forEach(offer::addTag);
        return offer;
    }

    @Override
    public Offer removeTags(Long id, List<String> tagNames) {
        Offer offer = offerValidator.checkFoundById(findById(id), id);
        tagNames.stream()
                .map(Tag::new)
                .forEach(offer::removeTag);
        return offer;
    }

    @Override
    public Offer changeCategory(Long id, Category category) {
        Offer offer = offerValidator.checkFoundById(findById(id), id);
        offer.setCategory(categoryService.save(category));
        return offer;
    }

    @Override
    public Offer updateNameAndPrice(Offer updateBearer) {
        offerValidator.checkNotNull(updateBearer);
        Offer existingOffer = offerValidator.checkFoundById(findById(updateBearer.getId()), updateBearer.getId());

        if (updateBearer.getName() != null) {
            offerValidator.checkName(updateBearer.getName());
            existingOffer.setName(updateBearer.getName());
        }
        if (updateBearer.getPrice() != null) {
            offerValidator.checkPrice(updateBearer.getPrice());
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
        categoryValidator.checkAllProperties(category);
        return offerDAO.findAllByCategory(category);
    }

    @Override
    public List<Offer> findAllOffersWithTags(List<Tag> tags) {
        tags.forEach(tagValidator::checkAllProperties);
        return offerDAO.findAllWithTags(tags);
    }

    @Override
    public List<Offer> findAllWithPriceInRange(Double left, Double right) {
        priceRangeValidator.check(left, right);
        return offerDAO.findAllWithPriceInRange(left, right);
    }
}
