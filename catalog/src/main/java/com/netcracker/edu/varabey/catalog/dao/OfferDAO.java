package com.netcracker.edu.varabey.catalog.dao;

import com.netcracker.edu.varabey.catalog.entity.Category;
import com.netcracker.edu.varabey.catalog.entity.Offer;
import com.netcracker.edu.varabey.catalog.entity.Tag;

import java.util.Collection;
import java.util.List;

public interface OfferDAO {
    Offer save(Offer offer);
    Offer findById(Long id);
    Offer update(Offer offer);
    void deleteById(Long id);

    List<Offer> findAll();
    List<Offer> findAllByCategory(Category category);
    List<Offer> findAllWithTags(Collection<Tag> tags);
    List<Offer> findAllWithPriceInRange(Double lowerBound, Double upperBound);
}