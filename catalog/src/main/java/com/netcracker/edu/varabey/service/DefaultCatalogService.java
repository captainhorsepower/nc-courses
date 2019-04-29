package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.CategoryDAO;
import com.netcracker.edu.varabey.dao.OfferDAO;
import com.netcracker.edu.varabey.dao.TagDAO;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.validation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DefaultCatalogService implements CatalogService {

    private final CategoryDAO categoryDAO;

    private final OfferDAO offerDAO;

    private final TagDAO tagDAO;

    public static final int MINIMUM_NAME_LENGTH = 3;

    /* autowired constructor */
    public DefaultCatalogService(CategoryDAO categoryDAO, OfferDAO offerDAO, TagDAO tagDAO) {
        this.categoryDAO = categoryDAO;
        this.offerDAO = offerDAO;
        this.tagDAO = tagDAO;
    }

/* category territory */

    @Override
    public Category createCategory(Category category) {
        if (category == null || category.getId() != null
                || category.getName() == null
                || category.getName().length() < MINIMUM_NAME_LENGTH) {
            throw new InvalidCategoryException();
        }
        return categoryDAO.save(category);
    }

    @Override
    public Category findCategory(Long id) {
        return categoryDAO.findById(id)
                .orElse(null);
    }

    @Override
    public Category updateCategory(Category category) throws InvalidCategoryException {
        if (category == null
                || category.getName() == null
                || category.getName().length() < MINIMUM_NAME_LENGTH) {
            throw new InvalidCategoryException("Illegal category passed to update");
        }

        Category sourceCategory = categoryDAO.findById(category.getId())
                .orElseThrow(CategoryNotFoundException::new);

        sourceCategory.setName(category.getName());
        return sourceCategory;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryDAO.deleteById(id);
    }

/* offer territory */

    @Override
    public Offer createOffer(Offer offer) {
        if (offer == null || offer.getId() != null
                || offer.getName() == null || offer.getName().length() < MINIMUM_NAME_LENGTH
                || offer.getPrice() == null || offer.getPrice().getValue() < 0.) {
            throw new InvalidOfferException();
        }
        return offerDAO.save(offer);
    }

    @Override
    public Offer findOffer(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("null id in findOffer");
        }
        return offerDAO.findById(id);
    }

    @Override
    public List<Offer> findAllOffers() {
        return offerDAO.findAll();
    }

    @Override
    public Offer updateOffer(Offer offer) {
        if (offer == null || offer.getId() == null
                || offer.getName() == null
                || offer.getPrice() == null
                || offer.getPrice().getValue() < 0.) {
            throw new InvalidOfferException();
        }
        return offerDAO.update(offer);
    }

    @Override
    public void deleteOffer(Long id) {
        if (id == null) throw new IllegalArgumentException("null id in deleteOffer");
        offerDAO.deleteById(id);
    }

    @Override
    public List<Offer> findAllOffersByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("null category in findByCategory");
        }
        return offerDAO.findAllByCategory(category);
    }

    @Override
    public List<Offer> findAllWithPriceInRange(Double left, Double right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("invalid range bounds");
        }
        return offerDAO.findAllWithPriceInRange(left, right);
    }

    @Override
    public List<Offer> findAllOffersWithTags(List<Tag>  tags) {
        if (tags == null || tags.isEmpty()) {
            throw new IllegalArgumentException("provide at least 1 tag to findAllOffersWithTags()");
        }
        return offerDAO.findAllWithTags(tags);
    }

/* tag territory */

    @Override
    public Tag createTag(Tag tag) {
        if (tag == null || tag.getId() != null
                || tag.getName() == null || tag.getName().length() < MINIMUM_NAME_LENGTH){
            throw new InvalidTagException();
        }
        return tagDAO.save(tag);
    }

    @Override
    public Tag findTag(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("tag id must not be null");
        }
        return tagDAO.findById(id);
    }

    @Override
    public Tag updateTag(Tag tag) {
        if (tag == null || tag.getId() == null
                || tag.getName() == null
                || tag.getName().length() < MINIMUM_NAME_LENGTH) {
            throw new InvalidTagException();
        }

        Tag sourceTag = tagDAO.findById(tag.getId());

        if (sourceTag == null) {
            throw new TagNotFoundException();
        }

        sourceTag.setName(tag.getName());
        return sourceTag;
    }

    @Override
    public void deleteTag(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        tagDAO.delete(id);
    }
}
