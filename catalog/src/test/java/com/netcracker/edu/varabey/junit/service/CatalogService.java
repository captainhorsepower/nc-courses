package com.netcracker.edu.varabey.junit.service;

import com.netcracker.edu.varabey.dao.CategoryDAO;
import com.netcracker.edu.varabey.dao.OfferDAO;
import com.netcracker.edu.varabey.dao.TagDAO;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CatalogService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private OfferDAO offerDAO;

    @Autowired
    private TagDAO tagDAO;


    public Category createCategory(Category category) {
        return categoryDAO.save(category);
    }

    public Category findCategory(Long id) {
        return categoryDAO.findById(id);
    }

    public Category updateCategory(Category category) {
        return categoryDAO.update(category);
    }

    public void deleteCategory(Long id) {
        categoryDAO.delete(id);
    }

    public List<Offer> findAllOffers() {
        return offerDAO.findAll();
    }

    public Offer createOffer(Offer offer) {
        return offerDAO.save(offer);
    }

    public Offer findOffer(Long id) {
        return offerDAO.save(id);
    }

    public List<Offer> findAllOffersByCategory(Category category) {
        return offerDAO.findAllByCategory(category);
    }

    public void deleteOffer(Long id) {
        offerDAO.delete(id);
    }

    public List<Offer> findAllWithPriceInRange(double left, double right) {
        return offerDAO.findAllWithPriceInRange(left, right);
    }

    public Tag createTag(Tag tag) {
        return tagDAO.save(tag);
    }

    public List<Offer> findAllOffersWithTags(List<Tag>  tags) {
        return offerDAO.findAllWithTags(tags);
    }

    public Tag findTag(Long id) {
        return tagDAO.find(id);
    }

    public Offer updateOffer(Offer offer) {
        return offerDAO.update(offer);
    }

    public Tag updateTag(Tag tag) {
        return tagDAO.update(tag);
    }

    public void deleteTag(Long id) {
        tagDAO.delete(id);
    }
}
