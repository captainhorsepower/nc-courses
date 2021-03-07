package com.netcracker.edu.varabey.catalog.data.dao;

import com.netcracker.edu.varabey.catalog.data.entity.Category;
import com.netcracker.edu.varabey.catalog.data.entity.Offer;
import com.netcracker.edu.varabey.catalog.data.entity.Tag;

import java.util.Collection;
import java.util.List;

public interface OfferDAO {
    /**
     * Create an offer in the database.
     * If tags and category inside offer, have set Ids, it might introduce unexpected
     * side effects like tag.name update, etc. Be careful.
     *
     * @param offer NEW (TRANSIENT) offer entity. Offer.id should be null.
     * @return created offer.
     */
    Offer save(Offer offer);

    /**
     * Retrieve offer from the database.
     * @param id unique offer id
     * @return offer with given id
     */
    Offer findById(Long id);

    /**
     * Retrieve all offers from the catalog database.
     * @return List of all offers in the catalog.
     */
    List<Offer> findAll();

    /**
     * Merge the state of the given offer into the catalog database.
     * Allows you to update:
     *  -price (value only)
     *  -remove tags
     *  -add tags (you can create new tags,
     *          but then it's id should be null when you pass it to this method)
     *  -change category (you can create new one, -||-)
     *
     * You should NOT change names of existing tags nor categories.
     *
     * @param offer  offer instance with updated fields
     * @return updated offer
     */
    Offer update(Offer offer);

    /**
     * Completely removes offer from the Catalog database.
     * @param id unique id of target offer.
     */
    void deleteById(Long id);

    /**
     * Finds all offers having given category
     * @param category query param
     * @return list of all offers with given category.
     */
    List<Offer> findAllByCategory(Category category);

    /**
     * Finds all offers having all tags.
     * @param tags query list param.
     * @return list of offers having all given tags.
     */
    List<Offer> findAllWithTags(Collection<Tag> tags);

    /**
     * Finds all offers with price in range.
     * @param lowerBound inclusive
     * @param upperBound inclusive
     * @return list of all offers with price within given range.
     */
    List<Offer> findAllWithPriceInRange(Double lowerBound, Double upperBound);
}