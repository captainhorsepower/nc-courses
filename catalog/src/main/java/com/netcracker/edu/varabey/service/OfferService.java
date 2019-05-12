package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidOfferException;

import java.util.List;

public interface OfferService {

    /**
     * Saves an offer.
     * @param offer - must be transient and not null
     * @return saved offer
     * @throws InvalidOfferException if offer is null || is detached || has invalid properties
     */
    Offer create(Offer offer);

    /**
     * Finds an offer by id.
     * @param id - must not be null
     * @return found offer or null
     */
    Offer findById(Long id);

    /**
     * @return list with all existing offers
     */
    List<Offer> findAll();

    /**
     * Merge the state of the given offer into the catalog database.
     * Allows you to update:
     *  -price (value only)
     *  -remove tags
     *  -add tags (you can findById new tag,
     *          but then it's id should be null when you pass it to this method)
     *  -change category (you can findById new one, -||-)
     *
     * You should NOT change names of existing tags nor categories.
     *
     * @param offer  offer instance with updated fields
     * @return updated offer
     * @throws InvalidOfferException if offer is null or has any invalid properties
     */
    Offer update(Offer offer);

    Offer addTags(Long id, List<String> tagNames);

    Offer removeTags(Long id, List<String> tagNames);

    Offer changeCategory(Long id, Category category);

    Offer updateNameAndPrice(Offer offer);

    /**
     * Deletes an offer by id
     * @param id - must not be null
     * @throws IllegalArgumentException if id is null
     */
    void delete(Long id);

    /**
     * Finds all offers with given category
     * @param category - must not be null
     * @return all found offers as List
     * @throws IllegalArgumentException if category is null
     */
    List<Offer> findAllOffersByCategory(Category category);

    /**
     * Finds all offers, that have all given tags
     * @param tags as filter
     * @return found offers
     * @throws IllegalArgumentException if tags is null or empty
     */
    List<Offer> findAllOffersWithTags(List<Tag>  tags);

    /**
     * Finds all offers with price in range given range
     * @param left inclusive
     * @param right inclusive
     * @return found offers
     * @throws IllegalArgumentException if left || right are invalid
     */
    List<Offer> findAllWithPriceInRange(Double left, Double right);
}
